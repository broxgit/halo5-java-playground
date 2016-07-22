package com.broxhouse.h5api;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;

import java.net.URI;
import java.util.Arrays;

import com.broxhouse.h5api.models.metadata.Medal;
import com.broxhouse.h5api.models.metadata.Weapon;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.*;

public class HaloApi {

    static JSonHelper jSonHelper = new JSonHelper();

    private static final String TOKEN = "293bb4a86da743bdb983b97efa5bb265";
    private static final String STATS_URL = "https://www.haloapi.com/stats/h5/";
    private static final String META_URL = "https://www.haloapi.com/metadata/h5/metadata/";
    private static final String PLAYER_MATCHES = STATS_URL + "players/%s/matches";
    private static final String CUSTOM_STATS = STATS_URL + "servicerecords/custom?players=%s";
    private static final String ARENA_STATS = STATS_URL + "servicerecords/arena?players=%s";
    private static final String META_WEAPONS = META_URL + "weapons";
    private static final String META_MEDALS = META_URL + "medals";

    //https://www.haloapi.com/stats/h5/players/that%20brock%20guy/matches?modes=arena,custom&start=10&count=26
    public static String playerMatches(String gt, String modes, String start, String count) throws Exception {
        String pURL = String.format(PLAYER_MATCHES, gt);
        if (modes != null)
            pURL.concat("modes=" + modes + "&");
        if (start != null)
            pURL.concat("start=" + start + "&");
        if (count != null)
            pURL.concat("count=" + count);

        return api(String.format(PLAYER_MATCHES, gt));
    }

    public static String customMatches(String gt) throws Exception
    {
        return api(String.format(CUSTOM_STATS, gt));
    }

    public static String arenaStats(String gt) throws Exception
    {
        return api(String.format(ARENA_STATS, gt));
    }

    public static String listMedals() throws Exception
    {
        return api(META_MEDALS);
    }

    public static String listWeapons() throws Exception
    {
        return api(META_WEAPONS);
    }

//    private static String api(String url) throws Exception {
//        System.out.println(url);
//        URL apiUrl = new URL(url);
//        HttpURLConnection urlConn = (HttpURLConnection)apiUrl.openConnection();
//        urlConn.setRequestMethod("GET");
//        urlConn.setRequestProperty("Ocp-Apim-Subscription-Key", TOKEN);
//
//        System.out.println(urlConn.getResponseCode());
//
//        InputStream response = urlConn.getInputStream();
//
//            Scanner scanner = new Scanner(response);
//            String responseBody = scanner.useDelimiter("\\A").next();
//            System.out.println(responseBody);
//            return responseBody;
//
//    }

    private static String api(String url) throws Exception
    {
        HttpClient httpclient = HttpClients.createDefault();
        String getResponse;

            URIBuilder builder = new URIBuilder(url);


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", TOKEN);


            // Request body
//            StringEntity reqEntity = new StringEntity("{body}");
//            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                getResponse = EntityUtils.toString(entity);
                return getResponse;
            }
        else
            {
                return null;
            }
    }

    public static void main(String[] args) throws Exception {
        try
        {

//            testJSONWeapons();
//            testJSONMedals();
//            JSONObject customMatches = new JSONObject(customMatches("that%20brock%20guy"));
//            JSONObject playerMatches = new JSONObject(playerMatches("that%20ax%20guy", "arena,custom", "50", null));
            JSONObject arenaStats = new JSONObject(arenaStats("that%20trev%20guy"));
//            JSONArray weapons = new JSONArray(listWeapons());
//            JSONArray medals = new JSONArray(listMedals());
//            System.out.println(customMatches);
//            System.out.println(playerMatches);
            System.out.println(arenaStats);
//            System.out.println(weapons);
//            System.out.println(medals);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void testJSONWeapons() throws Exception
    {
        Gson gson = new Gson();
        Weapon[] data = gson.fromJson(listWeapons(), Weapon[].class);
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < data.length; i++)
        {
            System.out.println(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public static void testJSONMedals() throws Exception
    {
        Gson gson = new Gson();
        Medal[] data = gson.fromJson(listMedals(), Medal[].class);
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < data.length; i++)
        {
            System.out.println(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public static void testPlayerStats() throws Exception
    {

    }

}