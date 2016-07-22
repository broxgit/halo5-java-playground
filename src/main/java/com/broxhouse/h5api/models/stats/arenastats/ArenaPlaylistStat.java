package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class ArenaPlaylistStat {

    @SerializedName("PlaylistId")
    @Expose
    private String playlistId;
    @SerializedName("MeasurementMatchesLeft")
    @Expose
    private String measurementMatchesLeft;
    @SerializedName("HighestCsr")
    @Expose
    private HighestCsr highestCsr;
    @SerializedName("Csr")
    @Expose
    private Csr csr;
    @SerializedName("CsrPercentile")
    @Expose
    private String csrPercentile;
    @SerializedName("TotalKills")
    @Expose
    private String totalKills;
    @SerializedName("TotalHeadshots")
    @Expose
    private String totalHeadshots;
    @SerializedName("TotalWeaponDamage")
    @Expose
    private String totalWeaponDamage;
    @SerializedName("TotalShotsFired")
    @Expose
    private String totalShotsFired;
    @SerializedName("TotalShotsLanded")
    @Expose
    private String totalShotsLanded;
    @SerializedName("WeaponWithMostKills")
    @Expose
    private WeaponWithMostKills weaponWithMostKills;
    @SerializedName("TotalMeleeKills")
    @Expose
    private String totalMeleeKills;
    @SerializedName("TotalMeleeDamage")
    @Expose
    private String totalMeleeDamage;
    @SerializedName("TotalAssassinations")
    @Expose
    private String totalAssassinations;
    @SerializedName("TotalGroundPoundKills")
    @Expose
    private String totalGroundPoundKills;
    @SerializedName("TotalGroundPoundDamage")
    @Expose
    private String totalGroundPoundDamage;
    @SerializedName("TotalShoulderBashKills")
    @Expose
    private String totalShoulderBashKills;
    @SerializedName("TotalShoulderBashDamage")
    @Expose
    private String totalShoulderBashDamage;
    @SerializedName("TotalGrenadeDamage")
    @Expose
    private String totalGrenadeDamage;
    @SerializedName("TotalPowerWeaponKills")
    @Expose
    private String totalPowerWeaponKills;
    @SerializedName("TotalPowerWeaponDamage")
    @Expose
    private String totalPowerWeaponDamage;
    @SerializedName("TotalPowerWeaponGrabs")
    @Expose
    private String totalPowerWeaponGrabs;
    @SerializedName("TotalPowerWeaponPossessionTime")
    @Expose
    private String totalPowerWeaponPossessionTime;
    @SerializedName("TotalDeaths")
    @Expose
    private String totalDeaths;
    @SerializedName("TotalAssists")
    @Expose
    private String totalAssists;
    @SerializedName("TotalGamesCompleted")
    @Expose
    private String totalGamesCompleted;
    @SerializedName("TotalGamesWon")
    @Expose
    private String totalGamesWon;
    @SerializedName("TotalGamesLost")
    @Expose
    private String totalGamesLost;
    @SerializedName("TotalGamesTied")
    @Expose
    private String totalGamesTied;
    @SerializedName("TotalTimePlayed")
    @Expose
    private String totalTimePlayed;
    @SerializedName("TotalGrenadeKills")
    @Expose
    private String totalGrenadeKills;
    @SerializedName("MedalAwards")
    @Expose
    private List<MedalAward> medalAwards = new ArrayList<MedalAward>();
    @SerializedName("DestroyedEnemyVehicles")
    @Expose
    private List<DestroyedEnemyVehicle> destroyedEnemyVehicles = new ArrayList<DestroyedEnemyVehicle>();
    @SerializedName("EnemyKills")
    @Expose
    private List<EnemyKill> enemyKills = new ArrayList<EnemyKill>();
    @SerializedName("WeaponStats")
    @Expose
    private List<WeaponStat> weaponStats = new ArrayList<WeaponStat>();
    @SerializedName("Impulses")
    @Expose
    private List<Impulse> impulses = new ArrayList<Impulse>();
    @SerializedName("TotalSpartanKills")
    @Expose
    private String totalSpartanKills;

    /**
     * @return The playlistId
     */
    public String getPlaylistId() {
        return playlistId;
    }

    /**
     * @param playlistId The PlaylistId
     */
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * @return The measurementMatchesLeft
     */
    public String getMeasurementMatchesLeft() {
        return measurementMatchesLeft;
    }

    /**
     * @param measurementMatchesLeft The MeasurementMatchesLeft
     */
    public void setMeasurementMatchesLeft(String measurementMatchesLeft) {
        this.measurementMatchesLeft = measurementMatchesLeft;
    }

    /**
     * @return The highestCsr
     */
    public HighestCsr getHighestCsr() {
        return highestCsr;
    }

    /**
     * @param highestCsr The HighestCsr
     */
    public void setHighestCsr(HighestCsr highestCsr) {
        this.highestCsr = highestCsr;
    }

    /**
     * @return The csr
     */
    public Csr getCsr() {
        return csr;
    }

    /**
     * @param csr The Csr
     */
    public void setCsr(Csr csr) {
        this.csr = csr;
    }

    /**
     * @return The csrPercentile
     */
    public String getCsrPercentile() {
        return csrPercentile;
    }

    /**
     * @param csrPercentile The CsrPercentile
     */
    public void setCsrPercentile(String csrPercentile) {
        this.csrPercentile = csrPercentile;
    }

    /**
     * @return The totalKills
     */
    public String getTotalKills() {
        return totalKills;
    }

    /**
     * @param totalKills The TotalKills
     */
    public void setTotalKills(String totalKills) {
        this.totalKills = totalKills;
    }

    /**
     * @return The totalHeadshots
     */
    public String getTotalHeadshots() {
        return totalHeadshots;
    }

    /**
     * @param totalHeadshots The TotalHeadshots
     */
    public void setTotalHeadshots(String totalHeadshots) {
        this.totalHeadshots = totalHeadshots;
    }

    /**
     * @return The totalWeaponDamage
     */
    public String getTotalWeaponDamage() {
        return totalWeaponDamage;
    }

    /**
     * @param totalWeaponDamage The TotalWeaponDamage
     */
    public void setTotalWeaponDamage(String totalWeaponDamage) {
        this.totalWeaponDamage = totalWeaponDamage;
    }

    /**
     * @return The totalShotsFired
     */
    public String getTotalShotsFired() {
        return totalShotsFired;
    }

    /**
     * @param totalShotsFired The TotalShotsFired
     */
    public void setTotalShotsFired(String totalShotsFired) {
        this.totalShotsFired = totalShotsFired;
    }

    /**
     * @return The totalShotsLanded
     */
    public String getTotalShotsLanded() {
        return totalShotsLanded;
    }

    /**
     * @param totalShotsLanded The TotalShotsLanded
     */
    public void setTotalShotsLanded(String totalShotsLanded) {
        this.totalShotsLanded = totalShotsLanded;
    }

    /**
     * @return The weaponWithMostKills
     */
    public WeaponWithMostKills getWeaponWithMostKills() {
        return weaponWithMostKills;
    }

    /**
     * @param weaponWithMostKills The WeaponWithMostKills
     */
    public void setWeaponWithMostKills(WeaponWithMostKills weaponWithMostKills) {
        this.weaponWithMostKills = weaponWithMostKills;
    }

    /**
     * @return The totalMeleeKills
     */
    public String getTotalMeleeKills() {
        return totalMeleeKills;
    }

    /**
     * @param totalMeleeKills The TotalMeleeKills
     */
    public void setTotalMeleeKills(String totalMeleeKills) {
        this.totalMeleeKills = totalMeleeKills;
    }

    /**
     * @return The totalMeleeDamage
     */
    public String getTotalMeleeDamage() {
        return totalMeleeDamage;
    }

    /**
     * @param totalMeleeDamage The TotalMeleeDamage
     */
    public void setTotalMeleeDamage(String totalMeleeDamage) {
        this.totalMeleeDamage = totalMeleeDamage;
    }

    /**
     * @return The totalAssassinations
     */
    public String getTotalAssassinations() {
        return totalAssassinations;
    }

    /**
     * @param totalAssassinations The TotalAssassinations
     */
    public void setTotalAssassinations(String totalAssassinations) {
        this.totalAssassinations = totalAssassinations;
    }

    /**
     * @return The totalGroundPoundKills
     */
    public String getTotalGroundPoundKills() {
        return totalGroundPoundKills;
    }

    /**
     * @param totalGroundPoundKills The TotalGroundPoundKills
     */
    public void setTotalGroundPoundKills(String totalGroundPoundKills) {
        this.totalGroundPoundKills = totalGroundPoundKills;
    }

    /**
     * @return The totalGroundPoundDamage
     */
    public String getTotalGroundPoundDamage() {
        return totalGroundPoundDamage;
    }

    /**
     * @param totalGroundPoundDamage The TotalGroundPoundDamage
     */
    public void setTotalGroundPoundDamage(String totalGroundPoundDamage) {
        this.totalGroundPoundDamage = totalGroundPoundDamage;
    }

    /**
     * @return The totalShoulderBashKills
     */
    public String getTotalShoulderBashKills() {
        return totalShoulderBashKills;
    }

    /**
     * @param totalShoulderBashKills The TotalShoulderBashKills
     */
    public void setTotalShoulderBashKills(String totalShoulderBashKills) {
        this.totalShoulderBashKills = totalShoulderBashKills;
    }

    /**
     * @return The totalShoulderBashDamage
     */
    public String getTotalShoulderBashDamage() {
        return totalShoulderBashDamage;
    }

    /**
     * @param totalShoulderBashDamage The TotalShoulderBashDamage
     */
    public void setTotalShoulderBashDamage(String totalShoulderBashDamage) {
        this.totalShoulderBashDamage = totalShoulderBashDamage;
    }

    /**
     * @return The totalGrenadeDamage
     */
    public String getTotalGrenadeDamage() {
        return totalGrenadeDamage;
    }

    /**
     * @param totalGrenadeDamage The TotalGrenadeDamage
     */
    public void setTotalGrenadeDamage(String totalGrenadeDamage) {
        this.totalGrenadeDamage = totalGrenadeDamage;
    }

    /**
     * @return The totalPowerWeaponKills
     */
    public String getTotalPowerWeaponKills() {
        return totalPowerWeaponKills;
    }

    /**
     * @param totalPowerWeaponKills The TotalPowerWeaponKills
     */
    public void setTotalPowerWeaponKills(String totalPowerWeaponKills) {
        this.totalPowerWeaponKills = totalPowerWeaponKills;
    }

    /**
     * @return The totalPowerWeaponDamage
     */
    public String getTotalPowerWeaponDamage() {
        return totalPowerWeaponDamage;
    }

    /**
     * @param totalPowerWeaponDamage The TotalPowerWeaponDamage
     */
    public void setTotalPowerWeaponDamage(String totalPowerWeaponDamage) {
        this.totalPowerWeaponDamage = totalPowerWeaponDamage;
    }

    /**
     * @return The totalPowerWeaponGrabs
     */
    public String getTotalPowerWeaponGrabs() {
        return totalPowerWeaponGrabs;
    }

    /**
     * @param totalPowerWeaponGrabs The TotalPowerWeaponGrabs
     */
    public void setTotalPowerWeaponGrabs(String totalPowerWeaponGrabs) {
        this.totalPowerWeaponGrabs = totalPowerWeaponGrabs;
    }

    /**
     * @return The totalPowerWeaponPossessionTime
     */
    public String getTotalPowerWeaponPossessionTime() {
        return totalPowerWeaponPossessionTime;
    }

    /**
     * @param totalPowerWeaponPossessionTime The TotalPowerWeaponPossessionTime
     */
    public void setTotalPowerWeaponPossessionTime(String totalPowerWeaponPossessionTime) {
        this.totalPowerWeaponPossessionTime = totalPowerWeaponPossessionTime;
    }

    /**
     * @return The totalDeaths
     */
    public String getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * @param totalDeaths The TotalDeaths
     */
    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    /**
     * @return The totalAssists
     */
    public String getTotalAssists() {
        return totalAssists;
    }

    /**
     * @param totalAssists The TotalAssists
     */
    public void setTotalAssists(String totalAssists) {
        this.totalAssists = totalAssists;
    }

    /**
     * @return The totalGamesCompleted
     */
    public String getTotalGamesCompleted() {
        return totalGamesCompleted;
    }

    /**
     * @param totalGamesCompleted The TotalGamesCompleted
     */
    public void setTotalGamesCompleted(String totalGamesCompleted) {
        this.totalGamesCompleted = totalGamesCompleted;
    }

    /**
     * @return The totalGamesWon
     */
    public String getTotalGamesWon() {
        return totalGamesWon;
    }

    /**
     * @param totalGamesWon The TotalGamesWon
     */
    public void setTotalGamesWon(String totalGamesWon) {
        this.totalGamesWon = totalGamesWon;
    }

    /**
     * @return The totalGamesLost
     */
    public String getTotalGamesLost() {
        return totalGamesLost;
    }

    /**
     * @param totalGamesLost The TotalGamesLost
     */
    public void setTotalGamesLost(String totalGamesLost) {
        this.totalGamesLost = totalGamesLost;
    }

    /**
     * @return The totalGamesTied
     */
    public String getTotalGamesTied() {
        return totalGamesTied;
    }

    /**
     * @param totalGamesTied The TotalGamesTied
     */
    public void setTotalGamesTied(String totalGamesTied) {
        this.totalGamesTied = totalGamesTied;
    }

    /**
     * @return The totalTimePlayed
     */
    public String getTotalTimePlayed() {
        return totalTimePlayed;
    }

    /**
     * @param totalTimePlayed The TotalTimePlayed
     */
    public void setTotalTimePlayed(String totalTimePlayed) {
        this.totalTimePlayed = totalTimePlayed;
    }

    /**
     * @return The totalGrenadeKills
     */
    public String getTotalGrenadeKills() {
        return totalGrenadeKills;
    }

    /**
     * @param totalGrenadeKills The TotalGrenadeKills
     */
    public void setTotalGrenadeKills(String totalGrenadeKills) {
        this.totalGrenadeKills = totalGrenadeKills;
    }

    /**
     * @return The medalAwards
     */
    public List<MedalAward> getMedalAwards() {
        return medalAwards;
    }

    /**
     * @param medalAwards The MedalAwards
     */
    public void setMedalAwards(List<MedalAward> medalAwards) {
        this.medalAwards = medalAwards;
    }

    /**
     * @return The destroyedEnemyVehicles
     */
    public List<DestroyedEnemyVehicle> getDestroyedEnemyVehicles() {
        return destroyedEnemyVehicles;
    }

    /**
     * @param destroyedEnemyVehicles The DestroyedEnemyVehicles
     */
    public void setDestroyedEnemyVehicles(List<DestroyedEnemyVehicle> destroyedEnemyVehicles) {
        this.destroyedEnemyVehicles = destroyedEnemyVehicles;
    }

    /**
     * @return The enemyKills
     */
    public List<EnemyKill> getEnemyKills() {
        return enemyKills;
    }

    /**
     * @param enemyKills The EnemyKills
     */
    public void setEnemyKills(List<EnemyKill> enemyKills) {
        this.enemyKills = enemyKills;
    }

    /**
     * @return The weaponStats
     */
    public List<WeaponStat> getWeaponStats() {
        return weaponStats;
    }

    /**
     * @param weaponStats The WeaponStats
     */
    public void setWeaponStats(List<WeaponStat> weaponStats) {
        this.weaponStats = weaponStats;
    }

    /**
     * @return The impulses
     */
    public List<Impulse> getImpulses() {
        return impulses;
    }

    /**
     * @param impulses The Impulses
     */
    public void setImpulses(List<Impulse> impulses) {
        this.impulses = impulses;
    }

    /**
     * @return The totalSpartanKills
     */
    public String getTotalSpartanKills() {
        return totalSpartanKills;
    }

    /**
     * @param totalSpartanKills The TotalSpartanKills
     */
    public void setTotalSpartanKills(String totalSpartanKills) {
        this.totalSpartanKills = totalSpartanKills;
    }


}
