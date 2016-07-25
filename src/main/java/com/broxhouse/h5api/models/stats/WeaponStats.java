package com.broxhouse.h5api.models.stats;

/**
 * Created by Brock Berrett on 7/20/2016.
 */

import com.google.gson.annotations.SerializedName;

public class WeaponStats
{
    @SerializedName("WeaponId")
    private WeaponId weaponId;

    /**
     * The number of shots fired for this weapon.
     */
    @SerializedName("TotalShotsFired")
    private int totalShotsFired;

    /**
     * The number of shots landed for this weapon.
     */
    @SerializedName("TotalShotsLanded")
    private int totalShotsLanded;

    /**
     * The number of headshots for this weapon.
     */
    @SerializedName("TotalHeadshots")
    private int totalHeadshots;

    /**
     * The number of kills for this weapon.
     */
    @SerializedName("TotalKills")
    private int totalKills;

    /**
     * The total damage dealt for this weapon.
     */
    @SerializedName("TotalDamageDealt")
    private double totalDamageDealt;

    /**
     * The total possession time for this weapon. This is expressed as an ISO 8601
     * Duration.
     */
    @SerializedName("TotalPossessionTime")
    private String totalPossessionTime;

    public WeaponId getWeaponId() {
        return weaponId;
    }

    public int getTotalShotsFired() {
        return totalShotsFired;
    }

    public int getTotalShotsLanded() {
        return totalShotsLanded;
    }

    public int getTotalHeadshots() {
        return totalHeadshots;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public double getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public String getTotalPossessionTime() {
        return totalPossessionTime;
    }

}
