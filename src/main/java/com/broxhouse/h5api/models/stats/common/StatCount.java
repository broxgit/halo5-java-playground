

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatCount implements Serializable {
    /**
     * The ID of the flexible stat.
     */
    @SerializedName("Id")
    private String id;

    /**
     * The number of times this flexible stat was earned.
     */
    @SerializedName("Count")
    private int count;

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }
}
