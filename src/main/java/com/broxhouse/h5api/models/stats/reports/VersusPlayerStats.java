

package com.broxhouse.h5api.models.stats.reports;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VersusPlayerStats extends BasePlayerStats {
    public static class OpponentDetails {
        /**
         * The gamertag of the opponent that was killed or killed the player.
         */
        @SerializedName("GamerTag")
        private String gamertag;

        /**
         * The number of times that opponent was killed or killed the player.
         */
        @SerializedName("TotalKills")
        private int totalKills;
    }

    /**
     * The number of times the player killed each opponent. If the player did not kill
     * an opponent, there will be no entry for that opponent.
     */
    @SerializedName("KilledOpponentDetails")
    private List<OpponentDetails> killedOpponentDetails;

    /**
     * The number of times the player was killed by each opponent. If the player was
     * not killed by an opponent, there will be no entry for that opponent.
     */
    @SerializedName("KilledByOpponentDetails")
    private List<OpponentDetails> killedByOpponentDetails;
}
