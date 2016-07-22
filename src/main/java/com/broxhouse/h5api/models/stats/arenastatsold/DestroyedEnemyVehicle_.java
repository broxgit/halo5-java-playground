package com.broxhouse.h5api.models.stats.arenastatsold;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DestroyedEnemyVehicle_ {

    @SerializedName("Enemy")
    @Expose
    private Enemy__ enemy;
    @SerializedName("TotalKills")
    @Expose
    private String totalKills;

    /**
     * @return The enemy
     */
    public Enemy__ getEnemy() {
        return enemy;
    }

    /**
     * @param enemy The Enemy
     */
    public void setEnemy(Enemy__ enemy) {
        this.enemy = enemy;
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

}
