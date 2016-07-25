

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlexibleStats {
    /**
     * The set of flexible stats that are derived from medal events.
     */
    @SerializedName("MedalStatCounts")
    private List<StatCount> medalStatCounts;

    /**
     * The set of flexible stats that are derived from impulse events.
     */
    @SerializedName("ImpulseStatCounts")
    private List<StatCount> impulseStatCounts;

    /**
     * The set of flexible stats that are derived from medal time lapses.
     */
    @SerializedName("MedalTimelapses")
    private List<Timelapse> medalTimelapses;

    /**
     * The set of flexible stats that are derived from impulse time lapses.
     */
    @SerializedName("ImpulseTimelapses")
    private List<Timelapse> impulseTimelapses;

    public List<StatCount> getMedalStatCounts() {
        return medalStatCounts;
    }

    public List<StatCount> getImpulseStatCounts() {
        return impulseStatCounts;
    }

    public List<Timelapse> getMedalTimelapses() {
        return medalTimelapses;
    }

    public List<Timelapse> getImpulseTimelapses() {
        return impulseTimelapses;
    }
}
