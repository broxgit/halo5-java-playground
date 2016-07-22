package com.broxhouse.h5api.models.stats.arenastatsold;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ImpulseStatCount {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Count")
    @Expose
    private String count;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The Id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count The Count
     */
    public void setCount(String count) {
        this.count = count;
    }

}
