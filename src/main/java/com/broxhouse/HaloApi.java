package com.broxhouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class HaloApi {
    private static final String TOKEN = "293bb4a86da743bdb983b97efa5bb265";
    private static final String STATS_URL = "https://www.haloapi.com/stats/h5/";
    private static final String META_URL = "https://www.haloapi.com/metadata/h5/metadata/playlists";
    private static final String PLAYER_MATCHES = STATS_URL + "players/%s/matches";
    private static final String CUSTOM_STATS = STATS_URL + "servicerecords/custom?players=%s";
    private static final String ARENA_STATS = STATS_URL + "servicerecords/arena?players=%s";

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

    public static String listWeapons() throws Exception
    {
        return api(META_URL);
    }

    private static String api(String url) throws Exception {
        System.out.println(url);
        URL apiUrl = new URL(url);
        HttpURLConnection urlConn = (HttpURLConnection)apiUrl.openConnection();
        urlConn.setRequestMethod("GET");
        urlConn.setRequestProperty("Ocp-Apim-Subscription-Key", TOKEN);

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String apiOutput = in.readLine();
        //System.out.println(apiOutput);
        urlConn.disconnect();

        return apiOutput;
    }

    public static void main(String[] args) throws Exception {
        try
        {
            JSONObject customMatches = new JSONObject(customMatches("that%20brock%20guy"));
            JSONObject playerMatches = new JSONObject(playerMatches("that%20ax%20guy", "arena,custom", "50", null));
            JSONObject arenaStats = new JSONObject(arenaStats("that%20trev%20guy"));
            JSONArray weapons = new JSONArray(listWeapons());
            System.out.println(customMatches);
            System.out.println(playerMatches);
            System.out.println(arenaStats);
            System.out.println(weapons);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}