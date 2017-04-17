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
package io.federecio.dropwizard.swagger.selenium.auth;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.OAuth2Definition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;

@Api("/auth")
@Path("/auth.json")
@SwaggerDefinition(securityDefinition = @SecurityDefinition(apiKeyAuthDefinitions = {
        @ApiKeyAuthDefinition(in = ApiKeyAuthDefinition.ApiKeyLocation.QUERY, key = "query_api_key", name = "api_key"),
        @ApiKeyAuthDefinition(in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, key = "header_api_key", name = "Authorization") },
    oAuth2Definitions = {@OAuth2Definition(flow = OAuth2Definition.Flow.IMPLICIT, key = "oauth2", authorizationUrl = "/oauth2/auth") })
)
public class AuthResource {
    public static final String OPERATION_DESCRIPTION = "This is a protected dummy endpoint for test";

    @GET
    @ApiOperation(OPERATION_DESCRIPTION)
    @Produces(MediaType.TEXT_PLAIN)
    public Response protectedDummyEndpoint(
            @ApiParam(hidden = true) @Auth PrincipalImpl user) {
        return Response.ok(user.getName()).build();
    }

    @GET
    @Path("oauth2")
    @ApiOperation(value = OPERATION_DESCRIPTION, authorizations = @Authorization("oauth2"))
    @Produces(MediaType.TEXT_PLAIN)
    public Response protectedWithOAuth2DummyEndpoint(@HeaderParam("Authorization") String bearer) {
        return Response.ok(bearer).build();
    }

    @GET
    @Path("apiKey")
    @ApiOperation(OPERATION_DESCRIPTION)
    @Produces(MediaType.TEXT_PLAIN)
    public Response apiKeyDummyEndpoint(
            @ApiParam(hidden = true) @QueryParam("api_key") String apiKey) {
        return Response.ok(apiKey).build();
    }
}
