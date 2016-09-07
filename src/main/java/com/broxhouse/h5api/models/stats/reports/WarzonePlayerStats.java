

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WarzonePlayerStats extends SpartanRankedPlayerStats implements Serializable {
    /**
     * The maximum level the player achieved in the match.
     */
    @SerializedName("WarzoneLevel")
    private int warzoneLevel;

    /**
     * The total number of "pies" (in-game currency) the player earned in the match.
     */
    @SerializedName("TotalPiesEarned")
    private int totalPiesEarned;
}
