

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VersusCarnageReport<S extends VersusPlayerStats> extends BaseCarnageReport<S> {

    /**
     * A list of stats for each team who in the match. Note that in Free For All modes,
     * there is an entry for every player.
     */
    @SerializedName("TeamStats")
    private List<TeamStats> teamStats;

}
