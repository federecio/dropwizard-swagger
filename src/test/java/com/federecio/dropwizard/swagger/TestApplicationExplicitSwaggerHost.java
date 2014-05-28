package com.federecio.dropwizard.swagger;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Federico Recio
 */
public class TestApplicationExplicitSwaggerHost extends Application<TestConfiguration> {

    private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        swaggerDropwizard.onInitialize(bootstrap);
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new TestResource());
        swaggerDropwizard.onRun(configuration, environment, "localhost");
    }
}
