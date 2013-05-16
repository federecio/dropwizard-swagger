package com.federecio.dropwizard.swagger;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * @author Federico Recio
 */
public class TestService extends Service<TestConfiguration> {

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle());
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new TestResource());
    }
}
