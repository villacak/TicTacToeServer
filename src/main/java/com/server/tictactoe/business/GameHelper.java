package com.server.tictactoe.business;

import com.server.tictactoe.Constants;
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
     * @return
     * @throws Exception
     */
    public Response createGameFirstIfNeeded(final String userName) throws Exception {
        Response respToReturn = null;
        final UserHelper userHelper = new UserHelper();

        GamesEntity gamesEntity = getGameEntityForGameAlreadyCreated(userName);
        if (gamesEntity == null) {
            gamesEntity = createABrandNewGame(userName, Constants.X_SELECTION, 0);
            respToReturn = Response.ok(gamesEntity, MediaType.APPLICATION_JSON).build();
        }
        return respToReturn;
    }


    /**
     * Retrive the very first game with one player only - mean the player is awaiting
     * @return
     * @throws Exception
     */
    private GamesEntity getGameEntityForGameAlreadyCreated(final String userName) throws Exception {
        final GamesDAO gamesDAO = new GamesDAO();
        GamesEntity entitySelected = gamesDAO.findCreatedGames();
        if (entitySelected != null) {
            final int game = entitySelected.getGame();
            entitySelected = createABrandNewGame(userName, Constants.O_SELECTION, entitySelected.getGame());
        }
        return entitySelected;
    }


    /**
     * Create a brand new game
     * @param userName
     * @param selection
     * @return
     * @throws Exception
     */
    private GamesEntity createABrandNewGame(final String userName, final String selection, final int game) throws Exception {
        final UserHelper userHelper = new UserHelper();
        final UserEntity userEntity = userHelper.retrieveUserIfAlreadyExist(userName);
        GamesEntity gamesEntity = null;
        if (userEntity != null) {
            userEntity.setLastDatePlayed(DateUtils.formatDate());
            final UserDAO userDAO = new UserDAO();
            userDAO.update(userEntity);

            gamesEntity = new GamesEntity();
            gamesEntity.setPlayerXOrO(selection);
            gamesEntity.setUser(userEntity);

            final GamesDAO gamesDAO = new GamesDAO();
            final int gameId = gamesDAO.save(gamesEntity);
            gamesEntity.setIdgames(gameId);
            if (game == 0) {
                gamesEntity.setGame(gameId);
            } else {
                gamesEntity.setGame(game);
            }
        }
        return gamesEntity;
    }


    /**
     * Check the game
     * @param game
     * @param selection
     * @param position
     * @return
     * @throws Exception
     */
    public Response checkGame(final String game, final String selection, final String position) throws Exception {
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final List<GamesEntity> gamesEntity = gamesDAO.findByGame(game);
        if (gamesEntity != null && gamesEntity.size() > 0) {
            if (gamesEntity.size() == 1) {
                // return the other selection if the selection is different
                // it's needed to create a new game with the same game
            } else {
                // check if position is equal the last one to return, if it's different
                // return the last game
            }
        }
        return respToReturn;
    }
}
