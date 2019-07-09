package com.xact;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Creditor;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.Customer;
import com.bean.Xact;
import com.bean.XactType;
import com.bean.XactCategory;
import com.bean.XactTypeItemActivity;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorException;
import com.gl.creditor.CreditorFactory;
import com.gl.customer.CustomerFactory;
import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerException;

import com.inventory.ItemConst;

import com.xact.sales.SalesFactory;
import com.xact.sales.SalesOrderException;
import com.xact.sales.SalesOrderApi;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;

import com.util.RMT2String;
import com.util.SystemException;

/**
 * This abstract class provides functionality obtaining basic transacction related data from the client's request object
 * 
 * @author Roy Terrell
 *
 */
public class HttpXactHelper extends AbstractActionHandler {
    protected static final String ITEM_COUNT_IND = "itemcount";

    private Logger logger;

    protected Xact xact;

    protected XactType xactType;

    protected XactCategory xactCategory;

    protected List xactItems;

    /**
     * Default constructor
     *
     */
    public HttpXactHelper() {
	super();
	logger = Logger.getLogger("HttpXactHelper");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public HttpXactHelper(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	logger = Logger.getLogger(HttpXactHelper.class);
    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	// Create base transaction API object.
	this.xact = null;
	this.xactType = null;
	this.xactCategory = null;
    }

    /**
     * Combines the process of probing the client's HttpServletRequest object for basic transaction 
     * data (transaction base, transaction type, transaction category, and transaction detail items).    
     * After invoking this method, be sure to call the methods, getXact, getXactType, getXactCatg, 
     * and getXactItems to obtain the results.  
     * 
     * @return Transaction object.
     * @throws XactException
     */
    public void getHttpCombined() throws SystemException {
	try {
	    this.xact = this.getHttpXact();
	    this.xactType = this.getHttpXactType();
	    this.xactCategory = this.getHttpXactCategory();
	    this.xactItems = (ArrayList) this.getHttpXactItems();
	    this.xactItems = (this.xactItems == null ? new ArrayList() : this.xactItems);
	}
	catch (XactException e) {
	    throw new SystemException(e.getMessage());
	}

	logger.log(Level.DEBUG, "Transaction was successfully gathered from the client");
	logger.log(Level.DEBUG, "Transaction Id: " + this.xact.getXactId());
	logger.log(Level.DEBUG, "Transaction Amount: " + this.xact.getXactAmount());
	return;
    }

    /**
     * Obtains transaction base object from the client's request.  <p>If the transaction is new 
     * then the data is retrieved from the request object.   Otherwise, a copy of the transaction is obtained from
     * the database using transaction id gathered from the client and stored as member variable. 
     * <p>
     * <p>
     *  A transaction is considered new if the transaction equals zero.   The id of existing transactions will have a value 
     * equal or greater than 1.  
     * 
     * @return  {@link Xact}
     * @throws XactException
     */
    public Xact packageHttpXact() throws XactException {
	Xact xact = null;
	try {
	    // Transaction is new, get remaining data from the request object.
	    logger.log(Level.DEBUG, "Packaging base Transaction from HTTP.");
	    xact = XactFactory.createXact();
	    XactFactory.packageBean(this.request, this.xact);
	    // Ensure that transaction id is equal to zero.
	    xact.setXactId(0);
	    return xact;
	}
	catch (SystemException e) {
	    this.msg = "SystemException - " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg);
	}
    }

    /**
     * Attempts to obtain transaction data from the client JSP into a transaction object.
     *  
     * @return {@link Xact}- Contains the transaction data retrieved from the client or a 
     *  newly initialized Xact object when transaction is not found.
     * @throws SalesOrderException
     */
    public Xact getHttpXact() throws XactException {
	Xact xact = XactFactory.createXact();
	String temp = null;
	String subMsg = null;
	int xactId = 0;
	boolean listPage = true;

	// Determine if we are coming from a page that presents data as a list of orders or as single order.
	// Get selected row number from client page.  If this row number exist, then we 
	// are coming from page that contains a list of orders.
	subMsg = "Transaction number could not be obtained from list of orders";
	temp = this.request.getParameter("XactId" + this.getSelectedRow());
	if (temp == null) {
	    listPage = false;
	}
	if (!listPage) {
	    // Get order id for single row
	    temp = this.request.getParameter("XactId");
	}
	try {
	    xactId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    logger.log(Level.INFO, subMsg);
	    this.xact = xact;
	    return xact;
	}

	// Get Transaction object
	try {
	    xact = XactFactory.createXact();
	    XactFactory.packageBean(this.request, xact);
	    xact.setXactId(xactId);
	    this.xact = xact;
	    return xact;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new XactException(e);
	}
    }

    /**
     * Obtains the transaction type object the client's HTTP request object.  If the transaction is new 
     * then the data is retrieved from the request object.  
     * <p>
     *  A transaction type is considered new if its id equals zero.   The id of existing transaction 
     *  type will have a value equal or greater than 1.  
     *  
     * @return  {@link XactType object}.
     * @throws XactException
     */
    public XactType getHttpXactType() throws XactException {
	String temp = null;
	int xactTypeId = 0;
	XactType xactType = XactFactory.createXactType();

	temp = this.request.getParameter("XactTypeId");
	if (temp == null) {
	    // Try to obtain from a list of transactions
	    temp = this.request.getParameter("XactTypeId" + this.getSelectedRow());
	}
	try {
	    xactTypeId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    this.msg = "Transaction Type could not be converted to a number from JSP page: " + xactType;
	    logger.log(Level.INFO, this.msg);
	    this.xactType = xactType;
	    return xactType;
	}

	// Get transaction type object from request object
	try {
	    XactFactory.packageBean(this.request, xactType);
	    xactType.setXactTypeId(xactTypeId);
	    this.xactType = xactType;
	    return xactType;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new XactException(e);
	}
    }

    /**
     * Obtains transaction category from client's request.  <p>If the transaction is new 
     * then the data is retrieved from the request object.   Otherwise, a copy of the 
     * transaction category is obtained from the database using the category id from the current 
     * transaction type object and stored as member variable. 
     * <p>
     * <p>
     *  A transaction category is considered new if its id equals zero.   The id of existing transaction 
     *  type will have a value equal or greater than 1.
     * 
     * @return {@link XactCategory}
     * @throws XactException
     */
    public XactCategory getHttpXactCategory() throws XactException {
	XactCategory catg = XactFactory.createXactCategory();
	String temp = null;
	int xactCatgId = 0;
	boolean listPage = true;

	temp = this.request.getParameter("XactCatgId");
	if (temp == null) {
	    // Try to obtain from a list of transactions
	    temp = this.request.getParameter("XactCatgId" + this.getSelectedRow());
	    if (temp == null) {
		listPage = false;
	    }
	    if (!listPage) {
		temp = this.request.getParameter("XactCatgId");
	    }
	}
	try {
	    xactCatgId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    this.msg = "Transaction Category could not be converted to a number from JSP page: " + xactType;
	    logger.log(Level.INFO, this.msg);
	    this.xactCategory = catg;
	    return catg;
	}
	// Get transaction category type object from request object
	try {
	    XactFactory.packageBean(this.request, catg);
	    catg.setXactCatgId(xactCatgId);
	    this.xactCategory = catg;
	    return xactCategory;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new XactException(e);
	}
    }

    /**
     * Obtains all related items of a transaction from the client.   Each item is associated with a 
     * client control that is used as an index and is identified as {@link XactConst.ITEM_COUNT_IND} 
     * by the default.  
     * 
     * @return  List of generic transaction items 
     * @throws XactException
     */
    public List getHttpXactItems() throws XactException {
	logger.log(Level.DEBUG, "Retrieving Base Transaction items from client UI using default index property, " + XactConst.ITEM_COUNT_IND);
	return this.getHttpXactItems(XactConst.ITEM_COUNT_IND);
    }

    /**
     * Obtains all related items of a transaction from the client.   Each item is associated with a 
     * client control that is used as an index and is identified by the parameter, "itemNdxName. 
     * Gathers and packages data from the client's request object into separate generic objects of type Object, which generically represents the detail items of the base 
     * transaction.  Subsequently, the transaction item buffer is refreshed with the ArrayList of generic objects that 
     * is intended to be associated with the base transaction.
     * <p>
     * This method requires that the Input control property containing each transaction item id is named, {@link XactConst.ITEM_COUNT_IND}.
     * 
     * @param itemNdxName
     * @return List of generic transaction items
     * @throws XactException
     */
    public List getHttpXactItems(String itemNdxName) throws XactException {
	ArrayList items = new ArrayList();
	Object obj = null;
	String itemRowIndexes[] = null;

	logger.log(Level.DEBUG, "Retrieving Base Transaction items from client UI using index property, " + itemNdxName);
	itemRowIndexes = this.request.getParameterValues(itemNdxName);

	//  Return to caller if there are no items to process.
	if (itemRowIndexes == null) {
	    return null;
	}

	// Process each transaction object coming from the clients http request object
	for (int ndx = 0; ndx < itemRowIndexes.length; ndx++) {
	    obj = this.getHttpXactItems(ndx, itemRowIndexes);
	    if (obj != null) {
		items.add(obj);
	    }
	} // end for

	this.xactItems = items;
	return items;
    }

    /**
     * Obtains a single transaction detail item from a list of items.  <p>Default functionality for evaluating and 
     * capturing individual pieces of data pertaining to the client's transaction item data.  The data is mapped 
     * by default to {@link XactTypeItemAcivity}.  
     * <p>
     * <p>
     * This method in addition to <b>createNewXactItemObject()</b> can be overriden to target another object 
     * other than <b>XactTypeItemAcivity</b> to map the transaction item data .
     * 
     * @param _ndx represents the target position in _rowIndexes that is to be processed.
     * @param _itemRowIndexes An String array of row numbers which corresponds to 
     *                  each transaction item derived from the client.
     * @return Generic object that will represent the mapped transaction item.  The object 
     *                  type returned by default is {@link XactTypeItemAcivity}.
     * @throws XactException
     */
    private Object getHttpXactItems(int _ndx, String _itemRowIndexes[]) throws XactException {
	XactTypeItemActivity xtia = null;
	String value = null;
	String property = null;
	int tempInt = 0;
	int row = 0;
	double tempDouble = 0;

	try {
	    // Get new transaction item object
	    xtia = (XactTypeItemActivity) createNewXactItemObject();

	    // Get row number
	    this.msg = "Problem converting row value to integer.";
	    row = Integer.parseInt(_itemRowIndexes[_ndx]);

	    // Was item selected by the user?
	    property = XactConst.ITEM_SELECTOR + row;
	    value = this.request.getParameter(property);
	    if (value != null) {
		// Do not include this item if it is selected to be removed from the order
		property = "itemcount" + row;
		value = this.request.getParameter(property);
		logger.log(Level.DEBUG, "The following item was removed from transaction details: " + value);
		return null;
	    }

	    // Get transaction type item activity id
	    this.msg = "Problem converting transaction type item activity id value to integer.";
	    property = "XactTypeItemActvId" + row;
	    value = this.request.getParameter(property);
	    tempInt = Integer.parseInt(value);
	    xtia.setXactTypeItemActvId(tempInt);

	    // Get transaction id
	    this.msg = "Problem converting xact id value to integer.";
	    property = "XactId" + row;
	    value = this.request.getParameter(property);
	    tempInt = Integer.parseInt(value);
	    xtia.setXactId(tempInt);

	    // Get Transaction Type Item Id
	    this.msg = "Problem converting Transaction Type Item Id value to integer.";
	    property = "XactItemId" + row;
	    value = this.request.getParameter(property);
	    value = (value == null || value.equals("") ? "0" : value);
	    tempInt = new Integer(value).intValue();
	    xtia.setXactItemId(tempInt);

	    // Get Transaction Type Item amount
	    this.msg = "Problem converting Transaction Type Item amount value to integer.";
	    property = "Amount" + row;
	    value = this.request.getParameter(property);
	    tempDouble = new Double(value).doubleValue();
	    xtia.setAmount(tempDouble);

	    // Get Transaction Type Item Description
	    this.msg = "Problem converting Transaction Type Item description value to integer.";
	    property = "Description" + row;
	    value = this.request.getParameter(property);
	    xtia.setDescription(value);

	    // Return generic object to caller
	    return xtia;
	}
	catch (NumberFormatException e) {
	    logger.log(Level.ERROR, this.msg);
	    return null;
	}
    }

    /**
     * Retrieves one or more sales order items based on its type from the client's request using _so and _itemTypeCode.  
     * <p>
     * Any items selected from the client UI will be excluded from the sales order at save time.
     * <p>
     *  In order for this method to work properly, the client's JSP must follow these rules:
     * 1.  One HTML input control must be declared with a non-unique name in the following format "rowId" + _itemTypeCode.
     *       The value of this column will be the row number.   The value of this column is used to identify all other columns
     *       on the same row.
     * 2.   Other HTML input cotrols must be decalred with unique names in the following format:
     *             _itemTypeCode + "SlsOrdItemId" + <row number>.
     *    
     * @param so - Sales Order object
     * @param itemTypeCode - Portion of the HTML input control's name that represents the Item Type such as "Srvc" or "Merch".
     * @retrun  List of {@link SalesOrderItems} that represents the list of items gathered as a result of this process.
     */
    public List getHttpXactItems(SalesOrder so, String itemTypeCode) {
	SalesOrderItems soi = null;
	ArrayList items = new ArrayList();
	String rowIndexes[] = null;
	String msg = null;
	String value = null;
	String property = null;
	int salesOrderItemId = 0;
	int itemId = 0;
	int orderQty = 0;
	Double retailPrice = null;
	int row = 0;

	logger.log(Level.DEBUG, "Retrieving sales order items from client UI");
	property = HttpXactHelper.CLIENT_ITEM_ROWID + itemTypeCode;
	rowIndexes = this.request.getParameterValues(property);
	if (items == null) {
	    items = new ArrayList();
	}

	//  Return to caller if there are no items to process.
	if (rowIndexes == null) {
	    return items;
	}

	for (int ndx = 0; ndx < rowIndexes.length; ndx++) {
	    try {
		// Get row number
		msg = "Problem converting row value to integer.";
		row = Integer.parseInt(rowIndexes[ndx]);

		// Was item selected by the user?
		property = HttpXactHelper.CLIENT_ITEM_SELECTOR + itemTypeCode + row;
		value = this.request.getParameter(property);
		if (value != null) {
		    // Do not include this item if it is selected to be removed from the order
		    property = itemTypeCode + "ItemId" + row;
		    value = this.request.getParameter(property);
		    logger.log(Level.DEBUG, "The following item was removed from sales order: " + value);
		    continue;
		}

		// Get sales order item id
		msg = "Problem converting sales order item id value to integer.";
		property = itemTypeCode + "SlsOrdItemId" + row;
		value = this.request.getParameter(property);
		salesOrderItemId = Integer.parseInt(value);

		// Get item master id
		msg = "Problem converting Item Master id value to integer.";
		property = itemTypeCode + "ItemId" + row;
		value = this.request.getParameter(property);
		itemId = Integer.parseInt(value);

		// Get Quantity order for item
		msg = "Problem converting Item Quantity ordered value to integer.";
		property = itemTypeCode + "OrderQty" + row;
		value = this.request.getParameter(property);
		orderQty = new Double(value).intValue();
		logger.log(Level.DEBUG, "Quantity Ordered for property, " + property + ", is : " + orderQty);
		
		// Get item cost amount for item
		msg = "Retail price for item was not editable/available from the UI";
		property = itemTypeCode + "RetailPrice" + row;
		value = this.request.getParameter(property);
		if (value != null) {
		    try {
			value = RMT2String.replace(value, "", "$");
			value = RMT2String.replace(value, "", ",");
			retailPrice = new Double(value);	
		    }
		    catch (NumberFormatException e) {
			retailPrice = null;
			logger.log(Level.DEBUG, msg);
		    }
		}
	    }
	    catch (NumberFormatException e) {
		logger.log(Level.DEBUG, msg);
		continue;
	    }

	    // Get general item details from the database and store in array list.
	    try {
		soi = SalesFactory.createSalesOrderItem(so.getSoId(), itemId, orderQty);
		soi.setSoItemId(salesOrderItemId);
		if (retailPrice != null) {
		    soi.setInitUnitCost(retailPrice.doubleValue());
		    soi.setInitMarkup(1);    
		}
		items.add(soi);
	    }
	    catch (SystemException e) {
		logger.log(Level.ERROR, e.getMessage());
	    }
	} // end for

	return items;
    }

    /**
     * Default functionality for creating the datasource object, {@link XactTypeItemAcivity}.  <p>This method is generally invoked 
     * by the method, <b>getXactDetails()</b>, to store data mapped to the client's transaction items.  This method can be 
     * overriden to create an object other than <b>XactTypeItemAcivity</b> for mapping transaction item data.
     * 
     * @return By default, returns {@link XactTypeItemAcivity} cast as type Object.
     */
    public Object createNewXactItemObject() {
	XactTypeItemActivity xtia = null;
	xtia = XactFactory.createXactTypeItemActivity();
	return xtia;
    }

    /**
     * Locates customer data by querying the clinet's request object.   <p>The data can be chosen from a list of 
     * customers or from a single customer record.   If customer cannot be created using request data, then a 
     * new Customer object is created.
     * <p>
     * <p>
     * It is required that the input control representing the customer id is named, "CustomerId", regardless if it exist within a list of 
     * customers or as a single customer.  In order for the target customer to be retrieved from a list, the user interface 
     * is required to be setup in such a way where the customer id input control is uniquely named.   A checkbox or radio button 
     * should exist to contain the row id, and its name should be labeled as "selCbx".   The unique naming of the customer id control 
     * should follow the following format: ("CustomerId" + row id).   Optionally, the customer id control can be named ("CustId" + row id) 
     * when processing a list of customers.
     * 
     * @return {@link Customer}
     * @throws CustomerException
     */
    public Customer getHttpCustomer() throws CustomerException {
	String temp = null;
	int custId = 0;
	boolean listData = true;

	// Determine if we are coming from a page that presents data as a list of orders or as single order.
	try {
	    // Get selected row number from client page.  If this row number exist, then we 
	    // are coming from page that contains a list of orders.
	    temp = this.request.getParameter("CustomerId" + this.getSelectedRow());
	    if (temp == null) {
		listData = false;
	    }
	    else {
		custId = Integer.parseInt(temp);
	    }
	}
	catch (NumberFormatException e) {
	    this.msg = "Customer number could not be obtained from list of orders";
	    logger.log(Level.ERROR, this.msg);
	    throw new CustomerException(this.msg);
	}

	if (!listData) {
	    try {
		temp = this.request.getParameter("CustomerId");
		if (temp == null) {
		    temp = this.request.getParameter("CustId");
		}
		custId = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		this.msg = "Customer number could not be obtained from single record form.";
		logger.log(Level.ERROR, this.msg);
		throw new CustomerException(this.msg);
	    }
	}

	// Get Customer object
	Customer cust = null;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CustomerApi custApi = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    cust = (Customer) custApi.findById(custId);
	    if (cust == null) {
	    	cust = CustomerFactory.createCustomer();
	    }
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	}
	finally {
	    custApi.close();
	    tx.close();
	    custApi = null;
	    tx = null;
	}
	
	return cust;
    }

    /**
     * Locates creditor data by querying the client's request object.   The data can be chosen from a list of 
     * creditors or from a single creditor record.   If customer cannot be created using request data, then a 
     * new Creditor object is created.
     * <p>
     * It is required that the input control representing the customer id is named, "CredtorId", regardless 
     * if it exist within a list of creditors or as a single creditor.  In order for the target creditor to 
     * be retrieved from a list, the user interface is required to be setup in such a way where the creditor 
     * id input control is uniquely named.   A checkbox or radio button should exist to contain the row id, 
     * and its name should be labeled as "selCbx".   The unique naming of the creditor id control should 
     * follow the following format: ("CreditorId" + row id).   Optionally, the customer id control can be 
     * named ("CredId" + row id) when processing a list of creditors.
     * 
     * @return {@link Creditor}
     * @throws CreditorException
     */
    public Creditor getHttpCreditor() throws CreditorException {
	String temp = null;
	int credId = 0;
	boolean listData = true;

	// Determine if we are coming from a page that presents data as a list of orders or as single order.
	try {
	    // Get selected row number from client page.  If this row number exist, then we 
	    // are coming from page that contains a list of orders.
	    temp = this.request.getParameter("CreditorId" + this.getSelectedRow());
	    if (temp == null) {
		listData = false;
	    }
	    else {
		credId = Integer.parseInt(temp);
	    }
	}
	catch (NumberFormatException e) {
	    this.msg = "Creditor number could not be obtained from list of orders";
	    logger.log(Level.ERROR, this.msg);
	    throw new CreditorException(this.msg);
	}

	if (!listData) {
	    try {
		temp = this.request.getParameter("CreditorId");
		if (temp == null) {
		    temp = this.request.getParameter("CredId");
		}
		credId = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		this.msg = "Creditor number could not be obtained from single record form.";
		logger.log(Level.ERROR, this.msg);
		throw new CreditorException(this.msg);
	    }
	}

	// Get Creditor object
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi credApi = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	Creditor cred = null;
	try {
	    cred = (Creditor) credApi.findById(credId);
	    if (cred == null) {
		cred = CreditorFactory.createCreditor();
	    }
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	}
	finally {
	    tx.close();
	    credApi = null;
	    tx = null;
	}
	return cred;
    }

    /**
     * Locates base sales order data by querying the clinet's request object.  <p>The data from the client's request can 
     * exist as a list of sales orders or as a single sales order record.   If the sales order cannot be obtained using request 
     * data, then a new Sales Order object is created.
     * <p>
     * <p>
     * It is required that the input control representing the order id is named, "OrderId", regardless if it exist within a list of 
     * sales orders or as a single sales order.  In order for the target sales order to be retrieved from a list, the user interface 
     * is required to be setup in such a way where the order id input control is uniquely named.   A checkbox or radio button 
     * should exist to contain the row id and its name should be labeled as "selCbx".   The unique naming of the order id control 
     * should follow the following format: ("OrderId" + row id).   
     * 
     * @return {@link SalesOrder}
     * @throws SalesOrderException
     */
    public SalesOrder getHttpSalesOrder() throws SalesOrderException {
	String temp = null;
	String subMsg = null;
	int orderId = 0;
	boolean isOrderListPage = true;
	
	// Determine if we are coming from a page that presents data as a list of orders or as single order.
	try {
	    // Get selected row number from client page.  If this row number exist, then we 
	    // are coming from page that contains a list of orders.
	    temp = this.request.getParameter("OrderId" + this.getSelectedRow());
	    if (temp == null) {
		isOrderListPage = false;
	    }
	    else {
		subMsg = "Order number could not be obtained from list of orders";
		orderId = Integer.parseInt(temp);
	    }
	}
	catch (NumberFormatException e) {
	    logger.log(Level.ERROR, subMsg);
	    throw new SalesOrderException(subMsg);
	}

	if (!isOrderListPage) {
	    try {
		// Get order id for single row
		temp = this.request.getParameter("OrderId");
		subMsg = "Order number could not be obtained from single record form.  Must be creating an order.";
		orderId = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		logger.log(Level.ERROR, subMsg);
		return SalesFactory.createSalesOrder();
	    }
	}

	// Get Sales Order object
	DatabaseTransApi tx = DatabaseTransFactory.create();
	SalesOrderApi salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	SalesOrder so = null;
	try {
	    so = (SalesOrder) salesApi.findSalesOrderById(orderId);
	    if (so == null) {
		so = SalesFactory.createSalesOrder();
	    }
	}
	catch (SalesOrderException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw e;
	}
	finally {
	    salesApi.close();
	    tx.close();
	    salesApi = null;
	    tx = null;
	}

	return so;
    }

    /**
     * Queries the client's request (Item Selection Page) and obtains all service and merchandise items selected by the user.
     * 
     * @return String[] A list of Item Master id's as type String.
     */
    public String[] getHttpNewItemSelections() {
	ArrayList list = new ArrayList();
	String temp[] = null;

	// Get selected service items
	temp = this.request.getParameterValues(ItemConst.SEL_NEW_ITEM_SRVC);
	if (temp != null) {
	    logger.log(Level.DEBUG, "Total Service items selected: " + temp.length);
	    for (int ndx = 0; ndx < temp.length; ndx++) {
		list.add(temp[ndx]);
		logger.log(Level.DEBUG, " Selected Service item: " + temp[ndx]);
	    }
	}

	// Get selected merchandise items.
	temp = this.request.getParameterValues(ItemConst.SEL_NEW_ITEM_MERCH);
	if (temp != null) {
	    logger.log(Level.DEBUG, "Total Merchandise items selected: " + temp.length);
	    for (int ndx = 0; ndx < temp.length; ndx++) {
		list.add(temp[ndx]);
		logger.log(Level.DEBUG, "Selected Merchandise item: " + temp[ndx]);
	    }
	}
	String items[] = new String[list.size()];
	items = (String[]) list.toArray(items);
	logger.log(Level.DEBUG, "Total Items selected: " + items.length);
	return items;
    }

    /**
     * Gets transaction object.
     * 
     * @return
     */
    public Xact getXact() {
	return this.xact;
    }

    /**
     * Gets the current transaction type object.
     * 
     * @return {@link XactType}
     */
    public XactType getXactType() {
	return this.xactType;
    }

    /**
     * Gets the current transactin category object.
     * 
     * @return {@link XactCategory}
     */
    public XactCategory getXactCategory() {
	return this.xactCategory;
    }

    /**
     * Gets the items of the current transaction
     * @return List
     */
    public List getXactItems() {
	return this.xactItems;
    }

    /**
     * Sets the value of the selected row contained in the client's request.
     * 
     * @param rowThe row number selected
     */
    public void setSelectedRow(int row) {
	this.selectedRow = row;
    }

    protected void receiveClientData() throws ActionHandlerException {
    }

    protected void sendClientData() throws ActionHandlerException {
    }

    public void add() throws ActionHandlerException {
    }

    public void edit() throws ActionHandlerException {
    }

    public void save() throws ActionHandlerException {
    }

    public void delete() throws ActionHandlerException {
    }
}