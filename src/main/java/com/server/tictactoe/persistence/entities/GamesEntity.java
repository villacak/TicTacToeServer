package com.server.tictactoe.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by klausvillaca on 1/15/16.
 */
@Entity
@Table(name = "games", schema = "tictactoe_schema")
@NamedQueries({
        @NamedQuery(name="GamesEntity.findAll", query="SELECT c FROM GamesEntity c"),
        @NamedQuery(name="GamesEntity.getAllFromSelection", query="SELECT c FROM GamesEntity c WHERE c.playerXOrO = :playerXOrO AND c.playersNumber = 0"),
        @NamedQuery(name="GamesEntity.findCreatedGames", query="SELECT c FROM GamesEntity c WHERE c.playersNumber = :playersNumber")
})
public class GamesEntity {
    private UserEntity user;
    private List<PlayEntity> plays;
    private int idgames;
    private int game;
    private String playerXOrO;
    private String wonXOrY;
    private int playersNumber;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="player", referencedColumnName = "iduser")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "game")
    public List<PlayEntity> getPlays() {
        return plays;
    }

    public void setPlays(final List<PlayEntity> plays) {
        this.plays = plays;
    }

    @Id
    @Column(name = "idgames", nullable = false)
    @SequenceGenerator(name="GAMES_PK_GENERATOR", sequenceName="GAMESSEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAMES_PK_GENERATOR")
    public int getIdgames() {
        return idgames;
    }

    public void setIdgames(final int idgames) {
        this.idgames = idgames;
    }

    @Basic
    @Column(name = "player_x_or_o", nullable = true, length = 1)
    public String getPlayerXOrO() {
        return playerXOrO;
    }

    public void setPlayerXOrO(final String playerXOrO) {
        this.playerXOrO = playerXOrO;
    }

    @Basic
    @Column(name = "won_x_or_y", nullable = true, length = 1)
    public String getWonXOrY() {
        return wonXOrY;
    }

    public void setWonXOrY(final String wonXOrY) {
        this.wonXOrY = wonXOrY;
    }

    @Basic
    @Column(name = "game", nullable = true)
    public int getGame() {
        return game;
    }

    public void setGame(final int game) {
        this.game = game;
    }


    @Basic
    @Column(name = "players_number", nullable = true)
    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(final int playersNumber) {
        this.playersNumber = playersNumber;
    }
}
