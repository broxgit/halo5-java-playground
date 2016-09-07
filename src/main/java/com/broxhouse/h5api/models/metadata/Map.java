

package com.broxhouse.h5api.models.metadata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Map implements Serializable {
    /**
     * A localized name, suitable for display to users.
     */
    private String name;

    /**
     * A localized description, suitable for display to users.
     */
    private String description;



    /**
     * This lists all the game modes to which this map is available. Options are:
     * - Arena
     * - Campaign
     * - Custom

     * - Warzone
     */
    private List<String> supportedGameModes;

    /**
     * A reference to an image. This may be null if there is no image defined.
     */
    private String imageUrl;

    /**
     * The ID that uniquely identifies this map.
     */
    private String id;

    private int count = 0;

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

    public List<String> getSupportedGameModes() {
        return supportedGameModes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSupportedGameModes(List<String> supportedGameModes) {
        this.supportedGameModes = supportedGameModes;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
