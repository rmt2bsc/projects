//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.23 at 11:17:17 PM CDT 
//


package com.xml.schema.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="header" type="{}header_type"/>
 *         &lt;element name="reply_status" type="{}reply_status_type" minOccurs="0"/>
 *         &lt;element name="session_token" type="{}user_session_type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "replyStatus",
    "sessionToken"
})
@XmlRootElement(name = "RS_authentication_login")
public class RSAuthenticationLogin {

    @XmlElement(required = true)
    protected HeaderType header;
    @XmlElement(name = "reply_status")
    protected ReplyStatusType replyStatus;
    @XmlElement(name = "session_token")
    protected UserSessionType sessionToken;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType }
     *     
     */
    public HeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType }
     *     
     */
    public void setHeader(HeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the replyStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyStatusType }
     *     
     */
    public ReplyStatusType getReplyStatus() {
        return replyStatus;
    }

    /**
     * Sets the value of the replyStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyStatusType }
     *     
     */
    public void setReplyStatus(ReplyStatusType value) {
        this.replyStatus = value;
    }

    /**
     * Gets the value of the sessionToken property.
     * 
     * @return
     *     possible object is
     *     {@link UserSessionType }
     *     
     */
    public UserSessionType getSessionToken() {
        return sessionToken;
    }

    /**
     * Sets the value of the sessionToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserSessionType }
     *     
     */
    public void setSessionToken(UserSessionType value) {
        this.sessionToken = value;
    }

}
