package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TopGameBaseVariant {

    @SerializedName("GameBaseVariantRank")
    @Expose
    private String gameBaseVariantRank;
    @SerializedName("NumberOfMatchesCompleted")
    @Expose
    private String numberOfMatchesCompleted;
    @SerializedName("GameBaseVariantId")
    @Expose
    private String gameBaseVariantId;
    @SerializedName("NumberOfMatchesWon")
    @Expose
    private String numberOfMatchesWon;

    /**
     * @return The gameBaseVariantRank
     */
    public String getGameBaseVariantRank() {
        return gameBaseVariantRank;
    }

    /**
     * @param gameBaseVariantRank The GameBaseVariantRank
     */
    public void setGameBaseVariantRank(String gameBaseVariantRank) {
        this.gameBaseVariantRank = gameBaseVariantRank;
    }

    /**
     * @return The numberOfMatchesCompleted
     */
    public String getNumberOfMatchesCompleted() {
        return numberOfMatchesCompleted;
    }

    /**
     * @param numberOfMatchesCompleted The NumberOfMatchesCompleted
     */
    public void setNumberOfMatchesCompleted(String numberOfMatchesCompleted) {
        this.numberOfMatchesCompleted = numberOfMatchesCompleted;
    }

    /**
     * @return The gameBaseVariantId
     */
    public String getGameBaseVariantId() {
        return gameBaseVariantId;
    }

    /**
     * @param gameBaseVariantId The GameBaseVariantId
     */
    public void setGameBaseVariantId(String gameBaseVariantId) {
        this.gameBaseVariantId = gameBaseVariantId;
    }

    /**
     * @return The numberOfMatchesWon
     */
    public String getNumberOfMatchesWon() {
        return numberOfMatchesWon;
    }

    /**
     * @param numberOfMatchesWon The NumberOfMatchesWon
     */
    public void setNumberOfMatchesWon(String numberOfMatchesWon) {
        this.numberOfMatchesWon = numberOfMatchesWon;
    }


}
