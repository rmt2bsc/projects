package com;

/**
 * Manages the the object id and object name of the the ORM resource 
 * that is to be generated from some external data source.
 * 
 * @author RTerrell
 *
 */
public class OrmObjectData {
    private String objName;
    private int objId;
    
    
    /**
     * Get the object id.
     * @return the int
     */
    public int getObjectId() {
        return objId;
    }
    /**
     * Set the object id.
     * @param objId int
     */
    public void setObjectId(int objectId) {
        this.objId = objectId;
    }
    /**
     * Get the object name.
     * @return the String
     */
    public String getObjectName() {
        return objName;
    }
    /**
     * Set the object name.
     * @param objName String
     */
    public void setObjectName(String objectName) {
        this.objName = objectName;
    }

}
