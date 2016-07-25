

package com.broxhouse.h5api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Profile {

    /**
     * This API returns an HTTP Redirect (302 Found) response to the caller
     * with the URL of an image of the player's emblem. The initial request
     * to this API that returns the HTTP Redirect is throttled and requires
     * a subscription key. However, the image itself (at hostname "image.halocdn.com")
     * is not throttled and does not require a subscription key. Note that if
     * the player later changes their emblem, the image itself is not refreshed
     * and will need to be refreshed via a new request to this API.
     *
     * @param player    Gamertag of player
     * @param size      One of 95, 128, 190, 256, 512 (optional)
     * @return Call object for desired emblem
     */
    @GET("/profile/h5/profiles/{player}/emblem")
    Call<Void> getEmblem(@Path("player") String player, @Query("size") int size);

    /**
     * This API returns an HTTP Redirect (302 Found) response to the caller with
     * the URL of an image of the player's Spartan's appearance. The initial
     * request to this API that returns the HTTP Redirect is throttled and requires
     * a subscription key. However, the image itself (at hostname "image.halocdn.com")
     * is not throttled and does not require a subscription key. Note that if the player
     * later changes their Spartan's appearance, the image itself is not refreshed and
     * will need to be refreshed via a new request to this API.
     *
     * @param player    Gamertag of player
     * @param size      One of 95, 128, 190, 256, 512 (optional)
     * @param crop      full or portrait (optional)
     */
    @GET("/profile/h5/profiles/{player}/spartan")
    Call<Void> getSpartan(@Path("player") String player, @Query("size") Integer size, @Query("crop") String crop);
}
