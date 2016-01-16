package com.server.tictactoe.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by klausvillaca on 1/15/16.
 */
@Entity
@Table(name = "play", schema = "tictactoe_schema")
public class PlayEntity {
    private GamesEntity game;
    private int playid;
    private Integer position;

    @JsonBackReference
    @ManyToOne
    public GamesEntity getGame() {
        return game;
    }

    public void setGame(GamesEntity game) {
        this.game = game;
    }

    @Id
    @Column(name = "playid", nullable = false)
    @SequenceGenerator(name="PLAY_PK_GENERATOR", sequenceName="PLAYSEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAY_PK_GENERATOR")
    public int getPlayid() {
        return playid;
    }

    public void setPlayid(int playid) {
        this.playid = playid;
    }

    @Basic
    @Column(name = "position", nullable = true)
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
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = playid;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
