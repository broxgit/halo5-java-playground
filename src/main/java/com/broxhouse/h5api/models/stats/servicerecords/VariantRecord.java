

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

public class VariantRecord {
    /**
     * Rank between 1-3
     */
    @SerializedName("GameBaseVariantRank")
    private int gameBaseVariantRank;

    /**
     * Number of games played in game base variant
     */
    @SerializedName("NumberOfMatchesCompleted")
    private long numberOfMatchesCompleted;

    /**
     * Id of the game base variant
     */
    @SerializedName("GameBaseVariantId")
    private String gameBaseVariantId;

    /**
     * Number of matches won on game base variant
     */
    @SerializedName("NumberOfMatchesWon")
    private long numberOfMatchesWon;

    public int getGameBaseVariantRank() {
        return gameBaseVariantRank;
    }

    public long getNumberOfMatchesCompleted() {
        return numberOfMatchesCompleted;
    }

    public String getGameBaseVariantId() {
        return gameBaseVariantId;
    }

    public long getNumberOfMatchesWon() {
        return numberOfMatchesWon;
    }
}
