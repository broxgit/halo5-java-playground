package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EnemyKill {

    @SerializedName("Enemy")
    @Expose
    private Enemy_ enemy;
    @SerializedName("TotalKills")
    @Expose
    private String totalKills;

    /**
     * @return The enemy
     */
    public Enemy_ getEnemy() {
        return enemy;
    }

    /**
     * @param enemy The Enemy
     */
    public void setEnemy(Enemy_ enemy) {
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
