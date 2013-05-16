package com.federecio.dropwizard.swagger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Federico Recio
 */
@Path("/test.json")
@Api("/test")
public class TestResource {

    @GET
    @ApiOperation("This is a dummy endpoint for test")
    public Response dummyEndpoint() {
        return Response.ok().build();
    }
}
