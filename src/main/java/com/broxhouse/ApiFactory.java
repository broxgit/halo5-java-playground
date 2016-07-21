package com.broxhouse;

import retrofit2.Retrofit;
import javax.ws.rs.core.Response;



/**
 * Created by Brock Berrett on 7/20/2016.
 */
public class ApiFactory

private static class ApiKeyInterceptor implements Interceptor {

    private final String apiKey;

    private ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("Ocp-Apim-Subscription-Key", apiKey)
                .build();
        System.out.println(newRequest.httpUrl());
        return chain.proceed(newRequest);
    }
{
    public ApiFactory(String apiKey) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.haloapi.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        interceptor = new ApiKeyInterceptor(apiKey);
        retrofit.client().setFollowRedirects(false);
        retrofit.client().interceptors().add(interceptor);
    }
}
