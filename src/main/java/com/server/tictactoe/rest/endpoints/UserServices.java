package com.server.tictactoe.rest.endpoints;

import com.server.tictactoe.Constants;
import com.server.tictactoe.business.UserHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by klausvillaca on 1/15/16.
 */

@Path("/user/v1/")
public class UserServices {

    @GET
    @Path("/userDetails/{name}")
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
}
