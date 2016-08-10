
package com.broxhouse.h5api.models.metadata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Stats {

    @SerializedName("BookmarkCount")
    @Expose
    private Integer bookmarkCount;
    @SerializedName("HasCallerBookmarked")
    @Expose
    private Boolean hasCallerBookmarked;

    /**
     * 
     * @return
     *     The bookmarkCount
     */
    public Integer getBookmarkCount() {
        return bookmarkCount;
    }

    /**
     * 
     * @param bookmarkCount
     *     The BookmarkCount
     */
    public void setBookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    /**
     * 
     * @return
     *     The hasCallerBookmarked
     */
    public Boolean getHasCallerBookmarked() {
        return hasCallerBookmarked;
    }

    /**
     * 
     * @param hasCallerBookmarked
     *     The HasCallerBookmarked
     */
    public void setHasCallerBookmarked(Boolean hasCallerBookmarked) {
        this.hasCallerBookmarked = hasCallerBookmarked;
    }

}
