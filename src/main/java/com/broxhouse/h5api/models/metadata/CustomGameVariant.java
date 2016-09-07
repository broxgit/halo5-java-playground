
package com.broxhouse.h5api.models.metadata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class CustomGameVariant implements Serializable {

    @SerializedName("BaseGameEngineType")
    @Expose
    private Integer baseGameEngineType;
    @SerializedName("GameType")
    @Expose
    private Integer gameType;
    @SerializedName("BaseGame")
    @Expose
    private BaseGame baseGame;
    @SerializedName("ScoreToWin")
    @Expose
    private Integer scoreToWin;
    @SerializedName("NumberOfLives")
    @Expose
    private Integer numberOfLives;
    @SerializedName("MatchDurationInSeconds")
    @Expose
    private Integer matchDurationInSeconds;
    @SerializedName("NumberOfRounds")
    @Expose
    private Integer numberOfRounds;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("AccessControl")
    @Expose
    private Integer accessControl;
    @SerializedName("Links")
    @Expose
    private Links links;
    @SerializedName("CreationTimeUtc")
    @Expose
    private CreationTimeUtc creationTimeUtc;
    @SerializedName("LastModifiedTimeUtc")
    @Expose
    private LastModifiedTimeUtc lastModifiedTimeUtc;
    @SerializedName("Banned")
    @Expose
    private Boolean banned;
    @SerializedName("Identity")
    @Expose
    private Identity identity;
    @SerializedName("Stats")
    @Expose
    private Stats stats;

    /**
     * 
     * @return
     *     The baseGameEngineType
     */
    public Integer getBaseGameEngineType() {
        return baseGameEngineType;
    }

    /**
     * 
     * @param baseGameEngineType
     *     The BaseGameEngineType
     */
    public void setBaseGameEngineType(Integer baseGameEngineType) {
        this.baseGameEngineType = baseGameEngineType;
    }

    /**
     * 
     * @return
     *     The gameType
     */
    public Integer getGameType() {
        return gameType;
    }

    /**
     * 
     * @param gameType
     *     The GameType
     */
    public void setGameType(Integer gameType) {
        this.gameType = gameType;
    }

    /**
     * 
     * @return
     *     The baseGame
     */
    public BaseGame getBaseGame() {
        return baseGame;
    }

    /**
     * 
     * @param baseGame
     *     The BaseGame
     */
    public void setBaseGame(BaseGame baseGame) {
        this.baseGame = baseGame;
    }

    /**
     * 
     * @return
     *     The scoreToWin
     */
    public Integer getScoreToWin() {
        return scoreToWin;
    }

    /**
     * 
     * @param scoreToWin
     *     The ScoreToWin
     */
    public void setScoreToWin(Integer scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    /**
     * 
     * @return
     *     The numberOfLives
     */
    public Integer getNumberOfLives() {
        return numberOfLives;
    }

    /**
     * 
     * @param numberOfLives
     *     The NumberOfLives
     */
    public void setNumberOfLives(Integer numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    /**
     * 
     * @return
     *     The matchDurationInSeconds
     */
    public Integer getMatchDurationInSeconds() {
        return matchDurationInSeconds;
    }

    /**
     * 
     * @param matchDurationInSeconds
     *     The MatchDurationInSeconds
     */
    public void setMatchDurationInSeconds(Integer matchDurationInSeconds) {
        this.matchDurationInSeconds = matchDurationInSeconds;
    }

    /**
     * 
     * @return
     *     The numberOfRounds
     */
    public Integer getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     * 
     * @param numberOfRounds
     *     The NumberOfRounds
     */
    public void setNumberOfRounds(Integer numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The accessControl
     */
    public Integer getAccessControl() {
        return accessControl;
    }

    /**
     * 
     * @param accessControl
     *     The AccessControl
     */
    public void setAccessControl(Integer accessControl) {
        this.accessControl = accessControl;
    }

    /**
     * 
     * @return
     *     The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The Links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     * 
     * @return
     *     The creationTimeUtc
     */
    public CreationTimeUtc getCreationTimeUtc() {
        return creationTimeUtc;
    }

    /**
     * 
     * @param creationTimeUtc
     *     The CreationTimeUtc
     */
    public void setCreationTimeUtc(CreationTimeUtc creationTimeUtc) {
        this.creationTimeUtc = creationTimeUtc;
    }

    /**
     * 
     * @return
     *     The lastModifiedTimeUtc
     */
    public LastModifiedTimeUtc getLastModifiedTimeUtc() {
        return lastModifiedTimeUtc;
    }

    /**
     * 
     * @param lastModifiedTimeUtc
     *     The LastModifiedTimeUtc
     */
    public void setLastModifiedTimeUtc(LastModifiedTimeUtc lastModifiedTimeUtc) {
        this.lastModifiedTimeUtc = lastModifiedTimeUtc;
    }

    /**
     * 
     * @return
     *     The banned
     */
    public Boolean getBanned() {
        return banned;
    }

    /**
     * 
     * @param banned
     *     The Banned
     */
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    /**
     * 
     * @return
     *     The identity
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * 
     * @param identity
     *     The Identity
     */
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    /**
     * 
     * @return
     *     The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * 
     * @param stats
     *     The Stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

}
