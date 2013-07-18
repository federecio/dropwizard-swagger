package com.federecio.dropwizard.swagger;

import com.codahale.dropwizard.assets.AssetsBundle;
import com.codahale.dropwizard.setup.Environment;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;

/**
 * @author Federico Recio
 */
public class SwaggerBundle extends AssetsBundle {

    public static final String PATH = "/swagger-ui";

    public SwaggerBundle() {
        super(PATH);
    }

    @Override
    public void run(Environment environment) {
        environment.jersey().register(new ApiListingResourceJSON());
        super.run(environment);
    }
}
