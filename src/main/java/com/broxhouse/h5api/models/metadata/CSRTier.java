

package com.broxhouse.h5api.models.metadata;

import com.google.gson.annotations.SerializedName;

public class CSRTier {

    /**
     * An image to use as the icon for th tier.
     */
    private String iconImageUrl;

    /**
     * An ID that identifies the tier.
     */
    private long id;

    /**
     * Internal use only. Do not use.
     */
    @SerializedName("contentId")
    private String contentId;

    public String getIconImageUrl() {
        return iconImageUrl;
    }

    public long getId() {
        return id;
    }
}
