/*
 * Copyright (c) 2015 Alex Hart
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.broxhouse.h5api.models.stats.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BaseStats {

    /**
     * Total number of kills done by the player. This includes melee kills, shoulder
     * bash kills and Spartan charge kills, all power weapons, AI kills and vehicle
     * destructions.
     */
    @JsonProperty("TotalKills")
    private int totalKills;

    /**
     * Total number of headshots done by the player.
     */
    @JsonProperty("TotalHeadshots")
    private int totalHeadshots;

    /**
     * Total weapon damage dealt by the player.
     */
    @JsonProperty("TotalWeaponDamage")
    private int totalWeaponDamage;

    /**
     * Total number of shots fired by the player.
     */
    @JsonProperty("TotalShotsFired")
    private int totalShotsFired;

    /**
     * Total number of shots landed by the player.
     */
    @JsonProperty("TotalShotsLanded")
    private int totalShotsLanded;

    /**
     * The weapon the player used to get the most kills this match.
     */
    @JsonProperty("WeaponWithMostKills")
    private WeaponStats weapon;

    /**
     * Total number of melee kills by the player.
     */
    @JsonProperty("TotalMeleeKills")
    private int totalMeleeKills;

    /**
     * Total melee damage dealt by the player.
     */
    @JsonProperty("TotalMeleeDamage")
    private double totalMeleeDamage;

    /**
     * Total number of assassinations by the player.
     */
    @JsonProperty("TotalAssassinations")
    private int totalAssassinations;

    /**
     * Total number of ground pound kills by the player.
     */
    @JsonProperty("TotalGroundPoundKills")
    private int totalGroundPoundKills;

    /**
     * Total ground pound damage dealt by the player.
     */
    @JsonProperty("TotalGroundPoundDamage")
    private double totalGroundPoundDamage;

    /**
     * Total number of shoulder bash kills by the player.
     */
    @JsonProperty("TotalShoulderBashKills")
    private int totalShoulderBashKills;

    /**
     * Total shoulder bash damage dealt by the player.
     */
    @JsonProperty("TotalShoulderBashDamage")
    private double totalShoulderBashDamage;

    /**
     * Total grenade damage dealt by the player.
     */
    @JsonProperty("TotalGrenadeDamage")
    private double totalGrenadeDamage;

    /**
     * Total number of power weapon kills by the player.
     */
    @JsonProperty("TotalPowerWeaponKills")
    private int totalPowerWeaponKills;

    /**
     * Total power weapon damage dealt by the player.
     */
    @JsonProperty("TotalPowerWeaponDamage")
    private double totalPowerWeaponDamage;

    /**
     * Total number of power weapon grabs by the player.
     */
    @JsonProperty("TotalPowerWeaponGrabs")
    private int totalPowerWeaponGrabs;

    /**
     * Total power weapon possession by the player. This is expressed as an ISO 8601
     * Duration.
     */
    @JsonProperty("TotalPowerWeaponPossessionTime")
    private String totalPowerWeaponPossessionTime;

    /**
     * Total number of deaths by the player.
     */
    @JsonProperty("TotalDeaths")
    private int totalDeaths;

    /**
     * Total number of assists by the player.
     */
    @JsonProperty("TotalAssists")
    private int totalAssists;

    /**
     * Not used.
     */
    @JsonProperty("TotalGamesCompleted")
    private int totalGamesCompleted;

    /**
     * Not used.
     */
    @JsonProperty("TotalGamesWon")
    private int totalGamesWon;

    /**
     * Not used.
     */
    @JsonProperty("TotalGamesLost")
    private int totalGamesLost;

    /**
     * Not used.
     */
    @JsonProperty("TotalGamesTied")
    private int totalGamesTied;

    /**
     * Total timed played in this match by the player.
     */
    @JsonProperty("TotalTimePlayed")
    private String totalTimePlayed;

    /**
     * Total number of grenade kills by the player.
     */
    @JsonProperty("TotalGrenadeKills")
    private int totalGrenadeKills;

    /**
     * The set of Medals earned by the player.
     */
    @JsonProperty("MedalAwards")
    private List<MedalAward> medalAwards;

    /**
     * List of enemy vehicles destroyed. Vehicles are available via the Metadata API.
     * Note: this stat measures enemy vehicles, not any vehicle destruction.
     */
    @JsonProperty("DestroyedEnemyVehicles")
    private List<EnemyKill> destroyedEnemyVehicles;

    /**
     * List of enemies killed, per enemy type. Enemies are available via the Metadata
     * API.
     */
    @JsonProperty("EnemyKills")
    private List<EnemyKill> enemyKills;

    /**
     * The set of weapons (weapons and vehicles included) used by the player.
     */
    @JsonProperty("WeaponStats")
    private List<WeaponStats> weapons;

    /**
     * The set of Impulses (invisible Medals) earned by the player.
     */
    @JsonProperty("Impulses")
    private List<Impulse> impulses;

    /**
     * Total number of Spartan kills by the player.
     */
    @JsonProperty("TotalSpartanKills")
    private int totalSpartanKills;

    public int getTotalKills() {
        return totalKills;
    }

    public int getTotalHeadshots() {
        return totalHeadshots;
    }

    public int getTotalWeaponDamage() {
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
