

package com.broxhouse.h5api.models.metadata;

import com.google.gson.annotations.SerializedName;

public class Impulse {
    /**
     * Internal use. The non-localized name of the impulse.
     */
    private String internalName;

    /**
     * The ID that uniquely identifies this impulse.
     */
    private long id;

    /**
     * Internal use only. Do not use.
     */
    @SerializedName("contentId")
    private String contentId;

    public long getId() {
        return id;
    }

    public String getInternalName() {
        return internalName;
    }
}
