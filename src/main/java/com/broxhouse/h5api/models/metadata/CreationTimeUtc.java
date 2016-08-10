
package com.broxhouse.h5api.models.metadata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CreationTimeUtc {

    @SerializedName("ISO8601Date")
    @Expose
    private String iSO8601Date;

    /**
     * 
     * @return
     *     The iSO8601Date
     */
    public String getISO8601Date() {
        return iSO8601Date;
    }

    /**
     * 
     * @param iSO8601Date
     *     The ISO8601Date
     */
    public void setISO8601Date(String iSO8601Date) {
        this.iSO8601Date = iSO8601Date;
    }

}
