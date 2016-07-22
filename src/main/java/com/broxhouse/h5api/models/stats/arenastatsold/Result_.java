package com.broxhouse.h5api.models.stats.arenastatsold;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Result_ {

    @SerializedName("ArenaStats")
    @Expose
    private ArenaStats arenaStats;
    @SerializedName("PlayerId")
    @Expose
    private PlayerId playerId;
    @SerializedName("SpartanRank")
    @Expose
    private String spartanRank;
    @SerializedName("Xp")
    @Expose
    private String xp;

    /**
     * @return The arenaStats
     */
    public ArenaStats getArenaStats() {
        return arenaStats;
    }

    /**
     * @param arenaStats The ArenaStats
     */
    public void setArenaStats(ArenaStats arenaStats) {
        this.arenaStats = arenaStats;
    }

    /**
     * @return The playerId
     */
    public PlayerId getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId The PlayerId
     */
    public void setPlayerId(PlayerId playerId) {
        this.playerId = playerId;
    }

    /**
     * @return The spartanRank
     */
    public String getSpartanRank() {
        return spartanRank;
    }

    /**
     * @param spartanRank The SpartanRank
     */
    public void setSpartanRank(String spartanRank) {
        this.spartanRank = spartanRank;
    }

    /**
     * @return The xp
     */
    public String getXp() {
        return xp;
    }

    /**
     * @param xp The Xp
     */
    public void setXp(String xp) {
        this.xp = xp;
    }


}
