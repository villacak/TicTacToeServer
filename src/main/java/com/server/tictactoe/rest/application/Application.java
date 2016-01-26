package com.server.tictactoe.rest.application;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;

import javax.ws.rs.ApplicationPath;

/**
 * Created by klausvillaca on 1/15/16.
 */

@ApplicationPath("/rest")
public class Application extends ResourceConfig {
    public Application() {
        packages("com.server.tictactoe.rest.endpoints");
        register(EntityFilteringFeature.class);

        // Some logs to see request been received
        register(new LoggingFilter(java.util.logging.Logger.getLogger("STANDARD_MESSAGE"), true));
        EncodingFilter.enableFor(this, GZipEncoder.class);
        property(ServerProperties.TRACING, "ALL");
    }
}
