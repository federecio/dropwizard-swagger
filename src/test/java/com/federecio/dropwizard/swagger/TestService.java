package com.federecio.dropwizard.swagger;

import com.codahale.dropwizard.Application;
import com.codahale.dropwizard.setup.Bootstrap;
import com.codahale.dropwizard.setup.Environment;

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
