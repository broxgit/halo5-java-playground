
package com.broxhouse.h5api.models.stats.reports;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TeamStat {

    @SerializedName("TeamId")
    @Expose
    private Integer teamId;
    @SerializedName("Score")
    @Expose
    private Integer score;
    @SerializedName("Rank")
    @Expose
    private Integer rank;


    /**
     * 
     * @return
     *     The teamId
     */
    public Integer getTeamId() {
        return teamId;
    }

    /**
     * 
     * @param teamId
     *     The TeamId
     */
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    /**
     * 
     * @return
     *     The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 
     * @param score
     *     The Score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 
     * @return
     *     The rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 
     * @param rank
     *     The Rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }



}
