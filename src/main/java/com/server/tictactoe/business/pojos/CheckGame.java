package com.server.tictactoe.business.pojos;

import com.server.tictactoe.persistence.entities.GamesEntity;

import java.io.Serializable;

/**
 * Created by klausvillaca on 1/25/16.
 */
public class CheckGame implements Serializable {

    private int playNumber;
    private GamesEntity gamesEntity;
    private boolean isWinner;


    public int getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }

    public GamesEntity getGamesEntity() {
        return gamesEntity;
    }

    public void setGamesEntity(GamesEntity gamesEntity) {
        this.gamesEntity = gamesEntity;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
}
