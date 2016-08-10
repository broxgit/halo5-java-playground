
package com.broxhouse.h5api.models.stats.reports;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class KilledByOpponentDetail {

    @SerializedName("GamerTag")
    @Expose
    private String gamerTag;
    @SerializedName("TotalKills")
    @Expose
    private Integer totalKills;
    private int killedEnemyTotal = 0;

    /**
     * 
     * @return
     *     The gamerTag
     */
    public String getGamerTag() {
        return gamerTag;
    }

    /**
     * 
     * @param gamerTag
     *     The GamerTag
     */
    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    /**
     * 
     * @return
     *     The totalKills
     */
    public Integer getTotalKills() {
        return totalKills;
    }

    public int getKilledEnemyTotal(){return killedEnemyTotal;}

    /**
     * 
     * @param totalKills
     *     The TotalKills
     */
    public void setTotalKills(Integer totalKills) {
        this.totalKills = totalKills;
    }

    public void setKilledEnemyTotal(int totalKills){this.killedEnemyTotal = totalKills;}

}
