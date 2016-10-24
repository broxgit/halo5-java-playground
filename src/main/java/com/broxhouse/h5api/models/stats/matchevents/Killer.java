
package com.broxhouse.h5api.models.stats.matchevents;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class Killer implements Serializable {

    @SerializedName("Gamertag")
    @Expose
    private String gamertag;
    @SerializedName("Xuid")
    @Expose
    private Object xuid;

    /**
     * 
     * @return
     *     The gamertag
     */
    public String getGamertag() {
        return gamertag;
    }

    /**
     * 
     * @param gamertag
     *     The Gamertag
     */
    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }

    /**
     * 
     * @return
     *     The xuid
     */
    public Object getXuid() {
        return xuid;
    }

    /**
     * 
     * @param xuid
     *     The Xuid
     */
    public void setXuid(Object xuid) {
        this.xuid = xuid;
    }

}
