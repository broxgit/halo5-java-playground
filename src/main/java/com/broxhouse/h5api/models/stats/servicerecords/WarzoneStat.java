

package com.broxhouse.h5api.models.stats.servicerecords;

import com.broxhouse.h5api.models.stats.common.FlexibleStats;
import com.broxhouse.h5api.models.stats.reports.BaseStats;
import com.google.gson.annotations.SerializedName;

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
