package com.broxhouse.h5api.models.stats.matches;

import com.broxhouse.h5api.models.metadata.GameVariant;
import com.broxhouse.h5api.models.metadata.MapVariant;
import com.broxhouse.h5api.models.stats.common.Player;
import com.broxhouse.h5api.models.stats.reports.Team;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brock Berrett on 7/28/2016.
 */
public class Result {
//    @SerializedName("Links")
//    @Expose
//    private Links links;
    @SerializedName("Id")
    @Expose
    private Match.Id id;
    @SerializedName("HopperId")
    @Expose
    private String hopperId;
    @SerializedName("MapId")
    @Expose
    private String mapId;
    @SerializedName("MapVariant")
    @Expose
    private MapVariant mapVariant;
    @SerializedName("GameBaseVariantId")
    @Expose
    private String gameBaseVariantId;
    @SerializedName("GameVariant")
    @Expose
    private GameVariant gameVariant;
    @SerializedName("MatchDuration")
    @Expose
    private String matchDuration;
//    @SerializedName("MatchCompletedDate")
//    @Expose
//    private MatchCompletedDate matchCompletedDate;
    @SerializedName("Teams")
    @Expose
    private List<Team> teams;
    @SerializedName("Players")
    @Expose
    private List<Player> players;
    @SerializedName("IsTeamGame")
    @Expose
    private Boolean isTeamGame;
    @SerializedName("SeasonId")
    @Expose
    private String seasonId;
    @SerializedName("MatchCompletedDateFidelity")
    @Expose
    private Integer matchCompletedDateFidelity;

//    /**
//     *
//     * @return
//     * The links
//     */
//    public Links getLinks() {
//        return links;
//    }

    /**
     *
     * @return
     * The id
     */
    public Match.Id getId() {
        return id;
    }

//    /**
//     *
//     * @param id
//     * The Id
//     */
//    public void setId(Match.Id id) {
//        this.id = id;
//    }

    /**
     *
     * @return
     * The hopperId
     */
    public String getHopperId() {
        return hopperId;
    }

//    /**
//     *
//     * @param hopperId
//     * The HopperId
//     */
//    public void setHopperId(String hopperId) {
//        this.hopperId = hopperId;
//    }

    /**
     *
     * @return
     * The mapId
     */
    public String getMapId() {
        return mapId;
    }

//    /**
//     *
//     * @param mapId
//     * The MapId
//     */
//    public void setMapId(String mapId) {
//        this.mapId = mapId;
//    }

    /**
     *
     * @return
     * The mapVariant
     */
    public MapVariant getMapVariant() {
        return mapVariant;
    }

//    /**
//     *
//     * @param mapVariant
//     * The MapVariant
//     */
//    public void setMapVariant(MapVariant mapVariant) {
//        this.mapVariant = mapVariant;
//    }

    /**
     *
     * @return
     * The gameBaseVariantId
     */
    public String getGameBaseVariantId() {
        return gameBaseVariantId;
    }
//
//    /**
//     *
//     * @param gameBaseVariantId
//     * The GameBaseVariantId
//     */
//    public void setGameBaseVariantId(String gameBaseVariantId) {
//        this.gameBaseVariantId = gameBaseVariantId;
//    }

    /**
     *
     * @return
     * The gameVariant
     */
    public GameVariant getGameVariant() {
        return gameVariant;
    }

    /**
     *
     * @param gameVariant
     * The GameVariant
     */
    public void setGameVariant(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
    }

    /**
     *
     * @return
     * The matchDuration
     */
    public String getMatchDuration() {
        return matchDuration;
    }

    /**
     *
     * @param matchDuration
     * The MatchDuration
     */
    public void setMatchDuration(String matchDuration) {
        this.matchDuration = matchDuration;
    }

//    /**
//     *
//     * @return
//     * The matchCompletedDate
//     */
//    public MatchCompletedDate getMatchCompletedDate() {
//        return matchCompletedDate;
//    }
//
//    /**
//     *
//     * @param matchCompletedDate
//     * The MatchCompletedDate
//     */
//    public void setMatchCompletedDate(MatchCompletedDate matchCompletedDate) {
//        this.matchCompletedDate = matchCompletedDate;
//    }

    /**
     *
     * @return
     * The teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     *
     * @param teams
     * The Teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    /**
     *
     * @return
     * The players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     * The Players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
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
    public String getSeasonId() {
        return seasonId;
    }

    /**
     *
     * @param seasonId
     * The SeasonId
     */
    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    /**
     *
     * @return
     * The matchCompletedDateFidelity
     */
    public Integer getMatchCompletedDateFidelity() {
        return matchCompletedDateFidelity;
    }

    /**
     *
     * @param matchCompletedDateFidelity
     * The MatchCompletedDateFidelity
     */
    public void setMatchCompletedDateFidelity(Integer matchCompletedDateFidelity) {
        this.matchCompletedDateFidelity = matchCompletedDateFidelity;
    }
}
