

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CampaignCarnageReport extends BaseCarnageReport<CampaignPlayerStats> {

    /**
     * The total playthrough time of the mission as calculated by the game. This value is
     * persisted in save files.
     */
    @SerializedName("TotalMissionPlaythroughTime")
    private String totalMissionPlaythroughTime;

    /** The difficulty the mission was played at. Options are:
     * Easy = 0,
     * Normal = 1,
     * Heroic = 2,
     * Legendary = 3
     */
    @SerializedName("Difficulty")
    private int difficulty;

    /**
     * The list of skulls used for the mission. Skulls are available via the Metadata API.
     */
    @SerializedName("Skulls")
    private List<Integer> skulls;

    /**
     * Indicates whether the mission was completed when the match ended.
     */
    @SerializedName("MissionCompleted")
    private boolean missionCompleted;

    public String getTotalMissionPlaythroughTime() {
        return totalMissionPlaythroughTime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public List<Integer> getSkulls() {
        return skulls;
    }

    public boolean isMissionCompleted() {
        return missionCompleted;
    }
}
