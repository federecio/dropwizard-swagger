package com.federecio.dropwizard.swagger;

import com.google.common.base.Charsets;
import io.dropwizard.views.View;

public class SwaggerView extends View {

    private final String applicationContextPath;

    protected SwaggerView(String applicationContextPath) {
        super("index.ftl", Charsets.UTF_8);
        if(applicationContextPath.charAt(0) != '/') {
            applicationContextPath = '/' + applicationContextPath;
        }
        this.applicationContextPath = applicationContextPath;
    }

    public String getSwaggerStaticPath() {
        return applicationContextPath + SwaggerBundle.PATH;
    }

    public String getContextPath() {
        return applicationContextPath;
    }
}
