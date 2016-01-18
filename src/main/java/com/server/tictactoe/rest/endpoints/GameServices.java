package com.server.tictactoe.rest.endpoints;

import com.server.tictactoe.Constants;

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
    public Response createGame(@PathParam("name") final String name,
                               @QueryParam("selection") final String selection) {
        Response resp = null;
        try {
            if ((name != null && name.equals(Constants.EMPTY)) &&
                (selection != null && selection.equals(Constants.EMPTY))) {

            } else {

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
    public Response gamePlay(@QueryParam("selection") final String selection,
                             @QueryParam("position") final String position) {
        Response resp = null;
        try {
            if ((selection != null && !selection.equals(Constants.EMPTY)) &&
                (position != null && !position.equals(Constants.EMPTY))) {

            } else {

            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }


    /**
     * Check and retrieve if the other player has played
     * @param gameId
     * @param selection
     * @param position
     * @return
     */
    @GET
    @Path("/check")
    @Produces("application/json")
    public Response checkPlay(@QueryParam("gameId") final int gameId,
                              @QueryParam("selection") final String selection,
                              @QueryParam("position") final String position) {
        Response resp = null;
        try {
            if (gameId != 0 &&
               (selection != null && !selection.equals(Constants.EMPTY)) &&
               (position != null && !position.equals(Constants.EMPTY))) {

            } else {

            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }
}
