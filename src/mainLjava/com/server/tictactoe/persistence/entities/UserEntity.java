package com.server.tictactoe.persistence.entities;

import javax.persistence.*;

/**
 * Created by klausvillaca on 1/13/16.
 */
@Entity
@Table(name = "user", schema = "tictactoe_schema")
public class UserEntity {
    private int iduser;
    private String userName;
    private Integer statsWins;
    private Integer statsLoses;
    private Integer statsTied;
    private String lastDatePlayed;

    @Id
    @Column(name = "iduser")
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "stats_wins")
    public Integer getStatsWins() {
        return statsWins;
    }

    public void setStatsWins(Integer statsWins) {
        this.statsWins = statsWins;
    }

    @Basic
    @Column(name = "stats_loses")
    public Integer getStatsLoses() {
        return statsLoses;
    }

    public void setStatsLoses(Integer statsLoses) {
        this.statsLoses = statsLoses;
    }

    @Basic
    @Column(name = "stats_tied")
    public Integer getStatsTied() {
        return statsTied;
    }

    public void setStatsTied(Integer statsTied) {
        this.statsTied = statsTied;
    }

    @Basic
    @Column(name = "last_date_played")
    public String getLastDatePlayed() {
        return lastDatePlayed;
    }

    public void setLastDatePlayed(String lastDatePlayed) {
        this.lastDatePlayed = lastDatePlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (iduser != that.iduser) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (statsWins != null ? !statsWins.equals(that.statsWins) : that.statsWins != null) return false;
        if (statsLoses != null ? !statsLoses.equals(that.statsLoses) : that.statsLoses != null) return false;
        if (statsTied != null ? !statsTied.equals(that.statsTied) : that.statsTied != null) return false;
        if (lastDatePlayed != null ? !lastDatePlayed.equals(that.lastDatePlayed) : that.lastDatePlayed != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iduser;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (statsWins != null ? statsWins.hashCode() : 0);
        result = 31 * result + (statsLoses != null ? statsLoses.hashCode() : 0);
        result = 31 * result + (statsTied != null ? statsTied.hashCode() : 0);
        result = 31 * result + (lastDatePlayed != null ? lastDatePlayed.hashCode() : 0);
        return result;
    }
}
