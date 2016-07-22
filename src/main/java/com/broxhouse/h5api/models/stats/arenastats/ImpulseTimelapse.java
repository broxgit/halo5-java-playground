package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ImpulseTimelapse {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Timelapse")
    @Expose
    private String timelapse;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The Id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The timelapse
     */
    public String getTimelapse() {
        return timelapse;
    }

    /**
     * @param timelapse The Timelapse
     */
    public void setTimelapse(String timelapse) {
        this.timelapse = timelapse;
    }

}
