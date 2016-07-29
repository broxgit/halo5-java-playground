

package com.broxhouse.h5api.models.stats.common;

import com.google.gson.annotations.SerializedName;


public class MedalAward {

    /**
     * The ID of the Medal. Medals are available via the Metadata API.
     */
    @SerializedName("MedalId")
    private long medalId;

    /**
     * The number of times the Medal was earned.
     */
    @SerializedName("Count")
    private int count;

    private String name;

    public long getMedalId() {
        return medalId;
    }


    public int getCount() {
        return count;
    }

    public String getName(){return name;}


    public void setMedalId(long medalId){
        this.medalId = medalId;
    }

    public void setCount(Integer count){
        this.count = count;
    }

    public void setName(String name){this.name = name;}

}
