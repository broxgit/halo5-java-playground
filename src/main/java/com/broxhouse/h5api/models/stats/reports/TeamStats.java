

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamStats {

    public static class RoundStats {
        /**
         * The round number this entry pertains to.
         */
        @SerializedName("RoundNumber")
        private int roundNumber;

        /**
         * The end rank for the team this round.
         */
        @SerializedName("Rank")
        private int rank;

        /**
         * The end score for the team this round.
         */
        @SerializedName("Score")
        private long score;

        public int getRoundNumber() {
            return roundNumber;
        }

        public int getRank() {
            return rank;
        }

        public long getScore() {
            return score;
        }
    }


    /**
     * The ID for the team.
     */
    @SerializedName("TeamId")
    private int teamId;

    /** The team's score at the end of the match. The way the score is determined is
     * based off the game base variant being played:
     *  Breakout = number of rounds won,
     *  CTF = number of flag captures,
     *  Slayer = number of kills,
     *  Strongholds = number of points,
     *  Warzone = number of points.
     */
    @SerializedName("Score")
    private long score;

    /**
     * The team's rank at the end of the match.
     */
    @SerializedName("Rank")
    private int rank;

    /**
     * The set of round stats for the team.
     */
    @SerializedName("RoundStats")
    private List<RoundStats> roundStats;

    public int getTeamId() {
        return teamId;
    }

    public long getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    public List<RoundStats> getRoundStats() {
        return roundStats;
    }
}
