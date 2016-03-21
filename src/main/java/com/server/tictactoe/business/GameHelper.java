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
import com.server.tictactoe.utils.CreateErrorResponse;
import com.server.tictactoe.utils.DateUtils;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by klausvillaca on 1/17/16.
 */
public class GameHelper {

    private final int ZERO_PLAYERS = 0;
    private final int FIRST_PLAYER = 1;
    private final int SECOND_PLAYER = 2;
    private final int PLAYER_NOT_ONLINE = 99;


    /**
     * Create a new game
     * @param userName
     * @return
     * @throws Exception
     */
    public Response createGameFirstIfNeeded(final String userName) throws Exception {
        Response respToReturn = null;
        final UserHelper userHelper = new UserHelper();
        final GamesDAO gamesDAO = new GamesDAO();
        final GamesEntity entitySelectedO = gamesDAO.findCreatedGames(Constants.O_SELECTION);
        final GamesEntity entitySelectedX = gamesDAO.findCreatedGames(Constants.X_SELECTION);

        GamesEntity gamesEntity = null;
        if (entitySelectedO == null && entitySelectedX != null) {
            if (!entitySelectedX.getUser().getUserName().equals(userName)) {
                final int game = entitySelectedX.getGame();
                gamesEntity = createABrandNewGame(userName, Constants.O_SELECTION, game, SECOND_PLAYER);
                entitySelectedX.setPlayersNumber(FIRST_PLAYER);
                final GamesDAO dao = new GamesDAO();
                dao.update(entitySelectedX);
            } else {
                respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.CONFLICT);
            }
        } else if (entitySelectedO != null && entitySelectedX == null) {
            if (!entitySelectedO.getUser().getUserName().equals(userName)) {
                final int game = entitySelectedO.getGame();
                gamesEntity = createABrandNewGame(userName, Constants.X_SELECTION, game, FIRST_PLAYER);
                entitySelectedO.setPlayersNumber(SECOND_PLAYER);
                final GamesDAO dao = new GamesDAO();
                dao.update(entitySelectedO);
            } else {
                respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.CONFLICT);
            }
        } else if (entitySelectedO != null && entitySelectedX != null) {
            if (!entitySelectedO.getUser().getUserName().equals(userName)) {
                final int game = entitySelectedX.getGame();
                gamesEntity = createABrandNewGame(userName, Constants.X_SELECTION, game, FIRST_PLAYER);
                entitySelectedO.setPlayersNumber(SECOND_PLAYER);
                final GamesDAO dao = new GamesDAO();
                dao.update(entitySelectedO);
            } else {
                respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.CONFLICT);
            }
        }

        if (respToReturn == null) {
            if (gamesEntity == null) {
                gamesEntity = createABrandNewGame(userName, Constants.X_SELECTION, 0, ZERO_PLAYERS);
            }

            respToReturn = Response.ok(gamesEntity, MediaType.APPLICATION_JSON).build();
        }
        return respToReturn;
    }


    /**
     * Create a brand new game
     * @param userName
     * @param selection
     * @return
     * @throws Exception
     */
    private GamesEntity createABrandNewGame(final String userName, final String selection, final int game, final int playersNumber) throws Exception {
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
            gamesEntity.setPlayersNumber(playersNumber);

            final GamesDAO gamesDAO = new GamesDAO();
            final int gameId = gamesDAO.save(gamesEntity);
            gamesEntity.setIdgames(gameId);
            if (game == 0) {
                gamesEntity.setGame(gameId);
            } else {
                gamesEntity.setGame(game);
            }
            gamesDAO.update(gamesEntity);
        }
        return gamesEntity;
    }


    /**
     * User play
     * @param game
     * @param selection
     * @param position
     * @return
     * @throws Exception
     */
    public Response userPlay(final String game, final String selection, final String position) throws Exception {
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final List<GamesEntity> gamesEntity = gamesDAO.findByGame(game);
        if (gamesEntity != null && gamesEntity.size() > 0) {
            final PlayEntity playEntity = new PlayEntity();
            for (GamesEntity tempGameEntity: gamesEntity) {
                if (tempGameEntity.getPlayerXOrO().equals(selection)) {
                    playEntity.setGame(tempGameEntity);
                    playEntity.setPosition(Integer.valueOf(position));
                    playEntity.setPlayid(tempGameEntity.getUser().getIduser());
                    playEntity.setUserId(tempGameEntity.getUser().getIduser());
                    break;
                }
            }

            final PlayDAO playDAO = new PlayDAO();
            playDAO.save(playEntity);
            respToReturn = Response.ok().build();
        } else {
            respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.NO_CONTENT);
        }
        return respToReturn;
    }


    /**
     * Check the game
     * @param game
     * @return
     * @throws Exception
     */
    public Response checkGame(final String game) throws Exception {
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final PlayDAO playDAO = new PlayDAO();
        final List<GamesEntity> gamesEntities = gamesDAO.findByGame(game);
        final List<PlayEntity> playEntities = playDAO.findByGame(game);
        if (gamesEntities != null && gamesEntities.size() > 0) {
            boolean hasWinner = false;
            final GamesEntity tempEntityForcheck = gamesEntities.get(gamesEntities.size() - 1);
            final CheckGame checkGame = new CheckGame();
            checkGame.setPlayNumber(gamesEntities.size());
            checkGame.setGamesEntity(tempEntityForcheck);

            // We don't need to check when its impossible to have a winner
            if (gamesEntities.size() >= Constants.MINIMUM_VALUE_TO_START_CHECKING) {
                hasWinner = checkIfHasAWinner(tempEntityForcheck);
            }
            checkGame.setWinner(hasWinner);

            final ObjectMapper mapper = new ObjectMapper();
            final String checkGameAsString = mapper.writeValueAsString(checkGame);
            final JSONObject json = new JSONObject(checkGameAsString);

            respToReturn = Response.ok(json.toString(), MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.NO_CONTENT);
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
                respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.NO_CONTENT);
            }
        } else {
            respToReturn = Response.ok("{\"success\": {\"nothingToDelete\":\"true\"}}", MediaType.APPLICATION_JSON_TYPE).build();
        }
        return respToReturn;
    }


    /**
     * Method that set user as in game, that may be playing or on hold for someone also go to play
     * as also set the user not in play when the user leave the game
     *
     * @param name
     * @param isInGame
     * @return
     * @throws Exception
     */
    public Response isPLayerInGame(final String name, final boolean isInGame) throws Exception {
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final List<GamesEntity> gamesEntities = gamesDAO.findByName(name);
        if (gamesEntities != null && gamesEntities.size() > 0) {
            for (GamesEntity gameTemp : gamesEntities) {
                if (!isInGame) {
                    gameTemp.setPlayersNumber(PLAYER_NOT_ONLINE);
                } else {
                    gameTemp.setPlayersNumber(ZERO_PLAYERS);
                }
                gamesDAO.update(gameTemp);
            }
        }
        return respToReturn;
    }
}
