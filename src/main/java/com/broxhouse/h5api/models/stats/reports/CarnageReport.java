package com.broxhouse.h5api.models.stats.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CarnageReport implements Serializable {

    @SerializedName("PlayerStats")
    @Expose
    private List<PlayerStat> playerStats = new ArrayList<PlayerStat>();
    @SerializedName("TeamStats")
    @Expose
    private List<TeamStat> teamStats = new ArrayList<TeamStat>();
    @SerializedName("IsMatchOver")
    @Expose
    private Boolean isMatchOver;
    @SerializedName("TotalDuration")
    @Expose
    private String totalDuration;
    @SerializedName("MapVariantId")
    @Expose
    private String mapVariantId;
    @SerializedName("GameVariantId")
    @Expose
    private String gameVariantId;
    @SerializedName("PlaylistId")
    @Expose
    private String playlistId;
    @SerializedName("MapId")
    @Expose
    private String mapId;
    @SerializedName("GameBaseVariantId")
    @Expose
    private String gameBaseVariantId;
    @SerializedName("IsTeamGame")
    @Expose
    private Boolean isTeamGame;
    @SerializedName("SeasonId")
    @Expose
    private Object seasonId;
    @SerializedName("GameVariantResourceId")
    @Expose
    private GameVariantResourceId gameVariantResourceId;
    @SerializedName("MapVariantResourceId")
    @Expose
    private MapVariantResourceId mapVariantResourceId;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {

        this.matchId = matchId;
    }

    @SerializedName("matchID")
    private String matchId;

    /**
     *
     * @return
     * The playerStats
     */
    public List<PlayerStat> getPlayerStats() {
        return playerStats;
    }

    /**
     *
     * @param playerStats
     * The PlayerStats
     */
    public void setPlayerStats(List<PlayerStat> playerStats) {
        this.playerStats = playerStats;
    }

    /**
     *
     * @return
     * The teamStats
     */
    public List<TeamStat> getTeamStats() {
        return teamStats;
    }

    /**
     *
     * @param teamStats
     * The TeamStats
     */
    public void setTeamStats(List<TeamStat> teamStats) {
        this.teamStats = teamStats;
    }

    /**
     *
     * @return
     * The isMatchOver
     */
    public Boolean getIsMatchOver() {
        return isMatchOver;
    }

    /**
     *
     * @param isMatchOver
     * The IsMatchOver
     */
    public void setIsMatchOver(Boolean isMatchOver) {
        this.isMatchOver = isMatchOver;
    }

    /**
     *
     * @return
     * The totalDuration
     */
    public String getTotalDuration() {
        return totalDuration;
    }

    /**
     *
     * @param totalDuration
     * The TotalDuration
     */
    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    /**
     *
     * @return
     * The mapVariantId
     */
    public String getMapVariantId() {
        return mapVariantId;
    }

    /**
     *
     * @param mapVariantId
     * The MapVariantId
     */
    public void setMapVariantId(String mapVariantId) {
        this.mapVariantId = mapVariantId;
    }

    /**
     *
     * @return
     * The gameVariantId
     */
    public String getGameVariantId() {
        return gameVariantId;
    }

    /**
     *
     * @param gameVariantId
     * The GameVariantId
     */
    public void setGameVariantId(String gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    /**
     *
     * @return
     * The playlistId
     */
    public String getPlaylistId() {
        return playlistId;
    }

    /**
     *
     * @param playlistId
     * The PlaylistId
     */
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    /**
     *
     * @return
     * The mapId
     */
    public String getMapId() {
        return mapId;
    }

    /**
     *
     * @param mapId
     * The MapId
     */
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    /**
     *
     * @return
     * The gameBaseVariantId
     */
    public String getGameBaseVariantId() {
        return gameBaseVariantId;
    }

    /**
     *
     * @param gameBaseVariantId
     * The GameBaseVariantId
     */
    public void setGameBaseVariantId(String gameBaseVariantId) {
        this.gameBaseVariantId = gameBaseVariantId;
    }

    /**
     *
     * @return
     * The isTeamGame
     */
    public Boolean getIsTeamGame() {
        return isTeamGame;
    }

    /**
     *
     * @param isTeamGame
     * The IsTeamGame
     */
    public void setIsTeamGame(Boolean isTeamGame) {
        this.isTeamGame = isTeamGame;
    }

    /**
     *
     * @return
     * The seasonId
     */
    public Object getSeasonId() {
        return seasonId;
    }

    /**
     *
     * @param seasonId
     * The SeasonId
     */
    public void setSeasonId(Object seasonId) {
        this.seasonId = seasonId;
    }

    /**
     *
     * @return
     * The gameVariantResourceId
     */
    public GameVariantResourceId getGameVariantResourceId() {
        return gameVariantResourceId;
    }

    /**
     *
     * @param gameVariantResourceId
     * The GameVariantResourceId
     */
    public void setGameVariantResourceId(GameVariantResourceId gameVariantResourceId) {
        this.gameVariantResourceId = gameVariantResourceId;
    }

    /**
     *
     * @return
     * The mapVariantResourceId
     */
    public MapVariantResourceId getMapVariantResourceId() {
        return mapVariantResourceId;
    }

    /**
     *
     * @param mapVariantResourceId
     * The MapVariantResourceId
     */
    public void setMapVariantResourceId(MapVariantResourceId mapVariantResourceId) {
        this.mapVariantResourceId = mapVariantResourceId;
    }

}