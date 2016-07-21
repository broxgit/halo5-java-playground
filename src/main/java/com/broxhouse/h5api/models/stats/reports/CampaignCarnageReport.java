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

package com.broxhouse.h5api.models.stats.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CampaignCarnageReport extends BaseCarnageReport<CampaignPlayerStats> {

    /**
     * The total playthrough time of the mission as calculated by the game. This value is
     * persisted in save files.
     */
    @JsonProperty("TotalMissionPlaythroughTime")
    private String totalMissionPlaythroughTime;

    /** The difficulty the mission was played at. Options are:
     * Easy = 0,
     * Normal = 1,
     * Heroic = 2,
     * Legendary = 3
     */
    @JsonProperty("Difficulty")
    private int difficulty;

    /**
     * The list of skulls used for the mission. Skulls are available via the Metadata API.
     */
    @JsonProperty("Skulls")
    private List<Integer> skulls;

    /**
     * Indicates whether the mission was completed when the match ended.
     */
    @JsonProperty("MissionCompleted")
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
