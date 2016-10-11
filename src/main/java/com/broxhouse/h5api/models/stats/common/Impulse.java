

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Impulse implements Serializable {

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
