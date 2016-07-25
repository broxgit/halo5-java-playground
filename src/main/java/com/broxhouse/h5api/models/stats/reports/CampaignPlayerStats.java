

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

public class CampaignPlayerStats extends BasePlayerStats {
    /**
     * The player's biggest score due to a kill.
     */
    @SerializedName("BiggestKillScore")
    private int biggestKillScore;

    /**
     * The player's score.
     */
    @SerializedName("Score")
    private long score;

    public int getBiggestKillScore() {
        return biggestKillScore;
    }

    public long getScore() {
        return score;
    }
}
