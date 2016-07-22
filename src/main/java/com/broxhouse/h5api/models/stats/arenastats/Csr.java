package com.broxhouse.h5api.models.stats.arenastats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Csr {

    @SerializedName("Tier")
    @Expose
    private String tier;
    @SerializedName("DesignationId")
    @Expose
    private String designationId;
    @SerializedName("Csr")
    @Expose
    private String csr;
    @SerializedName("PercentToNextTier")
    @Expose
    private String percentToNextTier;
    @SerializedName("Rank")
    @Expose
    private String rank;

    /**
     * @return The tier
     */
    public String getTier() {
        return tier;
    }

    /**
     * @param tier The Tier
     */
    public void setTier(String tier) {
        this.tier = tier;
    }

    /**
     * @return The designationId
     */
    public String getDesignationId() {
        return designationId;
    }

    /**
     * @param designationId The DesignationId
     */
    public void setDesignationId(String designationId) {
        this.designationId = designationId;
    }

    /**
     * @return The csr
     */
    public String getCsr() {
        return csr;
    }

    /**
     * @param csr The Csr
     */
    public void setCsr(String csr) {
        this.csr = csr;
    }

    /**
     * @return The percentToNextTier
     */
    public String getPercentToNextTier() {
        return percentToNextTier;
    }

    /**
     * @param percentToNextTier The PercentToNextTier
     */
    public void setPercentToNextTier(String percentToNextTier) {
        this.percentToNextTier = percentToNextTier;
    }

    /**
     * @return The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * @param rank The Rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

}
