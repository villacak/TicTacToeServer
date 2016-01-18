package com.server.tictactoe.business;

import com.server.tictactoe.persistence.daos.GamesDAO;
import com.server.tictactoe.persistence.daos.UserDAO;
import com.server.tictactoe.persistence.entities.GamesEntity;
import com.server.tictactoe.persistence.entities.UserEntity;
import com.server.tictactoe.utils.DateUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by klausvillaca on 1/17/16.
 */
public class GameHelper {

    /**
     * Create a new game
     * @param userName
     * @param selection
     * @return
     * @throws Exception
     */
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
            gamesEntity.setIdgames(gamesDAO.save(gamesEntity));

            respToReturn = Response.ok(gamesEntity, MediaType.APPLICATION_JSON).build();
        } else {
            respToReturn = Response.status(Response.Status.NO_CONTENT).build();
        }
        return respToReturn;
    }


    public Response checkGame(final Integer gameId, final String selection, final String position) throws Exception {
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
//        final List<GamesEntity> gamesEntity = gamesDAO.findById(gameId);

        return respToReturn;
    }


    private GamesEntity retrieveTheGameIfExist(final int gameId) throws Exception {
        final GamesDAO gamesDAO = new GamesDAO();
        final GamesEntity gamesEntity = gamesDAO.findById(gameId);
        return gamesEntity;
    }

}
