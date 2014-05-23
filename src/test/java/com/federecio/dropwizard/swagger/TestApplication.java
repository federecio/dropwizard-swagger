package com.federecio.dropwizard.swagger;

import io.dropwizard.Application;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * @author Federico Recio
 */
public class TestApplication extends Application<TestConfiguration> {

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle());
        bootstrap.addBundle(new ViewBundle());

    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        String applicationContextPath;
        ServerFactory serverFactory = configuration.getServerFactory();
        if(serverFactory instanceof SimpleServerFactory) {
            applicationContextPath = ((SimpleServerFactory) serverFactory).getApplicationContextPath();
        } else {
            applicationContextPath = "/";
        }
        environment.jersey().register(new SwaggerResource(applicationContextPath));
        environment.jersey().register(new TestResource());
        SwaggerBundle.configure(configuration);
    }
}
