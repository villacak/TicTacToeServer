package com.server.tictactoe.persistence.entities;

import javax.persistence.*;

/**
 * Created by klausvillaca on 1/13/16.
 */
@Entity
@Table(name = "games", schema = "tictactoe_schema")
public class GamesEntity {
    private int idgames;
    private Integer playerX;
    private Integer playerY;
    private String wonXOrY;

    @Id
    @Column(name = "idgames")
    public int getIdgames() {
        return idgames;
    }

    public void setIdgames(int idgames) {
        this.idgames = idgames;
    }

    @Basic
    @Column(name = "player_x")
    public Integer getPlayerX() {
        return playerX;
    }

    public void setPlayerX(Integer playerX) {
        this.playerX = playerX;
    }

    @Basic
    @Column(name = "player_y")
    public Integer getPlayerY() {
        return playerY;
    }

    public void setPlayerY(Integer playerY) {
        this.playerY = playerY;
    }

    @Basic
    @Column(name = "won_x_or_y")
    public String getWonXOrY() {
        return wonXOrY;
    }

    public void setWonXOrY(String wonXOrY) {
        this.wonXOrY = wonXOrY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesEntity that = (GamesEntity) o;

        if (idgames != that.idgames) return false;
        if (playerX != null ? !playerX.equals(that.playerX) : that.playerX != null) return false;
        if (playerY != null ? !playerY.equals(that.playerY) : that.playerY != null) return false;
        if (wonXOrY != null ? !wonXOrY.equals(that.wonXOrY) : that.wonXOrY != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idgames;
        result = 31 * result + (playerX != null ? playerX.hashCode() : 0);
        result = 31 * result + (playerY != null ? playerY.hashCode() : 0);
        result = 31 * result + (wonXOrY != null ? wonXOrY.hashCode() : 0);
        return result;
    }
}
