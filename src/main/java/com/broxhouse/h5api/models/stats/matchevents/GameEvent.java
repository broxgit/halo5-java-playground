
package com.broxhouse.h5api.models.stats.matchevents;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GameEvent {

    @SerializedName("RoundIndex")
    @Expose
    private Integer roundIndex;
    @SerializedName("EventName")
    @Expose
    private String eventName;
    @SerializedName("TimeSinceStart")
    @Expose
    private String timeSinceStart;
    @SerializedName("Player")
    @Expose
    private Player player;
    @SerializedName("WeaponAttachmentIds")
    @Expose
    private List<Integer> weaponAttachmentIds = new ArrayList<Integer>();
    @SerializedName("WeaponStockId")
    @Expose
    private Integer weaponStockId;
    @SerializedName("Assistants")
    @Expose
    private List<Object> assistants = new ArrayList<Object>();
    @SerializedName("DeathDisposition")
    @Expose
    private Integer deathDisposition;
    @SerializedName("IsAssassination")
    @Expose
    private Boolean isAssassination;
    @SerializedName("IsGroundPound")
    @Expose
    private Boolean isGroundPound;
    @SerializedName("IsHeadshot")
    @Expose
    private Boolean isHeadshot;
    @SerializedName("IsMelee")
    @Expose
    private Boolean isMelee;
    @SerializedName("IsShoulderBash")
    @Expose
    private Boolean isShoulderBash;
    @SerializedName("IsWeapon")
    @Expose
    private Boolean isWeapon;
    @SerializedName("Killer")
    @Expose
    private Killer killer;
    @SerializedName("KillerAgent")
    @Expose
    private Integer killerAgent;
    @SerializedName("KillerWeaponAttachmentIds")
    @Expose
    private List<Integer> killerWeaponAttachmentIds = new ArrayList<Integer>();
    @SerializedName("KillerWeaponStockId")
    @Expose
    private Integer killerWeaponStockId;
    @SerializedName("KillerWorldLocation")
    @Expose
    private KillerWorldLocation killerWorldLocation;
    @SerializedName("Victim")
    @Expose
    private Victim victim;
    @SerializedName("VictimAgent")
    @Expose
    private Integer victimAgent;
    @SerializedName("VictimAttachmentIds")
    @Expose
    private List<Object> victimAttachmentIds = new ArrayList<Object>();
    @SerializedName("VictimStockId")
    @Expose
    private Integer victimStockId;
    @SerializedName("VictimWorldLocation")
    @Expose
    private VictimWorldLocation victimWorldLocation;
    @SerializedName("MedalId")
    @Expose
    private Integer medalId;
    @SerializedName("ShotsFired")
    @Expose
    private Integer shotsFired;
    @SerializedName("ShotsLanded")
    @Expose
    private Integer shotsLanded;
    @SerializedName("TimeWeaponActiveAsPrimary")
    @Expose
    private String timeWeaponActiveAsPrimary;

    /**
     * 
     * @return
     *     The roundIndex
     */
    public Integer getRoundIndex() {
        return roundIndex;
    }

    /**
     * 
     * @param roundIndex
     *     The RoundIndex
     */
    public void setRoundIndex(Integer roundIndex) {
        this.roundIndex = roundIndex;
    }

    /**
     * 
     * @return
     *     The eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * 
     * @param eventName
     *     The EventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * 
     * @return
     *     The timeSinceStart
     */
    public String getTimeSinceStart() {
        return timeSinceStart;
    }

    /**
     * 
     * @param timeSinceStart
     *     The TimeSinceStart
     */
    public void setTimeSinceStart(String timeSinceStart) {
        this.timeSinceStart = timeSinceStart;
    }

    /**
     * 
     * @return
     *     The player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * 
     * @param player
     *     The Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * 
     * @return
     *     The weaponAttachmentIds
     */
    public List<Integer> getWeaponAttachmentIds() {
        return weaponAttachmentIds;
    }

    /**
     * 
     * @param weaponAttachmentIds
     *     The WeaponAttachmentIds
     */
    public void setWeaponAttachmentIds(List<Integer> weaponAttachmentIds) {
        this.weaponAttachmentIds = weaponAttachmentIds;
    }

    /**
     * 
     * @return
     *     The weaponStockId
     */
    public Integer getWeaponStockId() {
        return weaponStockId;
    }

    /**
     * 
     * @param weaponStockId
     *     The WeaponStockId
     */
    public void setWeaponStockId(Integer weaponStockId) {
        this.weaponStockId = weaponStockId;
    }

    /**
     * 
     * @return
     *     The assistants
     */
    public List<Object> getAssistants() {
        return assistants;
    }

    /**
     * 
     * @param assistants
     *     The Assistants
     */
    public void setAssistants(List<Object> assistants) {
        this.assistants = assistants;
    }

    /**
     * 
     * @return
     *     The deathDisposition
     */
    public Integer getDeathDisposition() {
        return deathDisposition;
    }

    /**
     * 
     * @param deathDisposition
     *     The DeathDisposition
     */
    public void setDeathDisposition(Integer deathDisposition) {
        this.deathDisposition = deathDisposition;
    }

    /**
     * 
     * @return
     *     The isAssassination
     */
    public Boolean getIsAssassination() {
        return isAssassination;
    }

    /**
     * 
     * @param isAssassination
     *     The IsAssassination
     */
    public void setIsAssassination(Boolean isAssassination) {
        this.isAssassination = isAssassination;
    }

    /**
     * 
     * @return
     *     The isGroundPound
     */
    public Boolean getIsGroundPound() {
        return isGroundPound;
    }

    /**
     * 
     * @param isGroundPound
     *     The IsGroundPound
     */
    public void setIsGroundPound(Boolean isGroundPound) {
        this.isGroundPound = isGroundPound;
    }

    /**
     * 
     * @return
     *     The isHeadshot
     */
    public Boolean getIsHeadshot() {
        return isHeadshot;
    }

    /**
     * 
     * @param isHeadshot
     *     The IsHeadshot
     */
    public void setIsHeadshot(Boolean isHeadshot) {
        this.isHeadshot = isHeadshot;
    }

    /**
     * 
     * @return
     *     The isMelee
     */
    public Boolean getIsMelee() {
        return isMelee;
    }

    /**
     * 
     * @param isMelee
     *     The IsMelee
     */
    public void setIsMelee(Boolean isMelee) {
        this.isMelee = isMelee;
    }

    /**
     * 
     * @return
     *     The isShoulderBash
     */
    public Boolean getIsShoulderBash() {
        return isShoulderBash;
    }

    /**
     * 
     * @param isShoulderBash
     *     The IsShoulderBash
     */
    public void setIsShoulderBash(Boolean isShoulderBash) {
        this.isShoulderBash = isShoulderBash;
    }

    /**
     * 
     * @return
     *     The isWeapon
     */
    public Boolean getIsWeapon() {
        return isWeapon;
    }

    /**
     * 
     * @param isWeapon
     *     The IsWeapon
     */
    public void setIsWeapon(Boolean isWeapon) {
        this.isWeapon = isWeapon;
    }

    /**
     * 
     * @return
     *     The killer
     */
    public Killer getKiller() {
        return killer;
    }

    /**
     * 
     * @param killer
     *     The Killer
     */
    public void setKiller(Killer killer) {
        this.killer = killer;
    }

    /**
     * 
     * @return
     *     The killerAgent
     */
    public Integer getKillerAgent() {
        return killerAgent;
    }

    /**
     * 
     * @param killerAgent
     *     The KillerAgent
     */
    public void setKillerAgent(Integer killerAgent) {
        this.killerAgent = killerAgent;
    }

    /**
     * 
     * @return
     *     The killerWeaponAttachmentIds
     */
    public List<Integer> getKillerWeaponAttachmentIds() {
        return killerWeaponAttachmentIds;
    }

    /**
     * 
     * @param killerWeaponAttachmentIds
     *     The KillerWeaponAttachmentIds
     */
    public void setKillerWeaponAttachmentIds(List<Integer> killerWeaponAttachmentIds) {
        this.killerWeaponAttachmentIds = killerWeaponAttachmentIds;
    }

    /**
     * 
     * @return
     *     The killerWeaponStockId
     */
    public Integer getKillerWeaponStockId() {
        return killerWeaponStockId;
    }

    /**
     * 
     * @param killerWeaponStockId
     *     The KillerWeaponStockId
     */
    public void setKillerWeaponStockId(Integer killerWeaponStockId) {
        this.killerWeaponStockId = killerWeaponStockId;
    }

    /**
     * 
     * @return
     *     The killerWorldLocation
     */
    public KillerWorldLocation getKillerWorldLocation() {
        return killerWorldLocation;
    }

    /**
     * 
     * @param killerWorldLocation
     *     The KillerWorldLocation
     */
    public void setKillerWorldLocation(KillerWorldLocation killerWorldLocation) {
        this.killerWorldLocation = killerWorldLocation;
    }

    /**
     * 
     * @return
     *     The victim
     */
    public Victim getVictim() {
        return victim;
    }

    /**
     * 
     * @param victim
     *     The Victim
     */
    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    /**
     * 
     * @return
     *     The victimAgent
     */
    public Integer getVictimAgent() {
        return victimAgent;
    }

    /**
     * 
     * @param victimAgent
     *     The VictimAgent
     */
    public void setVictimAgent(Integer victimAgent) {
        this.victimAgent = victimAgent;
    }

    /**
     * 
     * @return
     *     The victimAttachmentIds
     */
    public List<Object> getVictimAttachmentIds() {
        return victimAttachmentIds;
    }

    /**
     * 
     * @param victimAttachmentIds
     *     The VictimAttachmentIds
     */
    public void setVictimAttachmentIds(List<Object> victimAttachmentIds) {
        this.victimAttachmentIds = victimAttachmentIds;
    }

    /**
     * 
     * @return
     *     The victimStockId
     */
    public Integer getVictimStockId() {
        return victimStockId;
    }

    /**
     * 
     * @param victimStockId
     *     The VictimStockId
     */
    public void setVictimStockId(Integer victimStockId) {
        this.victimStockId = victimStockId;
    }

    /**
     * 
     * @return
     *     The victimWorldLocation
     */
    public VictimWorldLocation getVictimWorldLocation() {
        return victimWorldLocation;
    }

    /**
     * 
     * @param victimWorldLocation
     *     The VictimWorldLocation
     */
    public void setVictimWorldLocation(VictimWorldLocation victimWorldLocation) {
        this.victimWorldLocation = victimWorldLocation;
    }

    /**
     * 
     * @return
     *     The medalId
     */
    public Integer getMedalId() {
        return medalId;
    }

    /**
     * 
     * @param medalId
     *     The MedalId
     */
    public void setMedalId(Integer medalId) {
        this.medalId = medalId;
    }

    /**
     * 
     * @return
     *     The shotsFired
     */
    public Integer getShotsFired() {
        return shotsFired;
    }

    /**
     * 
     * @param shotsFired
     *     The ShotsFired
     */
    public void setShotsFired(Integer shotsFired) {
        this.shotsFired = shotsFired;
    }

    /**
     * 
     * @return
     *     The shotsLanded
     */
    public Integer getShotsLanded() {
        return shotsLanded;
    }

    /**
     * 
     * @param shotsLanded
     *     The ShotsLanded
     */
    public void setShotsLanded(Integer shotsLanded) {
        this.shotsLanded = shotsLanded;
    }

    /**
     * 
     * @return
     *     The timeWeaponActiveAsPrimary
     */
    public String getTimeWeaponActiveAsPrimary() {
        return timeWeaponActiveAsPrimary;
    }

    /**
     * 
     * @param timeWeaponActiveAsPrimary
     *     The TimeWeaponActiveAsPrimary
     */
    public void setTimeWeaponActiveAsPrimary(String timeWeaponActiveAsPrimary) {
        this.timeWeaponActiveAsPrimary = timeWeaponActiveAsPrimary;
    }

}
