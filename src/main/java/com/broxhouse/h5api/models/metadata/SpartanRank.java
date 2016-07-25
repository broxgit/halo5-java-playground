

package com.broxhouse.h5api.models.metadata;

import com.google.gson.annotations.SerializedName;

public class SpartanRank {

    /**
     * The amount of XP required to enter this rank.
     */
    private int startXp;

    /**
     * The reward the player will receive for earning this Spartan Rank.
     */
    private Reward reward;

    /**
     * The ID that uniquely identifies this Spartan Rank.
     */
    private long id;

    /**
     * Internal use only. Do not use.
     */
    @SerializedName("contentId")
    private String contentId;

    public int getStartXp() {
        return startXp;
    }

    public Reward getReward() {
        return reward;
    }

    public long getId() {
        return id;
    }
}
