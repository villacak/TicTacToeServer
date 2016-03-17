package com.server.tictactoe.rest.endpoints;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by klausvillaca on 1/25/16.
 */

@Path("/dummy/v1")
@Consumes("*/*")
public class DummyService {

    @GET
    @Path("/call/{name}")
    @Produces("*/*")
    public Response userDetails(@PathParam("name") final String name) {
        Response resp = Response.ok("Dummy returning").build();
        return resp;
    }
}
