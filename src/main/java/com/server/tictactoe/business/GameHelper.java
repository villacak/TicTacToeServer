package com.server.tictactoe.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.tictactoe.Constants;
import com.server.tictactoe.business.pojos.CheckGame;
import com.server.tictactoe.business.pojos.GamesPojo;
import com.server.tictactoe.persistence.daos.GamesDAO;
import com.server.tictactoe.persistence.daos.PlayDAO;
import com.server.tictactoe.persistence.daos.UserDAO;
import com.server.tictactoe.persistence.entities.GamesEntity;
import com.server.tictactoe.persistence.entities.PlayEntity;
import com.server.tictactoe.persistence.entities.PlayPlainEntity;
import com.server.tictactoe.persistence.entities.UserEntity;
import com.server.tictactoe.utils.CreateErrorResponse;
import com.server.tictactoe.utils.DateUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        final String gamesEntityStr = "gamesEntity";
        final String playEntityStr = "playEntity";
        final String userEntityStr = "userEntity";

        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final PlayDAO playDAO = new PlayDAO();
        final List<GamesEntity> gamesEntities = gamesDAO.findByGame(game);
        final List<PlayPlainEntity> plays = playDAO.findByGame(game);
        if ((gamesEntities != null && gamesEntities.size() > 0) &&
                (plays != null && plays.size() > 0)) {
            boolean hasWinner = false;

            GamesEntity lastPlayed = null;
            final PlayPlainEntity lastPlay = plays.get(plays.size() - 1);
            final int userId = lastPlay.getUserId();
            for(GamesEntity tempGameEntity: gamesEntities) {
                if (userId == tempGameEntity.getUser().getIduser()) {
                    lastPlayed = tempGameEntity;
                    break;
                }
            }

            // Check what GamesEntity user did the last play
            final GamesPojo lastPlayedPojo = getGamePojo(lastPlayed, plays);
            final CheckGame checkGame = new CheckGame();
            checkGame.setPlayNumber(lastPlayed.getPlayersNumber());
            checkGame.setGamesEntity(lastPlayedPojo);

            // We don't need to check when its impossible to have a winner
            if (plays.size() >= Constants.MINIMUM_VALUE_TO_START_CHECKING) {
                hasWinner = checkIfHasAWinner(lastPlayedPojo);
            }
            checkGame.setWinner(hasWinner);

            final ObjectMapper mapper = new ObjectMapper();
            final String checkGameAsString = mapper.writeValueAsString(checkGame);
            lastPlayed = null;

            respToReturn = Response.ok(checkGameAsString, MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.NO_CONTENT);
        }
        return respToReturn;
    }


    /**
     * Copy data from the entity to the pojo as the entity when generating json
     * exclude the instance variables that are part of relationship
     *
     * @param gamesEntityTemp
     * @return
     */
    private GamesPojo getGamePojo(final GamesEntity gamesEntityTemp, final List<PlayPlainEntity> playsPlain) {
        final GamesPojo pojo  = new GamesPojo();
        pojo.setIdgames(gamesEntityTemp.getIdgames());
        pojo.setGame(gamesEntityTemp.getGame());
        pojo.setPlayersNumber(gamesEntityTemp.getPlayersNumber());
        pojo.setPlayerXOrO(gamesEntityTemp.getPlayerXOrO());
        pojo.setPlays(playsPlain);
        pojo.setUser(gamesEntityTemp.getUser());
        pojo.setWonXOrY(gamesEntityTemp.getWonXOrY());
        return pojo;
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
    private boolean checkIfHasAWinner(final GamesPojo gamesPojo) {
        boolean hasWiner = false;
        final List<PlayPlainEntity> plays = getPlaysFromLastPlayerPLay(gamesPojo.getPlays(), gamesPojo.getUser().getIduser());

        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        int p4 = 0;
        int p5 = 0;
        int p6 = 0;
        int p7 = 0;
        int p8 = 0;
        int p9 = 0;

        for (PlayPlainEntity play: plays) {
            final int playPos = play.getPosition();
            if (playPos == 1) {
                p1 = 1;
            } else if (playPos == 2) {
                p2 = 2;
            } else if (playPos == 3) {
                p3 = 3;
            } else if (playPos == 4) {
                p4 = 4;
            } else if (playPos == 5) {
                p5 = 5;
            } else if (playPos == 6) {
                p6 = 6;
            } else if (playPos == 7) {
                p7 = 7;
            } else if (playPos == 8) {
                p8 = 8;
            } else if (playPos == 9) {
                p9 = 9;
            }
        }
        if ((p1 > 0 && p2 > 0 && p3 > 0) || (p4 > 0 && p5 > 0 && p6 > 0) || (p7 > 0 && p8 > 0 && p9 > 0) ||
            (p1 > 0 && p4 > 0 && p7 > 0) || (p2 > 0 && p5 > 0 && p8 > 0) || (p3 > 0 && p6 > 0 && p9 > 0) ||
            (p1 > 0 && p5 > 0 && p9 > 0) || (p3 > 0 && p5 > 0 && p6 > 7)) {
            hasWiner = true;
        }

        return hasWiner;
    }


    /**
     * Filter plays from the player
     * @param allPlays
     * @param userId
     * @return
     */
    private List<PlayPlainEntity> getPlaysFromLastPlayerPLay(final List<PlayPlainEntity> allPlays, int userId) {
        final List<PlayPlainEntity> playsFromUser = new ArrayList<>();
        for (PlayPlainEntity tempPlay: allPlays) {
            final int tempUserId = tempPlay.getUserId();
            if (tempUserId == userId) {
                playsFromUser.add(tempPlay);
            }
        }
        return playsFromUser;
    }


    /**
     * Finalize the game, removing all arlready played and data from that game
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Response finalizeGame(final String name) throws Exception {
        final String nothingToDelete = "{\"success\": {\"nothingToDelete\":\"true\"}}";
        Response respToReturn = null;
        final GamesDAO gamesDAO = new GamesDAO();
        final PlayDAO playDAO = new PlayDAO();
        final UserDAO userDAO = new UserDAO();

        final List<UserEntity> userList = userDAO.findByName(name);
        UserEntity user = null;
        if (userList != null && userList.size() > 0)
            user = userList.get(0);
        final List<PlayPlainEntity> playList = playDAO.findByUser(user.getIduser());
        final List<GamesEntity> gamesEntities = gamesDAO.findByName(name);

        try {
            if (playList == null && gamesEntities == null) {
                respToReturn = Response.ok(nothingToDelete, MediaType.APPLICATION_JSON_TYPE).build();
            } else {
                if (playList != null && playList.size() > 0) {
                    for (PlayPlainEntity play : playList) {
                        playDAO.deletePlain(play);
                    }
                }

                if (gamesEntities != null && gamesEntities.size() > 0) {
                    for (GamesEntity entity : gamesEntities) {
                        gamesDAO.delete(entity);
                    }
                }
                respToReturn = Response.ok().build();
            }
        } catch (Exception e) {
            respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.NO_CONTENT);
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
