package com.broxhouse;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;


/**
 * Created by Brock Berrett on 7/20/2016.
 */
public class ApiFactory
{

    private static class ApiKeyInterceptor implements Interceptor {

        private final String apiKey;

        private ApiKeyInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }


        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("Ocp-Apim-Subscription-Key", apiKey)
                    .build();
            return chain.proceed(newRequest);
        }
    }

    private final Retrofit retrofit;
    private final ApiKeyInterceptor interceptor;

    public ApiFactory(String apiKey) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.haloapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interceptor = new ApiKeyInterceptor(apiKey);
//        retrofit.client().setFollowRedirects(false);
//        retrofit.client().interceptors().add(interceptor);
    }

    /**
     * @return Metadata Adapter, for all metadata requests.
     */
    public MetaData getMetadata() {
        return retrofit.create(MetaData.class);
    }

    /**
     * @return Stats Adapter for Reports, Matches, and Service Records.
     */
    public Stats getStats() {
        return retrofit.create(Stats.class);
    }

    /**
     * @return Profile Adapter for Emblem and Profile Images
     */
    public Profile getProfile() {
        return retrofit.create(Profile.class);
    }
}
