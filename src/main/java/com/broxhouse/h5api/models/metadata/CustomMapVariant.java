
package com.broxhouse.h5api.models.metadata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CustomMapVariant {

    @SerializedName("BaseMap")
    private BaseMap baseMap;
    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("AccessControl")
    private Integer accessControl;
    @SerializedName("Links")
    private Links links;
    @SerializedName("CreationTimeUtc")
    private CreationTimeUtc creationTimeUtc;
    @SerializedName("LastModifiedTimeUtc")
    private LastModifiedTimeUtc lastModifiedTimeUtc;
    @SerializedName("Banned")
    private Boolean banned;
    @SerializedName("Identity")
    private Identity identity;
    @SerializedName("Stats")
    private Stats stats;
    private int count = 0;

    /**
     * 
     * @return
     *     The baseMap
     */
    public BaseMap getBaseMap() {
        return baseMap;
    }

    /**
     * 
     * @param baseMap
     *     The BaseMap
     */
    public void setBaseMap(BaseMap baseMap) {
        this.baseMap = baseMap;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The accessControl
     */
    public Integer getAccessControl() {
        return accessControl;
    }

    /**
     * 
     * @param accessControl
     *     The AccessControl
     */
    public void setAccessControl(Integer accessControl) {
        this.accessControl = accessControl;
    }

    /**
     * 
     * @return
     *     The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The Links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     * 
     * @return
     *     The creationTimeUtc
     */
    public CreationTimeUtc getCreationTimeUtc() {
        return creationTimeUtc;
    }

    /**
     * 
     * @param creationTimeUtc
     *     The CreationTimeUtc
     */
    public void setCreationTimeUtc(CreationTimeUtc creationTimeUtc) {
        this.creationTimeUtc = creationTimeUtc;
    }

    /**
     * 
     * @return
     *     The lastModifiedTimeUtc
     */
    public LastModifiedTimeUtc getLastModifiedTimeUtc() {
        return lastModifiedTimeUtc;
    }

    /**
     * 
     * @param lastModifiedTimeUtc
     *     The LastModifiedTimeUtc
     */
    public void setLastModifiedTimeUtc(LastModifiedTimeUtc lastModifiedTimeUtc) {
        this.lastModifiedTimeUtc = lastModifiedTimeUtc;
    }

    /**
     * 
     * @return
     *     The banned
     */
    public Boolean getBanned() {
        return banned;
    }

    /**
     * 
     * @param banned
     *     The Banned
     */
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    /**
     * 
     * @return
     *     The identity
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * 
     * @param identity
     *     The Identity
     */
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    /**
     * 
     * @return
     *     The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * 
     * @param stats
     *     The Stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return this.count;
    }
}


