

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Enemy {
    /**
     * The Base ID for the enemy.
     */
    @SerializedName("BaseId")
    private long baseId;

    /**
     * The attachments (variants) for the enemy.
     */
    @SerializedName("Attachments")
    private List<Long> attachments;

    public long getBaseId() {
        return baseId;
    }

    public List<Long> getAttachments() {
        return attachments;
    }
}
