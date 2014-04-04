package com.federecio.dropwizard.swagger;

import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Environment;

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
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
        super.run(environment);
    }
}
