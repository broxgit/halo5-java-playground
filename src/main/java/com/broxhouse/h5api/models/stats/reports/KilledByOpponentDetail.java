
package com.broxhouse.h5api.models.stats.reports;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class KilledByOpponentDetail implements Serializable {

    @SerializedName("GamerTag")
    @Expose
    private String gamerTag;
    @SerializedName("TotalKills")
    @Expose
    private Integer totalKills;
    private int killedEnemyTotal = 0;
    private int totalTimesPlayedAgainst = 0;

    public KilledByOpponentDetail(String name, Integer totalKills){
        this.totalKills = totalKills;
        this.gamerTag = name;
    }

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

    public int getTotalTimesPlayedAgainst(){return totalTimesPlayedAgainst;}

    /**
     * 
     * @param totalKills
     *     The TotalKills
     */
    public void setTotalKills(Integer totalKills) {
        this.totalKills = totalKills;
    }

    public void setTotalTimesPlayedAgainst(int timesPlayed){this.totalTimesPlayedAgainst = timesPlayed;}

    public void setKilledEnemyTotal(int totalKills){this.killedEnemyTotal = totalKills;}

}
