package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MedalAward__ {

    @SerializedName("MedalId")
    @Expose
    private String medalId;
    @SerializedName("Count")
    @Expose
    private String count;

    /**
     * @return The medalId
     */
    public String getMedalId() {
        return medalId;
    }

    /**
     * @param medalId The MedalId
     */
    public void setMedalId(String medalId) {
        this.medalId = medalId;
    }

    /**
     * @return The count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count The Count
     */
    public void setCount(String count) {
        this.count = count;
    }

}
