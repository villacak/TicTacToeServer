package com.server.tictactoe.business;

import com.server.tictactoe.Constants;
import com.server.tictactoe.persistence.daos.UserDAO;
import com.server.tictactoe.persistence.entities.UserEntity;
import com.server.tictactoe.utils.CreateErrorResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by klausvillaca on 1/16/16.
 */
public class UserHelper {

    /**
     * Create a new user in the DB
     * @param name
     * @return
     * @throws Exception
     */
    public Response createUser(final String name) throws Exception {
        Response respReturn = null;
        UserEntity userEntity = retrieveUserIfAlreadyExist(name);
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setLastDatePlayed(Constants.EMPTY_DATE);
            userEntity.setStatsLoses(0);
            userEntity.setStatsTied(0);
            userEntity.setStatsWins(0);
            userEntity.setUserName(name);

            final UserDAO dao = new UserDAO();
            userEntity.setIduser(dao.save(userEntity));

            respReturn = Response.ok(userEntity, MediaType.APPLICATION_JSON).build();
        } else {
            if (userEntity != null) {
                respReturn = Response.ok(userEntity, MediaType.APPLICATION_JSON).build();
            } else {
                respReturn = CreateErrorResponse.createErrorResponse(Response.Status.CONFLICT);
            }
        }
        return respReturn;
    }


    /**
     * Retrieve user
     * @param name
     * @return
     * @throws Exception
     */
    public Response retrieveUser(final String name) throws Exception {
        Response respToReturn = null;
        final UserEntity tempUser = retrieveUserIfAlreadyExist(name);
        if (tempUser != null) {
            respToReturn = Response.ok(tempUser, MediaType.APPLICATION_JSON).build();
        } else {
            respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.NO_CONTENT);
        }
        return respToReturn;
    }


    /**
     * Delete user
     * @param name
     * @return
     * @throws Exception
     */
    public Response deleteUser(final String name) throws Exception {
        Response respToReturn = null;
        final UserEntity tempUser = retrieveUserIfAlreadyExist(name);
        if (tempUser != null) {
            final UserDAO dao = new UserDAO();
            dao.delete(tempUser);
            respToReturn = Response.ok().build();
        } else {
            respToReturn = CreateErrorResponse.createErrorResponse(Response.Status.NO_CONTENT);
        }
        return respToReturn;
    }


    /**
     * Retrieve the user data if it already exist
     * @param name
     * @return
     * @throws Exception
     */
    public UserEntity retrieveUserIfAlreadyExist(final String name) {
        UserEntity user = null;
        final UserDAO dao = new UserDAO();
        final List<UserEntity> names = dao.findByName(name);
        if (names != null && names.size() > Constants.FIRST) {
            for(final UserEntity tempUserEntity : names) {
                if (name.equals(tempUserEntity.getUserName())) {
                    user = tempUserEntity;
                    break;
                }
            }
        }
        return user;
    }
}
