

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomResult extends BaseServiceRecordResult implements Serializable {

    @SerializedName("CustomStats")
    private CustomStat customStat;

    public CustomStat getCustomStat() {
        return customStat;
    }
}
