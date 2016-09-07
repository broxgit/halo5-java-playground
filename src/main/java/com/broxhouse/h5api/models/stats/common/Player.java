

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Player implements Serializable {
    /**
     *  The player's gamertag.
     */
    @SerializedName("Gamertag")
    private String gamertag;

    /**
     * Internal use only. This will always be null.
     */
    @SerializedName("Xuid")
    private String xuid;

    public String getGamertag() {
        return gamertag;
    }

    public String getXuid() {
        return xuid;
    }
}
