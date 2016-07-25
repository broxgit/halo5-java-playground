

package com.broxhouse.h5api.models.stats.servicerecords;

import com.broxhouse.h5api.models.stats.common.Player;
import com.google.gson.annotations.SerializedName;

public class BaseServiceRecordResult {

    /**
     * Information about the player for whom this data was returned.
     */
    @SerializedName("PlayerId")
    private Player player;

    /**
     * The player's Spartan Rank. Zero for campaign.
     */
    @SerializedName("SpartanRank")
    private int spartanRank;

    /**
     * The player's XP. Zero for campaign.
     */
    @SerializedName("Xp")
    private long xp;

    public Player getPlayer() {
        return player;
    }

    public int getSpartanRank() {
        return spartanRank;
    }

    public long getXp() {
        return xp;
    }
}
