package com.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.api.business.BusinessApi;
import com.api.business.BusinessException;
import com.api.business.BusinessFactory;
import com.api.db.DatabaseException;
import com.api.db.JdbcFactory;

import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.pagination.PageCalculator;
import com.api.db.pagination.PaginationQueryResults;
import com.api.messaging.ResourceFactory;
import com.api.personal.PersonApi;
import com.api.personal.PersonException;
import com.api.personal.PersonFactory;

import com.bean.Customers;
import com.bean.Products;
import com.bean.VwBusinessAddress;
import com.bean.VwCommonContact;
import com.bean.VwPersonAddress;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;
import com.util.RMT2Base64Decoder;
import com.util.RMT2Base64Encoder;
import com.util.RMT2File;
import com.xml.schema.bindings.BusinessType;
import com.xml.schema.bindings.CommonContactType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.PersonType;
import com.xml.schema.bindings.RSBusinessContactSearch;
import com.xml.schema.bindings.RSCommonContactSearch;
import com.xml.schema.bindings.RSPersonalContactSearch;

/**
 * @author rterrell
 *
 */
public class ContactFetchTest {

    private DatabaseConnectionBean dbCon;

    private RdbmsDaoQueryHelper daoHelper;
    
    private ObjectFactory f;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.dbCon = JdbcFactory.getConnection("jdbc:sybase:Tds:rmtdaldev04:2638?ServiceName=contacts");
	this.daoHelper = new RdbmsDaoQueryHelper(this.dbCon);
	this.f = new ObjectFactory();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.dbCon.close();
	this.f = null;
    }

    @Test
    public void testBusinessFetch() {
        BusinessApi api = BusinessFactory.createBusinessApi(this.dbCon);
        VwBusinessAddress b = null;
        try {
            b = (VwBusinessAddress) api.findBusAddress(1456);
        }
        catch (BusinessException e1) {
            e1.printStackTrace();
            return;
        }
        JaxbContactsFactory cf = new JaxbContactsFactory(this.dbCon);
        BusinessType bt = cf.createBusinessTypeInstance(b);
        try {
            RSBusinessContactSearch ws = this.f.createRSBusinessContactSearch();
            ws.getBusinessList().add(bt);
            String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
            Assert.assertNotNull(msg);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testPersonFetch() {
        PersonApi api = PersonFactory.createPersonApi(this.dbCon);
        VwPersonAddress p = null;
        try {
            p = (VwPersonAddress) api.findPerAddress(791);
        }
        catch (PersonException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }
        JaxbContactsFactory cf = new JaxbContactsFactory(this.dbCon);
        PersonType pt = cf.createPersonalTypeInstance(p);
        try {
            RSPersonalContactSearch ws = this.f.createRSPersonalContactSearch();
            ws.getPersonList().add(pt);
            String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
            Assert.assertNotNull(msg);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCommonFetch() {
	VwCommonContact obj = new VwCommonContact();
	obj.addLikeClause(VwCommonContact.PROP_CONTACTNAME, "A");
	List<VwCommonContact> results = null;
	try {
	    results = this.daoHelper.retrieveList(obj);
	    List<CommonContactType> list = JaxbContactsFactory.toCommonContactTypeList(results, this.dbCon);
	    RSCommonContactSearch ws = this.f.createRSCommonContactSearch();
	    ws.getContact().addAll(list);
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    Assert.assertNotNull(results);
	    Assert.assertTrue(results.size() > 0);
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	}
    }

}   