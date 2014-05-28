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
            <version>0.4.1-SNAPSHOT</version>
        </dependency>


* In your service class add the Swagger bundle and the ViewBundle:

		private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();

		@Override
		public void initialize(Bootstrap<TestConfiguration> bootstrap) {
		    ...
			swaggerDropwizard.onInitialize(bootstrap);
		}

		@Override
		public void run(TestConfiguration configuration, Environment environment) throws Exception {
		    ...
			swaggerDropwizard.onRun(configuration, environment);
		}

* As usual, add Swagger annotations to your resource classes and methods


* Open a browser and hit `http://localhost:8080/swagger` (replace port 8080 accordingly)


Running in AWS
--------------

Whether this service is running on AWS is determined by checking for the presence of the folder "/var/lib/cloud". If the folder is actually present then the host Swagger should be bound to is set to the result of a GET request to "http://169.254.169.254/latest/meta-data/public-hostname/".

Should the directory "/var/lib/cloud" not be present the host is set to "localhost"


Manually setting the host name
------------------------------

There might be a few cases where you want to set the host name to which Swagger is bound to. In this case you need to do:

		@Override
		public void run(TestConfiguration configuration, Environment environment) throws Exception {
		    ...
			swaggerDropwizard.onRun(configuration, environment, "your_host_here");
		}

Contributors
------------

* Federico Recio ([@federecio](http://twitter.com/federecio))
* Damien Raude-Morvan ([drazzib](https://github.com/drazzib))