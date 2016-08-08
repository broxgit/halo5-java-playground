

package com.broxhouse.h5api.models.stats.reports;

import com.broxhouse.h5api.models.stats.common.FlexibleStats;
import com.broxhouse.h5api.models.stats.common.Player;
import com.google.gson.annotations.SerializedName;

public class BasePlayerStats extends BaseStats {

    /**
     * The game base variant specific stats for this match. Flexible stats are
     * available via the Metadata API.
     */
    @SerializedName("FlexibleStats")
    private FlexibleStats flexibleStats;

    @SerializedName("Player")
    private Player player;

    /**
     * The ID of the team that the player was on when the match ended.
     */
    @SerializedName("TeamId")
    private int teamId;

    /**
     * The player's team-agnostic ranking.
     */
    @SerializedName("Rank")
    private int rank;

    /**
     * Indicates whether the player was present in the match when it ended.
     */
    @SerializedName("DNF")
    private boolean dnf;

    /**
     * The player's average lifetime.
     */
    @SerializedName("AvgLifeTimeOfPlayer")
    private String avgLifeTimeOfPlayer;

    public FlexibleStats getFlexibleStats() {
        return flexibleStats;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getRank() {
        return rank;
    }

    public boolean isDnf() {
        return dnf;
    }

    public String getAvgLifeTimeOfPlayer() {
        return avgLifeTimeOfPlayer;
    }

}
