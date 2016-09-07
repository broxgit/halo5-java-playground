

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WarzoneResult extends BaseServiceRecordResult implements Serializable {

    @SerializedName("WarzoneStat")
    private WarzoneStat warzoneStat;

    public WarzoneStat getWarzoneStat() {
        return warzoneStat;
    }
}
