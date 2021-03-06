//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.23 at 11:17:17 PM CDT 
//


package com.xml.schema.bindings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for xact_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xact_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xact_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="xact_type_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="xact_type_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xact_subtype_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="xact_amount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="xact_date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xact_date_str" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xact_reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="account_no" type="{http://www.w3.org/2001/XMLSchema}NCName"/>
 *         &lt;element name="business_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="business_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="confirm_no" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="document_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="invoice_no" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="item_total" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="items" type="{}xact_item_type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xact_type", propOrder = {
    "xactId",
    "xactTypeId",
    "xactTypeName",
    "xactSubtypeId",
    "xactAmount",
    "xactDate",
    "xactDateStr",
    "xactReason",
    "accountNo",
    "businessId",
    "businessName",
    "confirmNo",
    "documentId",
    "invoiceNo",
    "itemTotal",
    "items"
})
public class XactType {

    @XmlElement(name = "xact_id", required = true)
    protected BigInteger xactId;
    @XmlElement(name = "xact_type_id", required = true)
    protected BigInteger xactTypeId;
    @XmlElement(name = "xact_type_name", required = true)
    protected String xactTypeName;
    @XmlElement(name = "xact_subtype_id", required = true)
    protected BigInteger xactSubtypeId;
    @XmlElement(name = "xact_amount", required = true)
    protected BigDecimal xactAmount;
    @XmlElement(name = "xact_date", required = true)
    protected String xactDate;
    @XmlElement(name = "xact_date_str", required = true)
    protected String xactDateStr;
    @XmlElement(name = "xact_reason", required = true)
    protected String xactReason;
    @XmlElement(name = "account_no", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String accountNo;
    @XmlElement(name = "business_id", required = true)
    protected BigInteger businessId;
    @XmlElement(name = "business_name", required = true)
    protected String businessName;
    @XmlElement(name = "confirm_no", required = true)
    protected String confirmNo;
    @XmlElement(name = "document_id", required = true)
    protected BigInteger documentId;
    @XmlElement(name = "invoice_no", required = true)
    protected String invoiceNo;
    @XmlElement(name = "item_total", required = true)
    protected BigDecimal itemTotal;
    protected List<XactItemType> items;

    /**
     * Gets the value of the xactId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getXactId() {
        return xactId;
    }

    /**
     * Sets the value of the xactId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setXactId(BigInteger value) {
        this.xactId = value;
    }

    /**
     * Gets the value of the xactTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getXactTypeId() {
        return xactTypeId;
    }

    /**
     * Sets the value of the xactTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setXactTypeId(BigInteger value) {
        this.xactTypeId = value;
    }

    /**
     * Gets the value of the xactTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXactTypeName() {
        return xactTypeName;
    }

    /**
     * Sets the value of the xactTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXactTypeName(String value) {
        this.xactTypeName = value;
    }

    /**
     * Gets the value of the xactSubtypeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getXactSubtypeId() {
        return xactSubtypeId;
    }

    /**
     * Sets the value of the xactSubtypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setXactSubtypeId(BigInteger value) {
        this.xactSubtypeId = value;
    }

    /**
     * Gets the value of the xactAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getXactAmount() {
        return xactAmount;
    }

    /**
     * Sets the value of the xactAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setXactAmount(BigDecimal value) {
        this.xactAmount = value;
    }

    /**
     * Gets the value of the xactDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXactDate() {
        return xactDate;
    }

    /**
     * Sets the value of the xactDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXactDate(String value) {
        this.xactDate = value;
    }

    /**
     * Gets the value of the xactDateStr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXactDateStr() {
        return xactDateStr;
    }

    /**
     * Sets the value of the xactDateStr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXactDateStr(String value) {
        this.xactDateStr = value;
    }

    /**
     * Gets the value of the xactReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXactReason() {
        return xactReason;
    }

    /**
     * Sets the value of the xactReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXactReason(String value) {
        this.xactReason = value;
    }

    /**
     * Gets the value of the accountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Sets the value of the accountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    /**
     * Gets the value of the businessId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBusinessId() {
        return businessId;
    }

    /**
     * Sets the value of the businessId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBusinessId(BigInteger value) {
        this.businessId = value;
    }

    /**
     * Gets the value of the businessName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * Sets the value of the businessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessName(String value) {
        this.businessName = value;
    }

    /**
     * Gets the value of the confirmNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmNo() {
        return confirmNo;
    }

    /**
     * Sets the value of the confirmNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmNo(String value) {
        this.confirmNo = value;
    }

    /**
     * Gets the value of the documentId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDocumentId() {
        return documentId;
    }

    /**
     * Sets the value of the documentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDocumentId(BigInteger value) {
        this.documentId = value;
    }

    /**
     * Gets the value of the invoiceNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Sets the value of the invoiceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNo(String value) {
        this.invoiceNo = value;
    }

    /**
     * Gets the value of the itemTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getItemTotal() {
        return itemTotal;
    }

    /**
     * Sets the value of the itemTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setItemTotal(BigDecimal value) {
        this.itemTotal = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XactItemType }
     * 
     * 
     */
    public List<XactItemType> getItems() {
        if (items == null) {
            items = new ArrayList<XactItemType>();
        }
        return this.items;
    }

}
