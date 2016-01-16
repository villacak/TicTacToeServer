package com.server.tictactoe.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by klausvillaca on 1/15/16.
 */
@Entity
@Table(name = "games", schema = "tictactoe_schema", catalog = "")
public class GamesEntity {
    private UserEntity user;
    private List<PlayEntity> plays;
    private int idgames;
    private String playerXOrO;
    private String wonXOrY;

    @JsonBackReference
    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "game")
    public List<PlayEntity> getPlays() {
        return plays;
    }

    public void setPlays(List<PlayEntity> plays) {
        this.plays = plays;
    }

    @Id
    @Column(name = "idgames", nullable = false)
    @SequenceGenerator(name="GAMES_PK_GENERATOR", sequenceName="GAMESSEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAMES_PK_GENERATOR")
    public int getIdgames() {
        return idgames;
    }

    public void setIdgames(int idgames) {
        this.idgames = idgames;
    }

    @Basic
    @Column(name = "player_x_or_o", nullable = true, length = 1)
    public String getPlayerXOrO() {
        return playerXOrO;
    }

    public void setPlayerXOrO(String playerXOrO) {
        this.playerXOrO = playerXOrO;
    }

    @Basic
    @Column(name = "won_x_or_y", nullable = true, length = 1)
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
        if (playerXOrO != null ? !playerXOrO.equals(that.playerXOrO) : that.playerXOrO != null) return false;
        if (wonXOrY != null ? !wonXOrY.equals(that.wonXOrY) : that.wonXOrY != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idgames;
        result = 31 * result + (playerXOrO != null ? playerXOrO.hashCode() : 0);
        result = 31 * result + (wonXOrY != null ? wonXOrY.hashCode() : 0);
        return result;
    }
}
