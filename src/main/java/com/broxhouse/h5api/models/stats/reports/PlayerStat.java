
package com.broxhouse.h5api.models.stats.reports;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.broxhouse.h5api.models.stats.arenastatsold.WeaponStat;
import com.broxhouse.h5api.models.stats.arenastatsold.WeaponWithMostKills;
import com.broxhouse.h5api.models.stats.common.FlexibleStats;
import com.broxhouse.h5api.models.stats.common.Impulse;
import com.broxhouse.h5api.models.stats.common.MedalAward;
import com.broxhouse.h5api.models.stats.common.Player;
import com.broxhouse.h5api.models.stats.reports.KilledByOpponentDetail;
import com.broxhouse.h5api.models.stats.reports.KilledOpponentDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class PlayerStat {

    @SerializedName("KilledOpponentDetails")
    @Expose
    private List<KilledOpponentDetail> killedOpponentDetails = new ArrayList<KilledOpponentDetail>();
    @SerializedName("KilledByOpponentDetails")
    @Expose
    private List<KilledByOpponentDetail> killedByOpponentDetails = new ArrayList<KilledByOpponentDetail>();
    @SerializedName("FlexibleStats")
    @Expose
    private FlexibleStats flexibleStats;
    @SerializedName("Player")
    @Expose
    private Player player;
    @SerializedName("TeamId")
    @Expose
    private Integer teamId;
    @SerializedName("Rank")
    @Expose
    private Integer rank;
    @SerializedName("DNF")
    @Expose
    private Boolean dNF;
    @SerializedName("AvgLifeTimeOfPlayer")
    @Expose
    private String avgLifeTimeOfPlayer;
    @SerializedName("PreMatchRatings")
    @Expose
    private Object preMatchRatings;
    @SerializedName("PostMatchRatings")
    @Expose
    private Object postMatchRatings;
    @SerializedName("PlayerScore")
    @Expose
    private Object playerScore;
    @SerializedName("GameEndStatus")
    @Expose
    private Integer gameEndStatus;
    @SerializedName("TotalKills")
    @Expose
    private Integer totalKills;
    @SerializedName("TotalHeadshots")
    @Expose
    private Integer totalHeadshots;
    @SerializedName("TotalWeaponDamage")
    @Expose
    private Double totalWeaponDamage;
    @SerializedName("TotalShotsFired")
    @Expose
    private Integer totalShotsFired;
    @SerializedName("TotalShotsLanded")
    @Expose
    private Integer totalShotsLanded;
    @SerializedName("WeaponWithMostKills")
    @Expose
    private WeaponWithMostKills weaponWithMostKills;
    @SerializedName("TotalMeleeKills")
    @Expose
    private Integer totalMeleeKills;
    @SerializedName("TotalMeleeDamage")
    @Expose
    private Double totalMeleeDamage;
    @SerializedName("TotalAssassinations")
    @Expose
    private Integer totalAssassinations;
    @SerializedName("TotalGroundPoundKills")
    @Expose
    private Integer totalGroundPoundKills;
    @SerializedName("TotalGroundPoundDamage")
    @Expose
    private Double totalGroundPoundDamage;
    @SerializedName("TotalShoulderBashKills")
    @Expose
    private Integer totalShoulderBashKills;
    @SerializedName("TotalShoulderBashDamage")
    @Expose
    private Double totalShoulderBashDamage;
    @SerializedName("TotalGrenadeDamage")
    @Expose
    private Double totalGrenadeDamage;
    @SerializedName("TotalPowerWeaponKills")
    @Expose
    private Integer totalPowerWeaponKills;
    @SerializedName("TotalPowerWeaponDamage")
    @Expose
    private Double totalPowerWeaponDamage;
    @SerializedName("TotalPowerWeaponGrabs")
    @Expose
    private Integer totalPowerWeaponGrabs;
    @SerializedName("TotalPowerWeaponPossessionTime")
    @Expose
    private String totalPowerWeaponPossessionTime;
    @SerializedName("TotalDeaths")
    @Expose
    private Integer totalDeaths;
    @SerializedName("TotalAssists")
    @Expose
    private Integer totalAssists;
    @SerializedName("TotalGamesCompleted")
    @Expose
    private Integer totalGamesCompleted;
    @SerializedName("TotalGamesWon")
    @Expose
    private Integer totalGamesWon;
    @SerializedName("TotalGamesLost")
    @Expose
    private Integer totalGamesLost;
    @SerializedName("TotalGamesTied")
    @Expose
    private Integer totalGamesTied;
    @SerializedName("TotalTimePlayed")
    @Expose
    private String totalTimePlayed;
    @SerializedName("TotalGrenadeKills")
    @Expose
    private Integer totalGrenadeKills;
    @SerializedName("MedalAwards")
    @Expose
    private List<MedalAward> medalAwards = new ArrayList<MedalAward>();
    @SerializedName("DestroyedEnemyVehicles")
    @Expose
    private List<Object> destroyedEnemyVehicles = new ArrayList<Object>();
    @SerializedName("EnemyKills")
    @Expose
    private List<Object> enemyKills = new ArrayList<Object>();
    @SerializedName("WeaponStats")
    @Expose
    private List<WeaponStat> weaponStats = new ArrayList<WeaponStat>();
    @SerializedName("Impulses")
    @Expose
    private List<Impulse> impulses = new ArrayList<Impulse>();
    @SerializedName("TotalSpartanKills")
    @Expose
    private Integer totalSpartanKills;
    @SerializedName("FastestMatchWin")
    @Expose
    private Object fastestMatchWin;

    /**
     * 
     * @return
     *     The killedOpponentDetails
     */
    public List<KilledOpponentDetail> getKilledOpponentDetails() {
        return killedOpponentDetails;
    }

    /**
     * 
     * @param killedOpponentDetails
     *     The KilledOpponentDetails
     */
    public void setKilledOpponentDetails(List<KilledOpponentDetail> killedOpponentDetails) {
        this.killedOpponentDetails = killedOpponentDetails;
    }

    /**
     * 
     * @return
     *     The killedByOpponentDetails
     */
    public List<KilledByOpponentDetail> getKilledByOpponentDetails() {
        return killedByOpponentDetails;
    }

    /**
     * 
     * @param killedByOpponentDetails
     *     The KilledByOpponentDetails
     */
    public void setKilledByOpponentDetails(List<KilledByOpponentDetail> killedByOpponentDetails) {
        this.killedByOpponentDetails = killedByOpponentDetails;
    }

    /**
     * 
     * @return
     *     The flexibleStats
     */
    public FlexibleStats getFlexibleStats() {
        return flexibleStats;
    }

    /**
     * 
     * @param flexibleStats
     *     The FlexibleStats
     */
    public void setFlexibleStats(FlexibleStats flexibleStats) {
        this.flexibleStats = flexibleStats;
    }

    /**
     * 
     * @return
     *     The player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * 
     * @param player
     *     The Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * 
     * @return
     *     The teamId
     */
    public Integer getTeamId() {
        return teamId;
    }

    /**
     * 
     * @param teamId
     *     The TeamId
     */
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    /**
     * 
     * @return
     *     The rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 
     * @param rank
     *     The Rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 
     * @return
     *     The dNF
     */
    public Boolean getDNF() {
        return dNF;
    }

    /**
     * 
     * @param dNF
     *     The DNF
     */
    public void setDNF(Boolean dNF) {
        this.dNF = dNF;
    }

    /**
     * 
     * @return
     *     The avgLifeTimeOfPlayer
     */
    public String getAvgLifeTimeOfPlayer() {
        return avgLifeTimeOfPlayer;
    }

    /**
     * 
     * @param avgLifeTimeOfPlayer
     *     The AvgLifeTimeOfPlayer
     */
    public void setAvgLifeTimeOfPlayer(String avgLifeTimeOfPlayer) {
        this.avgLifeTimeOfPlayer = avgLifeTimeOfPlayer;
    }

    /**
     * 
     * @return
     *     The preMatchRatings
     */
    public Object getPreMatchRatings() {
        return preMatchRatings;
    }

    /**
     * 
     * @param preMatchRatings
     *     The PreMatchRatings
     */
    public void setPreMatchRatings(Object preMatchRatings) {
        this.preMatchRatings = preMatchRatings;
    }

    /**
     * 
     * @return
     *     The postMatchRatings
     */
    public Object getPostMatchRatings() {
        return postMatchRatings;
    }

    /**
     * 
     * @param postMatchRatings
     *     The PostMatchRatings
     */
    public void setPostMatchRatings(Object postMatchRatings) {
        this.postMatchRatings = postMatchRatings;
    }

    /**
     * 
     * @return
     *     The playerScore
     */
    public Object getPlayerScore() {
        return playerScore;
    }

    /**
     * 
     * @param playerScore
     *     The PlayerScore
     */
    public void setPlayerScore(Object playerScore) {
        this.playerScore = playerScore;
    }

    /**
     * 
     * @return
     *     The gameEndStatus
     */
    public Integer getGameEndStatus() {
        return gameEndStatus;
    }

    /**
     * 
     * @param gameEndStatus
     *     The GameEndStatus
     */
    public void setGameEndStatus(Integer gameEndStatus) {
        this.gameEndStatus = gameEndStatus;
    }

    /**
     * 
     * @return
     *     The totalKills
     */
    public Integer getTotalKills() {
        return totalKills;
    }

    /**
     * 
     * @param totalKills
     *     The TotalKills
     */
    public void setTotalKills(Integer totalKills) {
        this.totalKills = totalKills;
    }

    /**
     * 
     * @return
     *     The totalHeadshots
     */
    public Integer getTotalHeadshots() {
        return totalHeadshots;
    }

    /**
     * 
     * @param totalHeadshots
     *     The TotalHeadshots
     */
    public void setTotalHeadshots(Integer totalHeadshots) {
        this.totalHeadshots = totalHeadshots;
    }

    /**
     * 
     * @return
     *     The totalWeaponDamage
     */
    public Double getTotalWeaponDamage() {
        return totalWeaponDamage;
    }

    /**
     * 
     * @param totalWeaponDamage
     *     The TotalWeaponDamage
     */
    public void setTotalWeaponDamage(Double totalWeaponDamage) {
        this.totalWeaponDamage = totalWeaponDamage;
    }

    /**
     * 
     * @return
     *     The totalShotsFired
     */
    public Integer getTotalShotsFired() {
        return totalShotsFired;
    }

    /**
     * 
     * @param totalShotsFired
     *     The TotalShotsFired
     */
    public void setTotalShotsFired(Integer totalShotsFired) {
        this.totalShotsFired = totalShotsFired;
    }

    /**
     * 
     * @return
     *     The totalShotsLanded
     */
    public Integer getTotalShotsLanded() {
        return totalShotsLanded;
    }

    /**
     * 
     * @param totalShotsLanded
     *     The TotalShotsLanded
     */
    public void setTotalShotsLanded(Integer totalShotsLanded) {
        this.totalShotsLanded = totalShotsLanded;
    }

    /**
     * 
     * @return
     *     The weaponWithMostKills
     */
    public WeaponWithMostKills getWeaponWithMostKills() {
        return weaponWithMostKills;
    }

    /**
     * 
     * @param weaponWithMostKills
     *     The WeaponWithMostKills
     */
    public void setWeaponWithMostKills(WeaponWithMostKills weaponWithMostKills) {
        this.weaponWithMostKills = weaponWithMostKills;
    }

    /**
     * 
     * @return
     *     The totalMeleeKills
     */
    public Integer getTotalMeleeKills() {
        return totalMeleeKills;
    }

    /**
     * 
     * @param totalMeleeKills
     *     The TotalMeleeKills
     */
    public void setTotalMeleeKills(Integer totalMeleeKills) {
        this.totalMeleeKills = totalMeleeKills;
    }

    /**
     * 
     * @return
     *     The totalMeleeDamage
     */
    public Double getTotalMeleeDamage() {
        return totalMeleeDamage;
    }

    /**
     * 
     * @param totalMeleeDamage
     *     The TotalMeleeDamage
     */
    public void setTotalMeleeDamage(Double totalMeleeDamage) {
        this.totalMeleeDamage = totalMeleeDamage;
    }

    /**
     * 
     * @return
     *     The totalAssassinations
     */
    public Integer getTotalAssassinations() {
        return totalAssassinations;
    }

    /**
     * 
     * @param totalAssassinations
     *     The TotalAssassinations
     */
    public void setTotalAssassinations(Integer totalAssassinations) {
        this.totalAssassinations = totalAssassinations;
    }

    /**
     * 
     * @return
     *     The totalGroundPoundKills
     */
    public Integer getTotalGroundPoundKills() {
        return totalGroundPoundKills;
    }

    /**
     * 
     * @param totalGroundPoundKills
     *     The TotalGroundPoundKills
     */
    public void setTotalGroundPoundKills(Integer totalGroundPoundKills) {
        this.totalGroundPoundKills = totalGroundPoundKills;
    }

    /**
     * 
     * @return
     *     The totalGroundPoundDamage
     */
    public Double getTotalGroundPoundDamage() {
        return totalGroundPoundDamage;
    }

    /**
     * 
     * @param totalGroundPoundDamage
     *     The TotalGroundPoundDamage
     */
    public void setTotalGroundPoundDamage(Double totalGroundPoundDamage) {
        this.totalGroundPoundDamage = totalGroundPoundDamage;
    }

    /**
     * 
     * @return
     *     The totalShoulderBashKills
     */
    public Integer getTotalShoulderBashKills() {
        return totalShoulderBashKills;
    }

    /**
     * 
     * @param totalShoulderBashKills
     *     The TotalShoulderBashKills
     */
    public void setTotalShoulderBashKills(Integer totalShoulderBashKills) {
        this.totalShoulderBashKills = totalShoulderBashKills;
    }

    /**
     * 
     * @return
     *     The totalShoulderBashDamage
     */
    public Double getTotalShoulderBashDamage() {
        return totalShoulderBashDamage;
    }

    /**
     * 
     * @param totalShoulderBashDamage
     *     The TotalShoulderBashDamage
     */
    public void setTotalShoulderBashDamage(Double totalShoulderBashDamage) {
        this.totalShoulderBashDamage = totalShoulderBashDamage;
    }

    /**
     * 
     * @return
     *     The totalGrenadeDamage
     */
    public Double getTotalGrenadeDamage() {
        return totalGrenadeDamage;
    }

    /**
     * 
     * @param totalGrenadeDamage
     *     The TotalGrenadeDamage
     */
    public void setTotalGrenadeDamage(Double totalGrenadeDamage) {
        this.totalGrenadeDamage = totalGrenadeDamage;
    }

    /**
     * 
     * @return
     *     The totalPowerWeaponKills
     */
    public Integer getTotalPowerWeaponKills() {
        return totalPowerWeaponKills;
    }

    /**
     * 
     * @param totalPowerWeaponKills
     *     The TotalPowerWeaponKills
     */
    public void setTotalPowerWeaponKills(Integer totalPowerWeaponKills) {
        this.totalPowerWeaponKills = totalPowerWeaponKills;
    }

    /**
     * 
     * @return
     *     The totalPowerWeaponDamage
     */
    public Double getTotalPowerWeaponDamage() {
        return totalPowerWeaponDamage;
    }

    /**
     * 
     * @param totalPowerWeaponDamage
     *     The TotalPowerWeaponDamage
     */
    public void setTotalPowerWeaponDamage(Double totalPowerWeaponDamage) {
        this.totalPowerWeaponDamage = totalPowerWeaponDamage;
    }

    /**
     * 
     * @return
     *     The totalPowerWeaponGrabs
     */
    public Integer getTotalPowerWeaponGrabs() {
        return totalPowerWeaponGrabs;
    }

    /**
     * 
     * @param totalPowerWeaponGrabs
     *     The TotalPowerWeaponGrabs
     */
    public void setTotalPowerWeaponGrabs(Integer totalPowerWeaponGrabs) {
        this.totalPowerWeaponGrabs = totalPowerWeaponGrabs;
    }

    /**
     * 
     * @return
     *     The totalPowerWeaponPossessionTime
     */
    public String getTotalPowerWeaponPossessionTime() {
        return totalPowerWeaponPossessionTime;
    }

    /**
     * 
     * @param totalPowerWeaponPossessionTime
     *     The TotalPowerWeaponPossessionTime
     */
    public void setTotalPowerWeaponPossessionTime(String totalPowerWeaponPossessionTime) {
        this.totalPowerWeaponPossessionTime = totalPowerWeaponPossessionTime;
    }

    /**
     * 
     * @return
     *     The totalDeaths
     */
    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * 
     * @param totalDeaths
     *     The TotalDeaths
     */
    public void setTotalDeaths(Integer totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    /**
     * 
     * @return
     *     The totalAssists
     */
    public Integer getTotalAssists() {
        return totalAssists;
    }

    /**
     * 
     * @param totalAssists
     *     The TotalAssists
     */
    public void setTotalAssists(Integer totalAssists) {
        this.totalAssists = totalAssists;
    }

    /**
     * 
     * @return
     *     The totalGamesCompleted
     */
    public Integer getTotalGamesCompleted() {
        return totalGamesCompleted;
    }

    /**
     * 
     * @param totalGamesCompleted
     *     The TotalGamesCompleted
     */
    public void setTotalGamesCompleted(Integer totalGamesCompleted) {
        this.totalGamesCompleted = totalGamesCompleted;
    }

    /**
     * 
     * @return
     *     The totalGamesWon
     */
    public Integer getTotalGamesWon() {
        return totalGamesWon;
    }

    /**
     * 
     * @param totalGamesWon
     *     The TotalGamesWon
     */
    public void setTotalGamesWon(Integer totalGamesWon) {
        this.totalGamesWon = totalGamesWon;
    }

    /**
     * 
     * @return
     *     The totalGamesLost
     */
    public Integer getTotalGamesLost() {
        return totalGamesLost;
    }

    /**
     * 
     * @param totalGamesLost
     *     The TotalGamesLost
     */
    public void setTotalGamesLost(Integer totalGamesLost) {
        this.totalGamesLost = totalGamesLost;
    }

    /**
     * 
     * @return
     *     The totalGamesTied
     */
    public Integer getTotalGamesTied() {
        return totalGamesTied;
    }

    /**
     * 
     * @param totalGamesTied
     *     The TotalGamesTied
     */
    public void setTotalGamesTied(Integer totalGamesTied) {
        this.totalGamesTied = totalGamesTied;
    }

    /**
     * 
     * @return
     *     The totalTimePlayed
     */
    public String getTotalTimePlayed() {
        return totalTimePlayed;
    }

    /**
     * 
     * @param totalTimePlayed
     *     The TotalTimePlayed
     */
    public void setTotalTimePlayed(String totalTimePlayed) {
        this.totalTimePlayed = totalTimePlayed;
    }

    /**
     * 
     * @return
     *     The totalGrenadeKills
     */
    public Integer getTotalGrenadeKills() {
        return totalGrenadeKills;
    }

    /**
     * 
     * @param totalGrenadeKills
     *     The TotalGrenadeKills
     */
    public void setTotalGrenadeKills(Integer totalGrenadeKills) {
        this.totalGrenadeKills = totalGrenadeKills;
    }

    /**
     * 
     * @return
     *     The medalAwards
     */
    public List<MedalAward> getMedalAwards() {
        return medalAwards;
    }

    /**
     * 
     * @param medalAwards
     *     The MedalAwards
     */
    public void setMedalAwards(List<MedalAward> medalAwards) {
        this.medalAwards = medalAwards;
    }

    /**
     * 
     * @return
     *     The destroyedEnemyVehicles
     */
    public List<Object> getDestroyedEnemyVehicles() {
        return destroyedEnemyVehicles;
    }

    /**
     * 
     * @param destroyedEnemyVehicles
     *     The DestroyedEnemyVehicles
     */
    public void setDestroyedEnemyVehicles(List<Object> destroyedEnemyVehicles) {
        this.destroyedEnemyVehicles = destroyedEnemyVehicles;
    }

    /**
     * 
     * @return
     *     The enemyKills
     */
    public List<Object> getEnemyKills() {
        return enemyKills;
    }

    /**
     * 
     * @param enemyKills
     *     The EnemyKills
     */
    public void setEnemyKills(List<Object> enemyKills) {
        this.enemyKills = enemyKills;
    }

    /**
     * 
     * @return
     *     The weaponStats
     */
    public List<WeaponStat> getWeaponStats() {
        return weaponStats;
    }

    /**
     * 
     * @param weaponStats
     *     The WeaponStats
     */
    public void setWeaponStats(List<WeaponStat> weaponStats) {
        this.weaponStats = weaponStats;
    }

    /**
     * 
     * @return
     *     The impulses
     */
    public List<Impulse> getImpulses() {
        return impulses;
    }

    /**
     * 
     * @param impulses
     *     The Impulses
     */
    public void setImpulses(List<Impulse> impulses) {
        this.impulses = impulses;
    }

    /**
     * 
     * @return
     *     The totalSpartanKills
     */
    public Integer getTotalSpartanKills() {
        return totalSpartanKills;
    }

    /**
     * 
     * @param totalSpartanKills
     *     The TotalSpartanKills
     */
    public void setTotalSpartanKills(Integer totalSpartanKills) {
        this.totalSpartanKills = totalSpartanKills;
    }

    /**
     * 
     * @return
     *     The fastestMatchWin
     */
    public Object getFastestMatchWin() {
        return fastestMatchWin;
    }

    /**
     * 
     * @param fastestMatchWin
     *     The FastestMatchWin
     */
    public void setFastestMatchWin(Object fastestMatchWin) {
        this.fastestMatchWin = fastestMatchWin;
    }

}
