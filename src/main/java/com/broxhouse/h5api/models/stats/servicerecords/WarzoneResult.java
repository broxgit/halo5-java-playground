

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

public class WarzoneResult extends BaseServiceRecordResult {

    @SerializedName("WarzoneStat")
    private WarzoneStat warzoneStat;

    public WarzoneStat getWarzoneStat() {
        return warzoneStat;
    }
}
