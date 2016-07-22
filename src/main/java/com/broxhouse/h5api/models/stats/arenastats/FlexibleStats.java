package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class FlexibleStats {

    @SerializedName("MedalStatCounts")
    @Expose
    private List<MedalStatCount> medalStatCounts = new ArrayList<MedalStatCount>();
    @SerializedName("ImpulseStatCounts")
    @Expose
    private List<ImpulseStatCount> impulseStatCounts = new ArrayList<ImpulseStatCount>();
    @SerializedName("MedalTimelapses")
    @Expose
    private List<MedalTimelapse> medalTimelapses = new ArrayList<MedalTimelapse>();
    @SerializedName("ImpulseTimelapses")
    @Expose
    private List<ImpulseTimelapse> impulseTimelapses = new ArrayList<ImpulseTimelapse>();

    /**
     * @return The medalStatCounts
     */
    public List<MedalStatCount> getMedalStatCounts() {
        return medalStatCounts;
    }

    /**
     * @param medalStatCounts The MedalStatCounts
     */
    public void setMedalStatCounts(List<MedalStatCount> medalStatCounts) {
        this.medalStatCounts = medalStatCounts;
    }

    /**
     * @return The impulseStatCounts
     */
    public List<ImpulseStatCount> getImpulseStatCounts() {
        return impulseStatCounts;
    }

    /**
     * @param impulseStatCounts The ImpulseStatCounts
     */
    public void setImpulseStatCounts(List<ImpulseStatCount> impulseStatCounts) {
        this.impulseStatCounts = impulseStatCounts;
    }

    /**
     * @return The medalTimelapses
     */
    public List<MedalTimelapse> getMedalTimelapses() {
        return medalTimelapses;
    }

    /**
     * @param medalTimelapses The MedalTimelapses
     */
    public void setMedalTimelapses(List<MedalTimelapse> medalTimelapses) {
        this.medalTimelapses = medalTimelapses;
    }

    /**
     * @return The impulseTimelapses
     */
    public List<ImpulseTimelapse> getImpulseTimelapses() {
        return impulseTimelapses;
    }

    /**
     * @param impulseTimelapses The ImpulseTimelapses
     */
    public void setImpulseTimelapses(List<ImpulseTimelapse> impulseTimelapses) {
        this.impulseTimelapses = impulseTimelapses;
    }

}
