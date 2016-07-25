package com.broxhouse.h5api.models.stats;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeaponId
{
    /**
     * The ID of the weapon. Weapons are available via the Metadata API.
     */
    @SerializedName("StockId")
    private long stockId;

    /**
     * Any attachments the weapon had.
     */
    @SerializedName("Attachments")
    private List<Integer> attachments;

    public long getStockId() {
        return stockId;
    }

    public List<Integer> getAttachments() {
        return attachments;
    }
}
