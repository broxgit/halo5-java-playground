

package com.broxhouse.h5api.models.stats.servicerecords;

import com.broxhouse.h5api.models.stats.common.Csr;
import com.broxhouse.h5api.models.stats.reports.BaseStats;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArenaStat extends BaseStats {

    public static class ArenaPlaylistStats extends BaseStats {
        /**
         * The playlist ID. Playlists are available via the Metadata API.
         */
        @SerializedName("PlaylistId")
        private String playlistId;

        /**
         * The player's measurement matches left. If this field is greater than
         * zero, then the player will not have a Csr yet.
         */
        @SerializedName("MeasurementMatchesLeft")
        private int measurementMatchesLeft;

        /**
         * The highest Competitive Skill Ranking (Csr) achieved by the player. This
         * is included because a player's Csr can drop based on performance.
         */
        @SerializedName("HighestCsr")
        private Csr highestCsr;

        /**
         * The current Competitive Skill Ranking (Csr) of the player.
         */
        @SerializedName("Csr")
        private Csr csr;

        public String getPlaylistId() {
            return playlistId;
        }

        public int getMeasurementMatchesLeft() {
            return measurementMatchesLeft;
        }

        public Csr getHighestCsr() {
            return highestCsr;
        }

        public Csr getCsr() {
            return csr;
        }
    }

    /**
     * List of arena stats by playlist.
     */
    @SerializedName("ArenaPlaylistStats")
    private List<ArenaPlaylistStats> arenaPlaylistStats;

    /**
     * The highest obtained Csr by the player in arena. If the player hasn't
     * finished measurement matches yet for any playlist, this value is null.
     */
    @SerializedName("HighestCsrAttained")
    private Csr highestCsrAttained;

    /**
     * List of arena stats by GameBaseVariant
     */
    @SerializedName("ArenaGameBaseVariantStats")
    private List<GameBaseVariantStats> arenaGameBaseVariantStats;

    /**
     * A list of up to 3 game base variants with the highest win rate by the user.
     * If there is a tie, the one with more completions is higher. If there's still
     * a tie, the GUIDs are sorted and selected.
     */
    @SerializedName("TopGameBaseVariants")
    private List<VariantRecord> topGameBaseVariants;

    /**
     * The ID for the playlist that pertains to the highest obtained Csr field. If
     * the Csr is null, so is this field.
     */
    @SerializedName("HighestCsrPlaylistId")
    private String highestCsrPlaylistId;

    public List<ArenaPlaylistStats> getArenaPlaylistStats() {
        return arenaPlaylistStats;
    }

    public Csr getHighestCsrAttained() {
        return highestCsrAttained;
    }

    public List<GameBaseVariantStats> getArenaGameBaseVariantStats() {
        return arenaGameBaseVariantStats;
    }

    public List<VariantRecord> getTopGameBaseVariants() {
        return topGameBaseVariants;
    }

    public String getHighestCsrPlaylistId() {
        return highestCsrPlaylistId;
    }
}
