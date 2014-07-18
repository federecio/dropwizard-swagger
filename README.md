dropwizard-swagger
==================

a Dropwizard bundle that serves Swagger UI static content and loads Swagger endpoints. Swagger UI static content is taken from https://github.com/wordnik/swagger-ui

__NOTE__: current version has been tested with Dropwizard 0.7.1 and Swagger 1.3.2


License
-------

http://www.apache.org/licenses/LICENSE-2.0


How to use it
-------------

* Add the Maven dependency

        <dependency>
            <groupId>io.federecio</groupId>
            <artifactId>dropwizard-swagger</artifactId>
            <version>0.5</version>
            <exclusions>
                <exclusion>
                    <groupId>io.dropwizard</groupId>
                    <artifactId>*</artifactId>
		</exclusion>
            </exclusions>
        </dependency>


* In your Application class:

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
* Jochen Szostek ([prefabsoft] (http://prefabsoft.com))
* Damien Raude-Morvan ([drazzib](https://github.com/drazzib))
