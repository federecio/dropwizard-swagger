dropwizard-swagger
==================

a Dropwizard bundle that serves Swagger UI static content and loads Swagger endpoints.



How to use it
-------------

* Add the Maven repository

        <repository>
			<id>federecio-snapshots</id>
			<url>https://repository-federecio1.forge.cloudbees.com/snapshot/</url>
		</repository>


* Add the Maven dependency

        <dependency>
            <groupId>com.federecio</groupId>
            <artifactId>dropwizard-swagger</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>


* In your service class add the Swagger bundle:

        @Override
        public void initialize(Bootstrap<YourConfiguration> bootstrap) {
            ...
            bootstrap.addBundle(new SwaggerBundle());
        }


* As usual, add Swagger annotations to your resource classes and methods


* Open a browser and hit `http://localhost:8080/index.html`