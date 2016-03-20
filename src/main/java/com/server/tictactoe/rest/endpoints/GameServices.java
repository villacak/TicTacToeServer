package com.server.tictactoe.rest.endpoints;

import com.server.tictactoe.Constants;
import com.server.tictactoe.business.GameHelper;
import com.server.tictactoe.utils.CreateErrorResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by klausvillaca on 1/15/16.
 */

@Path("/game/v1/")
public class GameServices {

    /**
     * Create a new game
     * @param name
     * @return
     */
    @PUT
    @Path("/create/{name}")
    @Produces("application/json")
    public Response createGame(@PathParam("name") final String name) {
        Response resp = null;
        try {
            if (name != null && !name.equals(Constants.EMPTY)) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.createGameFirstIfNeeded(name);
            } else {
                resp = CreateErrorResponse.createErrorResponse(Response.Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            resp = CreateErrorResponse.createErrorResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }


    /**
     * Create a new play
     * @param selection
     * @param position
     * @return
     */
    @PUT
    @Path("/play")
    @Produces("application/json")
    public Response gamePlay(@QueryParam("game") final String game,
                             @QueryParam("selection") final String selection,
                             @QueryParam("position") final String position) {
        Response resp = null;
        try {
            if ((selection != null && !selection.equals(Constants.EMPTY)) &&
                (position != null && !position.equals(Constants.EMPTY))) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.userPlay(game, selection, position);
            } else {
                resp = CreateErrorResponse.createErrorResponse(Response.Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            resp = CreateErrorResponse.createErrorResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }


    /**
     * Check and retrieve if the other player has played
     * @param game
     * @return
     */
    @GET
    @Path("/check")
    @Produces("application/json")
    public Response checkPlay(@QueryParam("game") final String game) {
        Response resp = null;
        try {
            if (game != null && !game.equals(Constants.EMPTY)) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.checkGame(game);
            } else {
                resp = CreateErrorResponse.createErrorResponse(Response.Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            resp = CreateErrorResponse.createErrorResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }


    @GET
    @Path("/finalize")
    @Produces("application/json")
    public Response finalize(@QueryParam("name") final String name) {
        Response resp = null;
        try {
            if (name != null && !name.equals(Constants.EMPTY)) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.finalizeGame(name);
            } else {
                resp = CreateErrorResponse.createErrorResponse(Response.Status.BAD_REQUEST);
            }
        }  catch (Exception e) {
            resp = CreateErrorResponse.createErrorResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @PUT
    @Path("/inGame/{name}/{isInGame}")
    @Produces("application/json")
    public Response inGame(@PathParam("name") String name, @PathParam("isInGame") final boolean isInGame) {
        Response resp = null;
        try {
            if (name != null && !name.equals(Constants.EMPTY)) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.isPLayerInGame(name, isInGame);
            } else {
                resp = CreateErrorResponse.createErrorResponse(Response.Status.BAD_REQUEST);
            }
        }  catch (Exception e) {
            resp = CreateErrorResponse.createErrorResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }
}
