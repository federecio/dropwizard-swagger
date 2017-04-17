// Copyright (C) 2014 Federico Recio
/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.swagger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/swagger")
@Produces(MediaType.TEXT_HTML)
public class SwaggerResource {
    private final SwaggerViewConfiguration viewConfiguration;
    private final SwaggerOAuth2Configuration oAuth2Configuration;
    private final String contextRoot;
    private final String urlPattern;

    public SwaggerResource(String urlPattern,
            SwaggerViewConfiguration viewConfiguration,
            SwaggerOAuth2Configuration oAuth2Configuration) {
        this.urlPattern = urlPattern;
        this.viewConfiguration = viewConfiguration;
        this.oAuth2Configuration = oAuth2Configuration;
        this.contextRoot = "/";
    }

    public SwaggerResource(String urlPattern,
            SwaggerViewConfiguration viewConfiguration,
            SwaggerOAuth2Configuration oAuth2Configuration,
            String contextRoot) {
        this.viewConfiguration = viewConfiguration;
        this.oAuth2Configuration = oAuth2Configuration;
        this.urlPattern = urlPattern;
        this.contextRoot = contextRoot;
    }

    @GET
    public SwaggerView get() {
        return new SwaggerView(contextRoot, urlPattern, viewConfiguration,
                oAuth2Configuration);
    }
}
