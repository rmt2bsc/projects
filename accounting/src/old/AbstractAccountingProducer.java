package com.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractExternalServerAction;

import com.util.SystemException;
import com.util.RMT2Utility;

/**
 * Abstract class for all contact related service producers.
 *   
 * @author RTerrell
 * @deprecated use {@link com.services.AccountingServiceAction}
 *
 */
public abstract class AbstractAccountingProducer extends AbstractExternalServerAction {
    private Logger logger;

    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public AbstractAccountingProducer() throws SystemException {
	this.logger = Logger.getLogger("AbstractAccountingProducer");
    }

    /**
     * Provides basic functionality for retreiving the service request paramaters 
     * to be used by descendent.   The parameters are basically transferred from the
     * HttpServletRequest object to a Properties collection which in turn is stored
     * internally as the service's input data.
     * 
     * @throws ActionHandlerException
     */
    public void receiveClientData() throws ActionHandlerException {
	Properties prop = RMT2Utility.getRequestData(this.request);
	this.inData = prop;
	logger.log(Level.DEBUG, "Service producer parameters accessed successfully");
    }
}
