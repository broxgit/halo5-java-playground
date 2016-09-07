package com.broxhouse.h5api.models.metadata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Brock Berrett on 7/21/2016.
 */
public class Weapon extends MetaData implements Serializable{
    /**
     * A localized name for the object, suitable for display to users. The text is title
     * cased.
     */
    @SerializedName("name")
    private String name;

    /**
     * A localized description, suitable for display to users.
     */
    @SerializedName("description")
    private String description;

    /**
     * The type of the weapon. Options are:
     *   - Grenade
     *   - Turret
     *   - Vehicle
     *   - Standard
     *   - Power
     */
    @SerializedName("type")
    private String type;

    /**
     * A reference to a large image for icon use. This may be null if there is no image
     * defined.
     */
    @SerializedName("largeIconImageUrl")
    private String largeIconImageUrl;

    /**
     * A reference to a small image for icon use. This may be null if there is no image
     * defined.
     */
    @SerializedName("smallIconImageUrl")
    private String smallIconImageUrl;

    /**
     * Indicates whether the weapon is usable by a player.
     */
    @SerializedName("isUsableByPlayer")
    private boolean isUsableByPlayer;

    /**
     * The ID that uniquely identifies the weapon.
     */
    @SerializedName("id")
    private long id;

    /**
     * Internal use only. Do not use.
     */
    @SerializedName("contentId")
    private String contentId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getLargeIconImageUrl() {
        return largeIconImageUrl;
    }

    public String getSmallIconImageUrl() {
        return smallIconImageUrl;
    }

    public boolean isUsableByPlayer() {
        return isUsableByPlayer;
    }

    public long getId() {
        return id;
    }

    public String getContentId(){return contentId;}

    public String toString()
    {
        return getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLargeIconImageUrl(String largeIconImageUrl) {
        this.largeIconImageUrl = largeIconImageUrl;
    }

    public void setSmallIconImageUrl(String smallIconImageUrl) {
        this.smallIconImageUrl = smallIconImageUrl;
    }

    public void setUsableByPlayer(boolean usableByPlayer) {
        isUsableByPlayer = usableByPlayer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
