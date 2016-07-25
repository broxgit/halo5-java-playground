

package com.broxhouse.h5api.models.stats.servicerecords;

import com.google.gson.annotations.SerializedName;

public class CampaignResult extends BaseServiceRecordResult {

    @SerializedName("CampaignStat")
    private CampaignStat campaignStat;

    public CampaignStat getCampaignStat() {
        return campaignStat;
    }
}
