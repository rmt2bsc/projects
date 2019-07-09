package com.services.handlers;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.CommonContactFactory;
import com.api.Contact;
import com.api.ContactException;

import com.api.messaging.CommonMessageHandlerImpl;
import com.api.messaging.MessagingHandlerException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.bean.VwCommonContact;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.services.CommonContactlSearchCriteriaException;
import com.xml.schema.bindings.CommonContactCriteria;
import com.xml.schema.bindings.CommonContactType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQCommonContactSearch;
import com.xml.schema.bindings.RSCommonContactSearch;
import com.xml.schema.bindings.ReplyStatusType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching one or more peronal and/or business 
 * contacts by establishing a union of the two database tables, business and person, and using various forms of 
 * selection criteria.   The incoming and outgoing data is expected to be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class CommonContactFetchHandler extends CommonMessageHandlerImpl {

    private static Logger logger = Logger.getLogger(CommonContactFetchHandler.class);

    private RQCommonContactSearch reqMessage;
    
    private boolean useCustomCriteria;
    
    private int contactid;
    
    

    /**
     * Default constructor
     *
     */
    protected CommonContactFetchHandler() {
	super();
	this.useCustomCriteria = true;
    }

    
    /**
     * 
     * @param con
     * @param request
     */
    public CommonContactFetchHandler(DatabaseConnectionBean con, Request request) {
	super(con, request);
	this.useCustomCriteria = true;
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    public String execute(Properties parms) throws MessagingHandlerException {
	super.execute(parms);
	String xml = null;
	this.reqMessage = (RQCommonContactSearch) parms.get(SoapProductBuilder.PAYLOADINSTANCE);
	try {
	    xml = this.doFetch(this.reqMessage.getCriteriaData());
	    return xml;
	}
	catch (Exception e) {
	    throw new MessagingHandlerException(e);
	}
	
    }

  /**
   *  Fetch one or more common contacts using common contact criteria.
   *  
   * @param criteria
   * @return
   * @throws SoapProcessorException
   */
    public String doFetch(CommonContactCriteria criteria) throws CommonContactlSearchCriteriaException, ContactException {

	try {
            this.validate(criteria);
        }
        catch (CommonContactlSearchCriteriaException e) {
            this.msg = "The Common contact fetch service failed due to erroneous selection criteria";
            logger.error(this.msg);
            throw new CommonContactlSearchCriteriaException(this.msg, e);
        }
	
	//Fetch contacts
        Contact api = CommonContactFactory.createApi(this.con);
        List<VwCommonContact> list = new ArrayList<VwCommonContact>();
	try {
	    String sqlCriteria = this.buildSelectionCriteria(criteria);
	    if (this.useCustomCriteria) {
                Object results = api.findContact(sqlCriteria);
                if (results != null) {
                    list = (List<VwCommonContact>) results;
                }
            }
            else {
                VwCommonContact obj = (VwCommonContact) api.findContact(this.contactid);
                if (obj != null) {
                    list.add(obj);    
                }
            }
	    String returnMsg = "Total contacts found: " + list.size();
	    return this.buildResponsePayload(list, returnMsg);
	}
	catch (ContactException e) {
	    // SOAP engine router will handle the creation of the SOAP Fault from the exception
	    this.msg = "Unable to fetch common contact information";
	    logger.error(this.msg);
	    throw new ContactException(this.msg, e);
	}
	finally {
	    api = null;
	}
    }

    
    private void validate(CommonContactCriteria parms) throws CommonContactlSearchCriteriaException {
        if ((parms.getContactId() == null || parms.getContactId().size() == 0) && parms.getContactName() == null) {
            this.msg = "Common contact service is expecting selection criteria.  Open queries are not allowed.";
            logger.error(this.msg);
            throw new CommonContactlSearchCriteriaException(this.msg);
        }
        if (parms.getContactId().size() > 0 && parms.getContactName() != null) {
            this.msg = "Contact id and contact name must mutually exclusive when submitted as common contact selection criteria";
            logger.error(this.msg);
            throw new CommonContactlSearchCriteriaException(this.msg);
        }
        return;
    }
    
    
    /**
     * 
     * @param criteria
     * @return
     */
    private String buildSelectionCriteria(CommonContactCriteria criteria) {
	StringBuffer buf = new StringBuffer();
	this.useCustomCriteria = true;
	
	// handle contact id
	if (criteria.getContactId().size() > 1) {
	    buf.append(" contact_id in(");
	    int count = 0;
	    for (BigInteger busId : criteria.getContactId()) {

		if (count > 0) {
		    buf.append(", ");
		}
		buf.append(busId.intValue());
		count++;
	    }
	    buf.append(") ");
	}
	else if (criteria.getContactId().size() == 1) {
	    this.contactid = criteria.getContactId().get(0).intValue();
	    this.useCustomCriteria = false;
	    return null;
	}
	
	// handle contact name
	if (criteria.getContactName() != null && !criteria.getContactName().equals("")) {
	    if (buf.length() > 0) {
		buf.append(" and ");
	    }
	    buf.append(" contact_name like \'");
	    buf.append(criteria.getContactName());
	    buf.append("%\' ");
	}

	// handle phone number
	if (criteria.getMainPhone() != null && !criteria.getMainPhone().equals("")) {
	    if (buf.length() > 0) {
		buf.append(" and ");
	    }
	    buf.append(" addr_phone_main like \'");
	    buf.append(criteria.getMainPhone());
	    buf.append("%\' ");
	}

	// handle city
	if (criteria.getCity() != null && !criteria.getCity().equals("")) {
	    if (buf.length() > 0) {
		buf.append(" and ");
	    }
	    buf.append(" zip_city like \'");
	    buf.append(criteria.getCity());
	    buf.append("%\' ");
	}
	
	// handle state
        if (criteria.getState() != null && !criteria.getState().equals("")) {
            if (buf.length() > 0) {
                buf.append(" and ");
            }
            buf.append(" zip_state like \'");
            buf.append(criteria.getState());
            buf.append("%\' ");
        }
        
     // handle zip code
        if (criteria.getZipcode() != null && !criteria.getZipcode().equals("")) {
            if (buf.length() > 0) {
                buf.append(" and ");
            }
            buf.append(" addr_zip = ");
            buf.append(criteria.getZipcode());
        }
	return buf.toString();
    }

    /**
     * Uses the results of the query to create the response message, RS_common_contact_search.  
     * 
     * @param results
     * @return
     */
    public String buildResponsePayload(List<VwCommonContact> results, String message) {
        ObjectFactory f = new ObjectFactory();
        RSCommonContactSearch ws = f.createRSCommonContactSearch();

        HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), "SYNC", "RESPONSE", this.getUserId());
        ws.setHeader(header);
        ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, results.size());
        ws.setReplyStatus(rst);

        List<CommonContactType> jaxbList = JaxbContactsFactory.toCommonContactTypeList(results, this.con);
        ws.getContact().addAll(jaxbList);

        String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }

 
}