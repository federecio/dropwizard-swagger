/**
 * Copyright (C) 2014 Federico Recio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        if (usingRootPath()) {
            return SwaggerBundle.PATH;
        }
        return applicationContextPath + SwaggerBundle.PATH;
    }

    public String getContextPath() {
        if (usingRootPath()) {
            return "";
        }
        return applicationContextPath;
    }

    private boolean usingRootPath() {
        return applicationContextPath.equals("/");
    }
}
