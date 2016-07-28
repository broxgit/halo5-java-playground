

package com.broxhouse.h5api.models.stats.matches;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {
    /**
     * The starting point that was used. When the "start" query string parameter is
     * specified, this value is identical. When "start" is omitted, the default value is
     * returned.
     */
    @SerializedName("Start")
    private int start;

    /**
     * The number of results that the service attempted to retrieve to satisfy this
     * request. Normally this value is equal to the "count" parameter. If the client
     * specified a count parameter greater than the maximum allowed, this value contains
     * the maximum allowed amount.
     */
    @SerializedName("Count")
    private int count;

    /**
     * The number of results that are actually being returned in this response. This field
     * is named "ResultCount" to avoid confusion with "Count".
     */
    @SerializedName("ResultCount")
    private int resultCount;

    /**
     * A list of recent matches. Matches are listed in chronological order with the most
     * recently started match first.
     */
    @SerializedName("Results")
    private Result results;

    public int getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }

    public int getResultCount() {
        return resultCount;
    }

    public Result getResults() {
        return results;
    }
}
