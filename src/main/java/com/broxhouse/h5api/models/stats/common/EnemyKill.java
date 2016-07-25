

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

public class EnemyKill {

    /**
     * The enemy this entry references
     */
    @SerializedName("Enemy")
    private Enemy enemy;

    /**
     * Total number of kills on the enemy by the player
     */
    @SerializedName("TotalKills")
    private int kills;

    public Enemy getEnemy() {
        return enemy;
    }

    public int getKills() {
        return kills;
    }
}
