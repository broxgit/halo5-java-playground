
package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

public class Csr {
    /**
     * The Csr tier.
     */
    @SerializedName("Tier")
    private int tier;

    /**
     * The Designation of the Csr. Options are:
     * 1 through 5: Normal designations
     * 6 and 7: Semi-pro and Pro respectively
     */
    @SerializedName("DesignationId")
    private int designationId;

    /**
     * The Csr value. Zero for normal designations.
     */
    @SerializedName("Csr")
    private int csr;

    /**
     * The percentage of progress towards the next Csr tier.
     */
    @SerializedName("PercentToNextTier")
    private int percentToNextTier;

    /**
     * If the Csr is Semi-pro or Pro, the player's leaderboard ranking.
     */
    @SerializedName("Rank")
    private int rank;

    public int getTier() {
        return tier;
    }

    public int getDesignationId() {
        return designationId;
    }

    public int getCsr() {
        return csr;
    }

    public int getPercentToNextTier() {
        return percentToNextTier;
    }

    public int getRank() {
        return rank;
    }
}
