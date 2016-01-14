package com.server.tictactoe.persistence.entities;

import javax.persistence.*;

/**
 * Created by klausvillaca on 1/13/16.
 */
@Entity
@Table(name = "play", schema = "tictactoe_schema")
public class PlayEntity {
    private int playid;
    private Integer gameId;
    private Integer position;

    @Id
    @Column(name = "playid")
    public int getPlayid() {
        return playid;
    }

    public void setPlayid(int playid) {
        this.playid = playid;
    }

    @Basic
    @Column(name = "game_id")
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @Basic
    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayEntity that = (PlayEntity) o;

        if (playid != that.playid) return false;
        if (gameId != null ? !gameId.equals(that.gameId) : that.gameId != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = playid;
        result = 31 * result + (gameId != null ? gameId.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
