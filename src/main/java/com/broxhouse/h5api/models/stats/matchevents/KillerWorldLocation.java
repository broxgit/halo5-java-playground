
package com.broxhouse.h5api.models.stats.matchevents;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class KillerWorldLocation implements Serializable {

    @SerializedName("x")
    @Expose
    private Double x;
    @SerializedName("y")
    @Expose
    private Double y;
    @SerializedName("z")
    @Expose
    private Double z;

    /**
     * 
     * @return
     *     The x
     */
    public Double getX() {
        return x;
    }

    /**
     * 
     * @param x
     *     The x
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * 
     * @return
     *     The y
     */
    public Double getY() {
        return y;
    }

    /**
     * 
     * @param y
     *     The y
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * 
     * @return
     *     The z
     */
    public Double getZ() {
        return z;
    }

    /**
     * 
     * @param z
     *     The z
     */
    public void setZ(Double z) {
        this.z = z;
    }

}
