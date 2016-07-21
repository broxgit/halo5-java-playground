package com.broxhouse.h5api;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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
//
//   private static class ApiKeyInterceptor{
////
//
////
//        private ApiKeyInterceptor() {
//            this.apiKey = "293bb4a86da743bdb983b97efa5bb265";
//        }
////
////
//
//    }



    private final Retrofit retrofit;


    public ApiFactory() {
        final String apiKey = "293bb4a86da743bdb983b97efa5bb265";



        Interceptor interceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("Ocp-Apim-Subscription-Key", apiKey).build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.haloapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    /**
     * @return Metadata Adapter, for all metadata requests.
     */
    public Metadata getMetadata() {
        return retrofit.create(Metadata.class);
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
