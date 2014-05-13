dropwizard-swagger
==================

a Dropwizard bundle that serves Swagger UI static content and loads Swagger endpoints.



How to use it
-------------

__NOTE__: current version has been tested with Dropwizard 0.7.0 and Swagger 1.3.2

* Add the Maven repository

        <repository>
            <id>federecio-releases</id>
            <url>https://repository-federecio1.forge.cloudbees.com/release/</url>
        </repository>


* Add the Maven dependency

        <dependency>
            <groupId>com.federecio</groupId>
            <artifactId>dropwizard-swagger</artifactId>
            <version>0.2</version>
        </dependency>


* In your service class add the Swagger bundle:

        @Override
        public void initialize(Bootstrap<YourConfiguration> bootstrap) {
            ...
            bootstrap.addBundle(new SwaggerBundle());
        }


* As usual, add Swagger annotations to your resource classes and methods


* Open a browser and hit `http://localhost:8080/swagger-ui`