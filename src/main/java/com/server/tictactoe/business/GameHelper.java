package com.server.tictactoe.business;

import com.server.tictactoe.persistence.daos.GamesDAO;
import com.server.tictactoe.persistence.daos.UserDAO;
import com.server.tictactoe.persistence.entities.GamesEntity;
import com.server.tictactoe.persistence.entities.UserEntity;
import com.server.tictactoe.utils.DateUtils;

import javax.ws.rs.core.Response;

/**
 * Created by klausvillaca on 1/17/16.
 */
public class GameHelper {

    public Response createGame(final String userName, final String selection) throws Exception {
        Response respToReturn = null;
        final UserHelper userHelper = new UserHelper();
        UserEntity userEntity = userHelper.retrieveUserIfAlreadyExist(userName);
        if (userEntity != null) {
            userEntity.setLastDatePlayed(DateUtils.formatDate());
            final UserDAO userDAO = new UserDAO();
            userDAO.update(userEntity);

            final GamesEntity gamesEntity = new GamesEntity();
            gamesEntity.setPlayerXOrO(selection);
            gamesEntity.setUser(userEntity);

            final GamesDAO gamesDAO = new GamesDAO();
            gamesDAO.save(gamesEntity);
        } else {
            respToReturn = Response.status(Response.Status.NO_CONTENT).build();
        }
        return respToReturn;
    }


    private GamesEntity retrieveTheGameIfExist(final int gameId) throws Exception {
        GamesEntity game = null;

        return game;
    }

    private GamesEntity retrieveTheGameId(final String userName) throws Exception {
        GamesEntity game = null;


        return game;
    }
}
