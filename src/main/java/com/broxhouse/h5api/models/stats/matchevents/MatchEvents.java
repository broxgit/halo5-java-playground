
package com.broxhouse.h5api.models.stats.matchevents;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class MatchEvents {

    @SerializedName("Links")
    @Expose
    private Links links;
    @SerializedName("GameEvents")
    @Expose
    private List<GameEvent> gameEvents = new ArrayList<GameEvent>();
    @SerializedName("IsCompleteSetOfEvents")
    @Expose
    private Boolean isCompleteSetOfEvents;

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
     *     The gameEvents
     */
    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }

    /**
     * 
     * @param gameEvents
     *     The GameEvents
     */
    public void setGameEvents(List<GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
    }

    /**
     * 
     * @return
     *     The isCompleteSetOfEvents
     */
    public Boolean getIsCompleteSetOfEvents() {
        return isCompleteSetOfEvents;
    }

    /**
     * 
     * @param isCompleteSetOfEvents
     *     The IsCompleteSetOfEvents
     */
    public void setIsCompleteSetOfEvents(Boolean isCompleteSetOfEvents) {
        this.isCompleteSetOfEvents = isCompleteSetOfEvents;
    }

}
