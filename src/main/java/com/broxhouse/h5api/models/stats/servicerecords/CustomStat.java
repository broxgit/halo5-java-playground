

package com.broxhouse.h5api.models.stats.servicerecords;

import com.broxhouse.h5api.models.stats.reports.BaseStats;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomStat extends BaseStats {

    /**
     * List of custom stats by CustomGameBaseVariant
     */
    @SerializedName("CustomGameBaseVariantStats")
    private List<GameBaseVariantStats> customGameBaseVariantStats;

    /**
     * A list of up to 3 game base variants with the highest win rate by the user.
     * If there is a tie, the one with more completions is higher. If there's still
     * a tie, the GUIDs are sorted and selected.
     */
    @SerializedName("TopGameBaseVariants")
    private List<VariantRecord> topGameBaseVariants;

    public List<GameBaseVariantStats> getCustomGameBaseVariantStats() {
        return customGameBaseVariantStats;
    }

    public List<VariantRecord> getTopGameBaseVariants() {
        return topGameBaseVariants;
    }
}
