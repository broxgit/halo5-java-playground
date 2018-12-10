package com.broxhouse.h5api.models.stats.common;

/**
 * Created by Brock Berrett on 10/4/2016.
 */
public class PlayerCount {

    private String name;
    private int gameCount = 0;
    private int killCount = 0;

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public PlayerCount(String name, int gameCount){
        this.name = name;
        this.gameCount = gameCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
}
