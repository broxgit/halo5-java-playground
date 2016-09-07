

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CampaignResult extends BaseServiceRecordResult implements Serializable {

    @SerializedName("CampaignStat")
    private CampaignStat campaignStat;

    public CampaignStat getCampaignStat() {
        return campaignStat;
    }
}
