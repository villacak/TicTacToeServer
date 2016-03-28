package com.server.tictactoe.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by klausvillaca on 1/15/16.
 */
@Entity
@Table(name = "play", schema = "tictactoe_schema")
@NamedQuery(name="PlayEntity.findAll", query="SELECT c FROM PlayEntity c")
public class PlayEntity {
    private GamesEntity game;
    private int playid;
    private int position;
    private int userId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="game", referencedColumnName = "game")
    public GamesEntity getGame() {
        return game;
    }

    public void setGame(final GamesEntity game) {
        this.game = game;
    }


    @Id
    @Column(name = "playid", nullable = false)
    @SequenceGenerator(name="PLAY_PK_GENERATOR", sequenceName="PLAYSEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAY_PK_GENERATOR")
    public int getPlayid() {
        return playid;
    }

    public void setPlayid(final int playid) {
        this.playid = playid;
    }

    @Basic
    @Column(name = "position", nullable = true)
    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }


    @Basic
    @Column(name = "userId", nullable = true)
    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

//    @Override
//    public String toString() {
//        final StringBuilder playToString = new StringBuilder("PlayEntity{")
//                .append("game=").append(game)
//                .append(", playid=").append(playid)
//                .append(", position=").append(position)
//                .append(", userId=").append(userId).append('}');
//        return playToString.toString();
//    }
}
