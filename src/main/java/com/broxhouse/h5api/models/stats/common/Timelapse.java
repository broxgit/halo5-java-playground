

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

public class Timelapse {
    /**
     * The ID of the flexible stat.
     */
    @SerializedName("Id")
    private String id;

    /**
     * The amount of time the flexible stat was earned for. This is expressed as
     * an ISO 8601 Duration.
     */
    @SerializedName("Timelapse")
    private String timelapse;

    public String getId() {
        return id;
    }

    public String getTimelapse() {
        return timelapse;
    }
}
