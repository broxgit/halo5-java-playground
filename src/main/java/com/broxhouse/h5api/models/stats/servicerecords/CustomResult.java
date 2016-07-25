

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

public class CustomResult extends BaseServiceRecordResult {

    @SerializedName("CustomStats")
    private CustomStat customStat;

    public CustomStat getCustomStat() {
        return customStat;
    }
}
