package com.server.tictactoe.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.tictactoe.Constants;
import com.server.tictactoe.business.pojos.ErrorToReturn;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.Response;

/**
 * Created by klausvillaca on 3/6/16.
 */
public class CreateErrorResponse {

    private static final String OBJECT_KEY = "ErrorToReturn";

    private static final String EXCEPTION_TEMPLATE = "{\"ErrorToReturn\" : {\"status\": \"%1$s\", \"message\": \"%2$s\"}}";

    public static Response createErrorResponse(final Response.Status statusCode) {
        Response resp = null;
        try {
            final ErrorToReturn errorObject =
                    new ErrorToReturn(statusCode.getStatusCode() + Constants.EMPTY, statusCode.getReasonPhrase());
            final String msg = String.format(EXCEPTION_TEMPLATE, statusCode.getStatusCode(),
                    statusCode.getReasonPhrase());
            resp = Response.status(statusCode).entity(msg).build();
        } catch (Exception e) {
            final String msg = String.format(EXCEPTION_TEMPLATE, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            resp = Response.status(statusCode).entity(msg).build();
        }
        return resp;
    }
}
