package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PlayerId {

    @SerializedName("GamerTag")
    @Expose
    private String gamerTag;
    @SerializedName("Xuid")
    @Expose
    private Object xuid;

    /**
     * @return The gamerTag
     */
    public String getGamerTag() {
        return gamerTag;
    }

    /**
     * @param gamerTag The GamerTag
     */
    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    /**
     * @return The xuid
     */
    public Object getXuid() {
        return xuid;
    }

    /**
     * @param xuid The Xuid
     */
    public void setXuid(Object xuid) {
        this.xuid = xuid;
    }

}
