

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

public class ArenaResult extends BaseServiceRecordResult {

    @SerializedName("ArenaStats")
    private ArenaStat arenaStat;

    public ArenaStat getArenaStat() {
        return arenaStat;
    }
}
