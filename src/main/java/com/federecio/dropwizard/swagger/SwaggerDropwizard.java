package com.federecio.dropwizard.swagger;

import java.io.IOException;

import io.dropwizard.Configuration;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * @author Federico Recio
 */
public class SwaggerDropwizard {

    public void onInitialize(Bootstrap<?> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle());
        bootstrap.addBundle(new ViewBundle());
    }

    public void onRun(Configuration configuration, Environment environment) throws IOException {
        _onRun(configuration, environment);
        SwaggerBundle.configure(configuration);
    }

    /**
     * Call this method instead of {@link this#onRun(Configuration, Environment)} if the automatic host detection
     * does not work correctly.
     */
    public void onRun(Configuration configuration, Environment environment, String host) {
        _onRun(configuration, environment);
        SwaggerBundle.configure(configuration, host);
    }

    private void _onRun(Configuration configuration, Environment environment) {
        String applicationContextPath;
        ServerFactory serverFactory = configuration.getServerFactory();
        if (serverFactory instanceof SimpleServerFactory) {
            applicationContextPath = ((SimpleServerFactory) serverFactory).getApplicationContextPath();
        } else {
            applicationContextPath = "/";
        }
        environment.jersey().register(new SwaggerResource(applicationContextPath));
    }
}
