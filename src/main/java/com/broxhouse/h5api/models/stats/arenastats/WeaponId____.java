package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class WeaponId____ {

    @SerializedName("StockId")
    @Expose
    private String stockId;
    @SerializedName("Attachments")
    @Expose
    private List<String> attachments = new ArrayList<String>();

    /**
     * @return The stockId
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * @param stockId The StockId
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
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
