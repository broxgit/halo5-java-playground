
package com.broxhouse.h5api.models.stats.matchevents;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Links {

    @SerializedName("StatsMatchDetails")
    @Expose
    private StatsMatchDetails statsMatchDetails;
    @SerializedName("UgcFilmManifest")
    @Expose
    private UgcFilmManifest ugcFilmManifest;

    /**
     * 
     * @return
     *     The statsMatchDetails
     */
    public StatsMatchDetails getStatsMatchDetails() {
        return statsMatchDetails;
    }

    /**
     * 
     * @param statsMatchDetails
     *     The StatsMatchDetails
     */
    public void setStatsMatchDetails(StatsMatchDetails statsMatchDetails) {
        this.statsMatchDetails = statsMatchDetails;
    }

    /**
     * 
     * @return
     *     The ugcFilmManifest
     */
    public UgcFilmManifest getUgcFilmManifest() {
        return ugcFilmManifest;
    }

    /**
     * 
     * @param ugcFilmManifest
     *     The UgcFilmManifest
     */
    public void setUgcFilmManifest(UgcFilmManifest ugcFilmManifest) {
        this.ugcFilmManifest = ugcFilmManifest;
    }

}
