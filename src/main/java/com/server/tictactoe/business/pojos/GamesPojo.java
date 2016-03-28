package com.server.tictactoe.business.pojos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.server.tictactoe.persistence.entities.PlayEntity;
import com.server.tictactoe.persistence.entities.UserEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by klausvillaca on 1/15/16.
 */
public class GamesPojo {
    private UserEntity user;
    private List<PlayEntity> plays;
    private int idgames;
    private int game;
    private String playerXOrO;
    private String wonXOrY;
    private int playersNumber;

    public UserEntity getUser() {
        return user;
    }
    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public List<PlayEntity> getPlays() {
        return plays;
    }
    public void setPlays(final List<PlayEntity> plays) {
        this.plays = plays;
    }

    public int getIdgames() {
        return idgames;
    }
    public void setIdgames(final int idgames) {
        this.idgames = idgames;
    }

    public String getPlayerXOrO() {
        return playerXOrO;
    }
    public void setPlayerXOrO(final String playerXOrO) {
        this.playerXOrO = playerXOrO;
    }

    public String getWonXOrY() {
        return wonXOrY;
    }
    public void setWonXOrY(final String wonXOrY) {
        this.wonXOrY = wonXOrY;
    }

    public int getGame() {
        return game;
    }
    public void setGame(final int game) {
        this.game = game;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }
    public void setPlayersNumber(final int playersNumber) {
        this.playersNumber = playersNumber;
    }
}
