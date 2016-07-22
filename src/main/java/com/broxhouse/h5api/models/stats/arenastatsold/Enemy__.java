package com.broxhouse.h5api.models.stats.arenastatsold;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Enemy__ {

    @SerializedName("BaseId")
    @Expose
    private String baseId;
    @SerializedName("Attachments")
    @Expose
    private List<String> attachments = new ArrayList<String>();

    /**
     * @return The baseId
     */
    public String getBaseId() {
        return baseId;
    }

    /**
     * @param baseId The BaseId
     */
    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    /**
     * @return The attachments
     */
    public List<String> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments The Attachments
     */
    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }



}
