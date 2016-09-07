

package com.broxhouse.h5api.models.metadata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Medal implements Serializable {
//    @Override
//    public String getContentId() {
//        return null;
//    }
//
//    @Override
//    public String getLargeIconImageUrl() {
//        return null;
//    }
//
//    @Override
//    public String getSmallIconImageUrl() {
//        return null;
//    }
//
//    @Override
//    public boolean isUsableByPlayer() {
//        return false;
//    }
//
//    @Override
//    public String getType() {
//        return null;
//    }

    /**
     * A localized name for the medal, suitable for display to users.
     */
    @SerializedName("name")
    private String name;

    /**
     * A localized description, suitable for display to users.
     */
    @SerializedName("description")
    private String description;

    /**
     * The type of this medal. It will be one of the following options:
     * - Unknown
     * - Multi-kill
     * - Spree
     * - Style
     * - Vehicle
     * - Breakout
     * - Objective
     */
    private String classification;

    /**
     * The anticipated difficulty, relative to all other medals of this classification.
     * The difficulty is ordered from easiest to most difficult.
     */
    private int difficulty;

    /**
     * The location on the sprite sheet for the medal.
     */
    private SpriteLocation spriteLocation;

    /**
     * The ID that uniquely identifies this map medal.
     */
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

    public String getClassification() {
        return classification;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public SpriteLocation getSpriteLocation() {
        return spriteLocation;
    }

    public long getId() {
        return id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setSpriteLocation(SpriteLocation spriteLocation) {
        this.spriteLocation = spriteLocation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
