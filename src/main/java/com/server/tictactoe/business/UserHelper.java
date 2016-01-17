package com.server.tictactoe.business;

import com.server.tictactoe.Constants;
import com.server.tictactoe.persistence.daos.UserDAO;
import com.server.tictactoe.persistence.entities.UserEntity;

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
            userEntity.setLastDatePlayed(Constants.EMPTY_DATE);
            userEntity.setStatsLoses(0);
            userEntity.setStatsTied(0);
            userEntity.setStatsWins(0);
            userEntity.setUserName(name);

            final UserDAO dao = new UserDAO();
            dao.save(userEntity);

            respReturn = Response.ok().build();
        } else {
            respReturn = Response.status(Response.Status.CONFLICT).build();
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
            respToReturn = Response.ok().entity(tempUser).build();
        } else {
            respToReturn = Response.status(Response.Status.NO_CONTENT).build();
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
            respToReturn = Response.status(Response.Status.NO_CONTENT).build();
        }
        return respToReturn;
    }


    /**
     * Retrieve the user data if it already exist
     * @param name
     * @return
     * @throws Exception
     */
    private UserEntity retrieveUserIfAlreadyExist(final String name) throws Exception {
        UserEntity user = null;
        final UserDAO dao = new UserDAO();
        final List<UserEntity> names = dao.findByName(name);
        if (names != null && names.size() > 0) {
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
