package com.federecio.dropwizard.swagger;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Federico Recio
 */
public class TestService extends Application<TestConfiguration> {


    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle());

    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {

        environment.jersey().register(new TestResource());
    }

}
