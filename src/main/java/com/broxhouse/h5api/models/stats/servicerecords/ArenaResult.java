

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ArenaResult extends BaseServiceRecordResult implements Serializable {

    @SerializedName("ArenaStats")
    private ArenaStat arenaStat;

    public ArenaStat getArenaStat() {
        return arenaStat;
    }
}
