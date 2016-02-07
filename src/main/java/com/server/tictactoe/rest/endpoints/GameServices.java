package com.server.tictactoe.rest.endpoints;

import com.server.tictactoe.Constants;
import com.server.tictactoe.business.GameHelper;

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
    @Path("/create/{userName}")
    @Produces("application/json")
    public Response createGame(@PathParam("name") final String name) {
        Response resp = null;
        try {
            if (name != null && name.equals(Constants.EMPTY)) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.createGameFirstIfNeeded(name);
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }


    /**
     * Check and retrieve if the other player has played
     * @param game
     * @param selection
     * @param position
     * @return
     */
    @GET
    @Path("/check")
    @Produces("application/json")
    public Response checkPlay(@QueryParam("game") final String game,
                              @QueryParam("selection") final String selection,
                              @QueryParam("position") final String position) {
        Response resp = null;
        try {
            if ((game != null && !game.equals(Constants.EMPTY)) &&
               (selection != null && !selection.equals(Constants.EMPTY)) &&
               (position != null && !position.equals(Constants.EMPTY))) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.checkGame(game, selection, position);
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }


    @GET
    @Path("/finalize")
    @Produces("application/json")
    public Response finalize(@QueryParam("game") final String game,
                             @QueryParam("selection") final String selection) {
        Response resp = null;
        try {
            if ((game != null && !game.equals(Constants.EMPTY)) &&
                    (selection != null && !selection.equals(Constants.EMPTY))) {
                final GameHelper gameHelper = new GameHelper();
                resp = gameHelper.finalizeGame(game, selection);
            }
        }  catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }
}
