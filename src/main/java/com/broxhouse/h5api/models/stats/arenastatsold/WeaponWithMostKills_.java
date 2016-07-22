package com.broxhouse.h5api.models.stats.arenastatsold;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class WeaponWithMostKills_ {

    @SerializedName("WeaponId")
    @Expose
    private WeaponId__ weaponId;
    @SerializedName("TotalShotsFired")
    @Expose
    private String totalShotsFired;
    @SerializedName("TotalShotsLanded")
    @Expose
    private String totalShotsLanded;
    @SerializedName("TotalHeadshots")
    @Expose
    private String totalHeadshots;
    @SerializedName("TotalKills")
    @Expose
    private String totalKills;
    @SerializedName("TotalDamageDealt")
    @Expose
    private String totalDamageDealt;
    @SerializedName("TotalPossessionTime")
    @Expose
    private String totalPossessionTime;

    /**
     * @return The weaponId
     */
    public WeaponId__ getWeaponId() {
        return weaponId;
    }

    /**
     * @param weaponId The WeaponId
     */
    public void setWeaponId(WeaponId__ weaponId) {
        this.weaponId = weaponId;
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
     * @return The totalDamageDealt
     */
    public String getTotalDamageDealt() {
        return totalDamageDealt;
    }

    /**
     * @param totalDamageDealt The TotalDamageDealt
     */
    public void setTotalDamageDealt(String totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    /**
     * @return The totalPossessionTime
     */
    public String getTotalPossessionTime() {
        return totalPossessionTime;
    }

    /**
     * @param totalPossessionTime The TotalPossessionTime
     */
    public void setTotalPossessionTime(String totalPossessionTime) {
        this.totalPossessionTime = totalPossessionTime;
    }



}
