package com.server.tictactoe.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by klausvillaca on 1/15/16.
 */
@Entity
@Table(name = "user", schema = "tictactoe_schema")
@NamedQuery(name="UserEntity.findAll", query="SELECT c FROM UserEntity c")
public class UserEntity {
    private int iduser;
    private String userName;
    private int statsWins;
    private int statsLoses;
    private int statsTied;
    private String lastDatePlayed;
    private List<GamesEntity> games;

    @Id
    @Column(name = "iduser", nullable = false)
    @SequenceGenerator(name="USER_PK_GENERATOR", sequenceName="USERSEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_PK_GENERATOR")
    public int getIduser() {
        return iduser;
    }

    public void setIduser(final int iduser) {
        this.iduser = iduser;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 60)
    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "stats_wins", nullable = true)
    public int getStatsWins() {
        return statsWins;
    }

    public void setStatsWins(final int statsWins) {
        this.statsWins = statsWins;
    }

    @Basic
    @Column(name = "stats_loses", nullable = true)
    public int getStatsLoses() {
        return statsLoses;
    }

    public void setStatsLoses(final int statsLoses) {
        this.statsLoses = statsLoses;
    }

    @Basic
    @Column(name = "stats_tied", nullable = true)
    public int getStatsTied() {
        return statsTied;
    }

    public void setStatsTied(final int statsTied) {
        this.statsTied = statsTied;
    }

    @Basic
    @Column(name = "last_date_played", nullable = true, length = 10)
    public String getLastDatePlayed() {
        return lastDatePlayed;
    }

    public void setLastDatePlayed(final String lastDatePlayed) {
        this.lastDatePlayed = lastDatePlayed;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    public List<GamesEntity> getGames() {
        return games;
    }

    public void setGames(final List<GamesEntity> games) {
        this.games = games;
    }


}
