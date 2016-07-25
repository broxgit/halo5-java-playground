

package com.broxhouse.h5api.models.stats.servicerecords;


import com.google.gson.annotations.SerializedName;

public class PlayerQueryResponse<T> {

    /**
     * The player's gamertag.
     */
    @SerializedName("Id")
    private String id;

    /**
     * The result of the query for the player. One of the following:
     *   Success = 0,
     *   NotFound = 1,
     *   ServiceFailure = 2,
     *   ServiceUnavailable = 3
     * It is possible for different requests from the batch to succeed and fail
     * independently.
     */
    @SerializedName("ResultCode")
    private int resultCode;

    /**
     * The Service Record result for the player. Only set if ResultCode is Success.
     */
    @SerializedName("Result")
    private T result;

    public String getId() {
        return id;
    }

    public int getResultCode() {
        return resultCode;
    }

    public T getResult() {
        return result;
    }
}
