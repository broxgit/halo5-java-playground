

package com.broxhouse.h5api.models.stats.reports;

import com.broxhouse.h5api.models.stats.common.Player;
import com.google.gson.annotations.SerializedName;

public class TeamPlayer {

    @SerializedName("Player")
    private Player player;

    /**
     * The ID of the team that the player was on when the match ended. Zero for
     * campaign games.
     */
    @SerializedName("TeamId")
    private int teamId;

    /**
     * The player's team-agnostic ranking in this match.
     */
    @SerializedName("Rank")
    private int rank;

    /**
     * Indicates what result the player received at the conclusion of the match.
     * Options are:
     *   Did Not Finish = 0,
     *   Lost = 1,
     *   Tied = 2,
     *   Won = 3.
     * Did Not Finish: The player was not present when the match ended.
     * Lost: The player was on a team that was assigned a loss, typically this is
     * when a team does not have rank = 1.
     * Won: The player was on the team that was assigned the win, typically this is
     * the team that has rank = 1.
     * Tied: The player was on the team that was awarded a tie. Typically this is
     * when the player is on the team with rank = 1, and there is at least one
     * other team with rank = 1. Ties are only for rank = 1 teams. Consider the
     * scenario when exactly one team is rank = 1, and two teams are rank = 2.
     * Players on the rank=1 team will have "Won", players on the rank = 2 teams
     * will have "Lost". For ties, this documentation states 'typically' because
     * the game may have unique rules for multi-team and FFA scenarios, in which
     * multiple teams are awarded a win.
     */
    @SerializedName("Result")
    private int result;

    /**
     * The number of enemy kills the player had during this match. This includes
     * other Spartans and Enemy AI.
     */
    @SerializedName("TotalKills")
    private int totalKills;

    /**
     * The number of times this player died during the match.
     */
    @SerializedName("TotalDeaths")
    private int totalDeaths;

    /**
     * The number of assists credited to the player during the match. This includes
     * other Spartans and Enemy AI.
     */
    @SerializedName("TotalAssists")
    private int totalAssists;

    public Player getPlayer() {
        return player;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getRank() {
        return rank;
    }

    public int getResult() {
        return result;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public void setTotalKills(int kills)
    {
        this.totalKills = kills;
    }

    public void setTotalDeaths(int deaths)
    {
        this.totalDeaths = deaths;
    }
}
