

package com.broxhouse.h5api.models.stats.reports;

import com.broxhouse.h5api.models.stats.common.EnemyKill;
import com.broxhouse.h5api.models.stats.common.Impulse;
import com.broxhouse.h5api.models.stats.common.MedalAward;
import com.broxhouse.h5api.models.stats.common.WeaponStats;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseStats {

    /**
     * Total number of kills done by the player. This includes melee kills, shoulder
     * bash kills and Spartan charge kills, all power weapons, AI kills and vehicle
     * destructions.
     */
    @SerializedName("TotalKills")
    private int totalKills;

    /**
     * Total number of headshots done by the player.
     */
    @SerializedName("TotalHeadshots")
    private int totalHeadshots;

    /**
     * Total weapon damage dealt by the player.
     */
    @SerializedName("TotalWeaponDamage")
    private double totalWeaponDamage;

    /**
     * Total number of shots fired by the player.
     */
    @SerializedName("TotalShotsFired")
    private int totalShotsFired;

    /**
     * Total number of shots landed by the player.
     */
    @SerializedName("TotalShotsLanded")
    private int totalShotsLanded;

    /**
     * The weapon the player used to get the most kills this match.
     */
    @SerializedName("WeaponWithMostKills")
    private WeaponStats weapon;

    /**
     * Total number of melee kills by the player.
     */
    @SerializedName("TotalMeleeKills")
    private int totalMeleeKills;

    /**
     * Total melee damage dealt by the player.
     */
    @SerializedName("TotalMeleeDamage")
    private double totalMeleeDamage;

    /**
     * Total number of assassinations by the player.
     */
    @SerializedName("TotalAssassinations")
    private int totalAssassinations;

    /**
     * Total number of ground pound kills by the player.
     */
    @SerializedName("TotalGroundPoundKills")
    private int totalGroundPoundKills;

    /**
     * Total ground pound damage dealt by the player.
     */
    @SerializedName("TotalGroundPoundDamage")
    private double totalGroundPoundDamage;

    /**
     * Total number of shoulder bash kills by the player.
     */
    @SerializedName("TotalShoulderBashKills")
    private int totalShoulderBashKills;

    /**
     * Total shoulder bash damage dealt by the player.
     */
    @SerializedName("TotalShoulderBashDamage")
    private double totalShoulderBashDamage;

    /**
     * Total grenade damage dealt by the player.
     */
    @SerializedName("TotalGrenadeDamage")
    private double totalGrenadeDamage;

    /**
     * Total number of power weapon kills by the player.
     */
    @SerializedName("TotalPowerWeaponKills")
    private int totalPowerWeaponKills;

    /**
     * Total power weapon damage dealt by the player.
     */
    @SerializedName("TotalPowerWeaponDamage")
    private double totalPowerWeaponDamage;

    /**
     * Total number of power weapon grabs by the player.
     */
    @SerializedName("TotalPowerWeaponGrabs")
    private int totalPowerWeaponGrabs;

    /**
     * Total power weapon possession by the player. This is expressed as an ISO 8601
     * Duration.
     */
    @SerializedName("TotalPowerWeaponPossessionTime")
    private String totalPowerWeaponPossessionTime;

    /**
     * Total number of deaths by the player.
     */
    @SerializedName("TotalDeaths")
    private int totalDeaths;

    /**
     * Total number of assists by the player.
     */
    @SerializedName("TotalAssists")
    private int totalAssists;

    /**
     * Not used.
     */
    @SerializedName("TotalGamesCompleted")
    private int totalGamesCompleted;

    /**
     * Not used.
     */
    @SerializedName("TotalGamesWon")
    private int totalGamesWon;

    /**
     * Not used.
     */
    @SerializedName("TotalGamesLost")
    private int totalGamesLost;

    /**
     * Not used.
     */
    @SerializedName("TotalGamesTied")
    private int totalGamesTied;

    /**
     * Total timed played in this match by the player.
     */
    @SerializedName("TotalTimePlayed")
    private String totalTimePlayed;

    /**
     * Total number of grenade kills by the player.
     */
    @SerializedName("TotalGrenadeKills")
    private int totalGrenadeKills;

    /**
     * The set of Medals earned by the player.
     */
    @SerializedName("MedalAwards")
    private List<MedalAward> medalAwards;

    /**
     * List of enemy vehicles destroyed. Vehicles are available via the Metadata API.
     * Note: this stat measures enemy vehicles, not any vehicle destruction.
     */
    @SerializedName("DestroyedEnemyVehicles")
    private List<EnemyKill> destroyedEnemyVehicles;

    /**
     * List of enemies killed, per enemy type. Enemies are available via the Metadata
     * API.
     */
    @SerializedName("EnemyKills")
    private List<EnemyKill> enemyKills;

    /**
     * The set of weapons (weapons and vehicles included) used by the player.
     */
    @SerializedName("WeaponStats")
    private List<WeaponStats> weapons;

    /**
     * The set of Impulses (invisible Medals) earned by the player.
     */
    @SerializedName("Impulses")
    private List<com.broxhouse.h5api.models.stats.common.Impulse> impulses;

    /**
     * Total number of Spartan kills by the player.
     */
    @SerializedName("TotalSpartanKills")
    private int totalSpartanKills;

    public int getTotalKills() {
        return totalKills;
    }

    public int getTotalHeadshots() {
        return totalHeadshots;
    }

    public double getTotalWeaponDamage() {
        return totalWeaponDamage;
    }

    public int getTotalShotsFired() {
        return totalShotsFired;
    }

    public int getTotalShotsLanded() {
        return totalShotsLanded;
    }

    public WeaponStats getWeapon() {
        return weapon;
    }

    public int getTotalMeleeKills() {
        return totalMeleeKills;
    }

    public double getTotalMeleeDamage() {
        return totalMeleeDamage;
    }

    public int getTotalAssassinations() {
        return totalAssassinations;
    }

    public int getTotalGroundPoundKills() {
        return totalGroundPoundKills;
    }

    public double getTotalGroundPoundDamage() {
        return totalGroundPoundDamage;
    }

    public int getTotalShoulderBashKills() {
        return totalShoulderBashKills;
    }

    public double getTotalShoulderBashDamage() {
        return totalShoulderBashDamage;
    }

    public double getTotalGrenadeDamage() {
        return totalGrenadeDamage;
    }

    public int getTotalPowerWeaponKills() {
        return totalPowerWeaponKills;
    }

    public double getTotalPowerWeaponDamage() {
        return totalPowerWeaponDamage;
    }

    public int getTotalPowerWeaponGrabs() {
        return totalPowerWeaponGrabs;
    }

    public String getTotalPowerWeaponPossessionTime() {
        return totalPowerWeaponPossessionTime;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public int getTotalGamesCompleted() {
        return totalGamesCompleted;
    }

    public int getTotalGamesWon() {
        return totalGamesWon;
    }

    public int getTotalGamesLost() {
        return totalGamesLost;
    }

    public int getTotalGamesTied() {
        return totalGamesTied;
    }

    public String getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public int getTotalGrenadeKills() {
        return totalGrenadeKills;
    }

    public List<MedalAward> getMedalAwards() {
        return medalAwards;
    }

    public List<EnemyKill> getDestroyedEnemyVehicles() {
        return destroyedEnemyVehicles;
    }

    public List<EnemyKill> getEnemyKills() {
        return enemyKills;
    }

    public List<WeaponStats> getWeapons() {
        return weapons;
    }

    public List<Impulse> getImpulses() {
        return impulses;
    }

    public int getTotalSpartanKills() {
        return totalSpartanKills;
    }
}
