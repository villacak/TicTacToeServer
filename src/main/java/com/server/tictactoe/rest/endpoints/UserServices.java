package com.server.tictactoe.rest.endpoints;

import com.server.tictactoe.Constants;
import com.server.tictactoe.business.UserHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by klausvillaca on 1/15/16.
 */

@Path("/user/v1")
public class UserServices {

    /**
     * Endpoint to create a user
     * @param name
     * @return
     */
    @PUT
    @Path("/create/{name}")
    public Response userDetails(@PathParam("name") final String name) {
        Response resp = null;
        try {
            if (name != null && name != Constants.EMPTY) {
                final UserHelper userHelper = new UserHelper();
                resp = userHelper.createUser(name);
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }


    /**
     * Endpoint to retrieve the user
     * @param name
     * @return
     */
    @GET
    @Path("/retrieve/{name}")
    public Response retrieveUser(@PathParam("name") final String name) {
        Response resp = null;
        try {
            if (name != null && name != Constants.EMPTY) {
                final UserHelper userHelper = new UserHelper();
                resp = userHelper.retrieveUser(name);
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }


    @DELETE
    @Path("/delete/{name}")
    public Response deleteUser(@PathParam("name") final String name) {
        Response resp = null;
        try {
            if (name != null && name != Constants.EMPTY) {
                final UserHelper userHelper = new UserHelper();
                resp = userHelper.deleteUser(name);
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return resp;
    }
}
