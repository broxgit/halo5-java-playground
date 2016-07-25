

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;

public class Impulse {

    /**
     * The ID of the Impulse. Impulses are available via the Metadata API.
     */
    @SerializedName("Id")
    private long id;

    /**
     * The number of times the Impuse was earned.
     */
    @SerializedName("Count")
    private int count;

    public long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }
}
