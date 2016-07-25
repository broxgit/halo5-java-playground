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

package com.broxhouse.h5api.models.stats.servicerecords;

import com.broxhouse.h5api.models.stats.common.FlexibleStats;
import com.broxhouse.h5api.models.stats.reports.BaseStats;

import java.util.List;

public class WarzoneStat extends BaseStats {

    public static class ScenarioStats extends GameBaseVariantStats {

        /**
         * The total number of "pies" (in-game currency) the player has earned in
         * the scenario.
         */
        @SerializedName("TotalPiesEarned")
        private long totalPiesEarned;

        /**
         * The game base variant specific stats. Flexible stats are available via
         * the Metadata API.
         */
        @SerializedName("FlexibleStats")
        private FlexibleStats flexibleStats;

        /**
         * The map global ID that this warzone scenario pertains to. Found in
         * metadata
         */
        @SerializedName("MapId")
        private String mapId;

        public long getTotalPiesEarned() {
            return totalPiesEarned;
        }

        public FlexibleStats getFlexibleStats() {
            return flexibleStats;
        }

        public String getMapId() {
            return mapId;
        }
    }

    /**
     * The total number of "pies" (in-game currency) the player has earned.
     */
    @SerializedName("TotalPiesEarned")
    private long totalPiesEarned;

    /**
     * List of scenario stats by map and game base variant id.
     */
    @SerializedName("ScenarioStats")
    private List<ScenarioStats> scenarioStats;
}
