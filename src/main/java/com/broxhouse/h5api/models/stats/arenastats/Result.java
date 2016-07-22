package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Result {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("ResultCode")
    @Expose
    private String resultCode;
    @SerializedName("Result")
    @Expose
    private Result_ result;

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
     * @return The resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode The ResultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * @return The result
     */
    public Result_ getResult() {
        return result;
    }

    /**
     * @param result The Result
     */
    public void setResult(Result_ result) {
        this.result = result;
    }

}
