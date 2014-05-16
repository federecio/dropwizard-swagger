dropwizard-swagger
==================

a Dropwizard bundle that serves Swagger UI static content and loads Swagger endpoints.

__NOTE__: current version has been tested with Dropwizard 0.7.0 and Swagger 1.3.2

How to use it (last stable build)
---------------------------------

* Add the Maven repository

        <repository>
            <id>federecio-releases</id>
            <url>https://repository-federecio1.forge.cloudbees.com/release/</url>
        </repository>


* Add the Maven dependency

        <dependency>
            <groupId>com.federecio</groupId>
            <artifactId>dropwizard-swagger</artifactId>
            <version>0.3</version>
        </dependency>


* In your service class add the Swagger bundle:

        @Override
        public void initialize(Bootstrap<YourConfiguration> bootstrap) {
            ...
            bootstrap.addBundle(new SwaggerBundle());
        }


* As usual, add Swagger annotations to your resource classes and methods


* Open a browser and hit `http://localhost:8080/swagger-ui/` (replace port 8080 accordingly)

__NOTE__: Make sure you add a `/` at the end, otherwise static resources are not loaded correctly. This is fixed in the next version.


How to use it (latest snapshot)
---------------------------------

* Add the Maven repository

        <repository>
            <id>federecio-snapshots</id>
            <url>https://repository-federecio1.forge.cloudbees.com/snapshot/</url>
        </repository>


* Add the Maven dependency

        <dependency>
            <groupId>com.federecio</groupId>
            <artifactId>dropwizard-swagger</artifactId>
            <version>0.4-SNAPSHOT</version>
        </dependency>


* In your service class add the Swagger bundle and the ViewBundle:

        @Override
        public void initialize(Bootstrap<YourConfiguration> bootstrap) {
            ...
            bootstrap.addBundle(new SwaggerBundle());
            bootstrap.addBundle(new ViewBundle());
        }

		@Override
		public void run(YourConfiguration config, Environment environment) throws Exception {
		    ...
			environment.jersey().register(new SwaggerResource(your_application_context_path));
			SwaggerBundle.configure(config);
		}

* As usual, add Swagger annotations to your resource classes and methods


* Open a browser and hit `http://localhost:8080/swagger` (replace port 8080 accordingly)

Contributors
------------

* Federico Recio ([@federecio](http://twitter.com/federecio))
* Damien Raude-Morvan ([drazzib](https://github.com/drazzib))