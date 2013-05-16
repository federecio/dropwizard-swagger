package com.federecio.dropwizard.swagger;

import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Environment;

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
        environment.addResource(new ApiListingResourceJSON());
        super.run(environment);
    }
}
