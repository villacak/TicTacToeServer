package com.server.tictactoe.rest.endpoints;

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
    public Response userDetails(@PathParam("name") String userDetails) {
        Response resp = null;


        return resp;
    }
}
