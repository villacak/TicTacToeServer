package com.server.tictactoe.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.tictactoe.Constants;
import com.server.tictactoe.business.pojos.CheckGame;
import com.server.tictactoe.persistence.daos.GamesDAO;
import com.server.tictactoe.persistence.daos.PlayDAO;
import com.server.tictactoe.persistence.daos.UserDAO;
import com.server.tictactoe.persistence.entities.GamesEntity;
import com.server.tictactoe.persistence.entities.PlayEntity;
import com.server.tictactoe.persistence.entities.UserEntity;
import com.server.tictactoe.utils.DateUtils;
import org.codehaus.jettison.json.JSONObject;

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
        GamesEntity entitySelected = gamesDAO.findCreatedGames(Constants.O_SELECTION);
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


    public Response userPlay(final String game, final String selection, final String position) throws Exception {
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final List<GamesEntity> gamesEntity = gamesDAO.findByGame(game);
        if (gamesEntity != null && gamesEntity.size() > 0) {
            final PlayEntity playEntity = new PlayEntity();
            final GamesEntity tempGameEntity = gamesEntity.get(gamesEntity.size() - 1);
            playEntity.setGame(tempGameEntity);
            playEntity.setPosition(Integer.valueOf(position));
            playEntity.setPlayid(tempGameEntity.getUser().getIduser());

            final PlayDAO playDAO = new PlayDAO();
            playDAO.save(playEntity);
            respToReturn = Response.ok().build();
        } else {
            respToReturn = Response.status(Response.Status.NO_CONTENT).build();
        }
        return respToReturn;
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
            boolean hasWinner = false;
            final GamesEntity tempEntityForcheck = gamesEntity.get(gamesEntity.size() - 1);
            final CheckGame checkGame = new CheckGame();
            checkGame.setPlayNumber(gamesEntity.size());
            checkGame.setGamesEntity(tempEntityForcheck);

            // We don't need to check when its impossible to have a winner
            if (gamesEntity.size() >= Constants.MINIMUM_VALUE_TO_START_CHECKING) {
                hasWinner = checkIfHasAWinner(tempEntityForcheck);
            }
            checkGame.setWinner(hasWinner);

            final ObjectMapper mapper = new ObjectMapper();
            final String checkGameAsString = mapper.writeValueAsString(checkGame);
            final JSONObject json = new JSONObject(checkGameAsString);

            respToReturn = Response.ok(json, MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            respToReturn = Response.status(Response.Status.NO_CONTENT).build();
        }
        return respToReturn;
    }


    /**
     * Check if has a winner
     *
     * Positions:
     *
     *  0 # 1 # 2
     * ===#===#===
     *  3 # 4 # 5
     * ===#===#===
     *  6 # 7 # 8
     *
     * @return
     */
    private boolean checkIfHasAWinner(final GamesEntity gamesEntity) {
        boolean hasWiner = false;
        final List<PlayEntity> plays = gamesEntity.getPlays();
        int p0 = 0;
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        int p4 = 0;
        int p5 = 0;
        int p6 = 0;
        int p7 = 0;
        int p8 = 0;

        for (PlayEntity play: plays) {
            final int playPos = play.getPosition();
            if (playPos == 0) {
                p0 = 1;
            } else if (playPos == 1) {
                p1 = 2;
            } else if (playPos == 2) {
                p1 = 3;
            } else if (playPos == 3) {
                p1 = 4;
            } else if (playPos == 4) {
                p1 = 5;
            } else if (playPos == 5) {
                p1 = 6;
            } else if (playPos == 6) {
                p1 = 7;
            } else if (playPos == 7) {
                p1 = 8;
            } else if (playPos == 8) {
                p1 = 9;
            }
        }
        if ((p0 > 0 && p1 > 0 && p2 > 0) || (p3 > 0 && p4 > 0 && p5 > 0) || (p6 > 0 && p7 > 0 && p8 > 0) ||
            (p0 > 0 && p3 > 0 && p6 > 0) || (p1 > 0 && p4 > 0 && p7 > 0) || (p2 > 0 && p5 > 0 && p8 > 0) ||
            (p0 > 0 && p4 > 0 && p6 > 8) || (p2 > 0 && p4 > 0 && p6 > 0)) {
            hasWiner = true;
        }

        return hasWiner;
    }


    /**
     * Finalize the game, removing all arlready played and data from that game
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Response finalizeGame(final String name) throws Exception {
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final List<GamesEntity> gamesEntities = gamesDAO.findByName(name);
        if (gamesEntities != null && gamesEntities.size() > 0) {
            boolean hasBeenDelete = false;
            for (GamesEntity entity : gamesEntities) {
                final List<PlayEntity> gamePlays = entity.getPlays();
                final PlayDAO playDAO = new PlayDAO();
                if (gamePlays != null && gamePlays.size() > 0) {
                    for (PlayEntity play : gamePlays) {
                        playDAO.delete(play);
                    }
                }
                gamesDAO.delete(entity);
                hasBeenDelete = true;
            }

            if (hasBeenDelete) {
                respToReturn = Response.ok().build();
            } else {
                respToReturn = Response.status(Response.Status.NO_CONTENT).build();
            }
        } else {
            respToReturn = Response.status(Response.Status.NO_CONTENT).build();
        }
        return respToReturn;
    }
}
