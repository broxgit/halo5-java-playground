

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

public class Team {
    /**
     * The ID for the team. The team's ID dictates the team's color. Team colors
     * are available via the Metadata API.
     */
    @SerializedName("Id")
    private int id;

    /**
     * The team's score at the end of the match. The way the score is determined is
     * based off the game base variant being played:
     *   Breakout = number of rounds won,
     *   CTF = number of flag captures,
     *   Slayer = number of kills,
     *   Strongholds = number of points,
     *   Warzone = number of points.
     */
    @SerializedName("Score")
    private long score;

    /**
     * The team's rank at the end of the match.
     */
    @SerializedName("Rank")
    private int rank;

    public int getId() {
        return id;
    }

    public long getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }
}
