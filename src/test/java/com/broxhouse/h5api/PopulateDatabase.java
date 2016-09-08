package com.broxhouse.h5api;

import com.broxhouse.h5api.models.stats.common.Player;
import com.broxhouse.h5api.models.stats.matches.Match;

import java.util.ArrayList;
import java.util.List;

import static com.broxhouse.h5api.gameType.ARENA;

/**
 * Created by Brock Berrett on 9/8/2016.
 */
public class PopulateDatabase {

    HaloApi hApi = new HaloApi();

    public Player[] cachePlayers() throws Exception{
        Match[] matches = hApi.getMatches(ARENA);
        List<Player> playersList = new ArrayList<>();
        for (int i = 0; i < matches.length; i++){
            for (int k = 0; k < matches[i].getPlayers().size(); i++){
                playersList.add(matches[i].getPlayers().get(k).getPlayer());
                System.out.println(matches[i].getPlayers().size() + " " + matches[i].getPlayers().get(k).getPlayer().getGamertag());
            }
        }
        Player[] players = new Player[playersList.size()];
        for (int i = 0; i < playersList.size(); i++){
            players[i] = playersList.get(i);
        }
        return players;
    }
}
