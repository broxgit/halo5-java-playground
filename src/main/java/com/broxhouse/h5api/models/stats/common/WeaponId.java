

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeaponId extends WeaponStats {

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
