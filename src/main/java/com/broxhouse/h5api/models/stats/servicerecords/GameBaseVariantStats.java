

package com.broxhouse.h5api.models.stats.servicerecords;

import com.broxhouse.h5api.models.stats.common.FlexibleStats;
import com.broxhouse.h5api.models.stats.reports.BaseStats;
import com.google.gson.annotations.SerializedName;

public class GameBaseVariantStats extends BaseStats {

    /**
     * The ID of the game base variant. Game base variants are available via
     * the Metadata API.
     */
    @SerializedName("GameBaseVariantId")
    private String gameBaseVariantId;

    /**
     * The game base variant specific stats. Flexible stats are available via
     * the Metadata API.
     */
    @SerializedName("FlexibleStats")
    private FlexibleStats flexibleStats;

    public String getGameBaseVariantId() {
        return gameBaseVariantId;
    }

    public FlexibleStats getFlexibleStats() {
        return flexibleStats;
    }
}
