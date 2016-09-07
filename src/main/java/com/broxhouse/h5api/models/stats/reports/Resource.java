

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Resource implements Serializable {

    /**
     * 2 - GameVariant
     * 3 - MatchVariant
     */
    @SerializedName("ResourceType")
    private int resourceType;

    /**
     * The ID of the resource. Can be linked to metadata.
     */
    @SerializedName("ResourceId")
    private String resourceId;

    /**
     * The source of the map variant. Options are:
     *   Unknown = 0
     *   User-generated = 1 and 2,
     *   Official = 3.
     */
    @SerializedName("OwnerType")
    private String ownerType;

    /**
     * The owner. Usually set to null.
     */
    @SerializedName("Owner")
    private String owner;

    public int getResourceType() {
        return resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public String getOwner() {
        return owner;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
