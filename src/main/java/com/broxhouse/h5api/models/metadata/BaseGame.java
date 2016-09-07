
package com.broxhouse.h5api.models.metadata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class BaseGame implements Serializable {

    @SerializedName("ResourceType")
    @Expose
    private Integer resourceType;
    @SerializedName("ResourceId")
    @Expose
    private String resourceId;
    @SerializedName("OwnerType")
    @Expose
    private Integer ownerType;
    @SerializedName("Owner")
    @Expose
    private Object owner;

    /**
     * 
     * @return
     *     The resourceType
     */
    public Integer getResourceType() {
        return resourceType;
    }

    /**
     * 
     * @param resourceType
     *     The ResourceType
     */
    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 
     * @return
     *     The resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * 
     * @param resourceId
     *     The ResourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 
     * @return
     *     The ownerType
     */
    public Integer getOwnerType() {
        return ownerType;
    }

    /**
     * 
     * @param ownerType
     *     The OwnerType
     */
    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    /**
     * 
     * @return
     *     The owner
     */
    public Object getOwner() {
        return owner;
    }

    /**
     * 
     * @param owner
     *     The Owner
     */
    public void setOwner(Object owner) {
        this.owner = owner;
    }

}
