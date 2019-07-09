package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_database_access database table/view.
 *
 * @author Roy Terrell.
 */
public class UserDatabaseAccess extends OrmBean {

/** The javabean property equivalent of database column user_database_access.id */
  private int id;
/** The javabean property equivalent of database column user_database_access.user_login_id */
  private int userLoginId;
/** The javabean property equivalent of database column user_database_access.user_database_id */
  private int userDatabaseId;
/** The javabean property equivalent of database column user_database_access.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column user_database_access.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column user_database_access.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public UserDatabaseAccess() throws SystemException {
  }
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable userLoginId
 *
 * @author Roy Terrell.
 */
  public void setUserLoginId(int value) {
    this.userLoginId = value;
  }
/**
 * Gets the value of member variable userLoginId
 *
 * @author Roy Terrell.
 */
  public int getUserLoginId() {
    return this.userLoginId;
  }
/**
 * Sets the value of member variable userDatabaseId
 *
 * @author Roy Terrell.
 */
  public void setUserDatabaseId(int value) {
    this.userDatabaseId = value;
  }
/**
 * Gets the value of member variable userDatabaseId
 *
 * @author Roy Terrell.
 */
  public int getUserDatabaseId() {
    return this.userDatabaseId;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}