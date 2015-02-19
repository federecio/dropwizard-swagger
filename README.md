dropwizard-swagger
==================

a Dropwizard bundle that serves Swagger UI static content and loads Swagger endpoints. Swagger UI static content is taken from https://github.com/wordnik/swagger-ui

Current version has been tested with Dropwizard 0.7.1 and Swagger 1.3.2

__NOTE__: the project's group id has been changed `io.federecio` and therefore all packages have been renamed accordingly

License
-------

http://www.apache.org/licenses/LICENSE-2.0

How to use it
-------------

* Add the Maven dependency (__now available in Maven Central!__)

        <dependency>
            <groupId>io.federecio</groupId>
            <artifactId>dropwizard-swagger</artifactId>
            <version>0.5.3</version>
        </dependency>


* In your Application class:

		@Override
		public void initialize(Bootstrap<YourConfiguration> bootstrap) {
		    ...
			bootstrap.addBundle(new SwaggerBundle<YourConfiguration>());
            ...
		}


* As usual, add Swagger annotations to your resource classes and methods


* Open a browser and hit `http://localhost:8080/swagger` (replace port 8080 accordingly)

Sample Application
------------------

Take a look at this sample application that shows how to integrate DropWizard and Swagger: [dropwizard-swagger-sample-app](https://github.com/federecio/dropwizard-swagger-sample-app)

Running in AWS
--------------

Whether this service is running on AWS is determined by checking for the presence of the folder "/var/lib/cloud". If the folder is actually present then the host Swagger should be bound to is set to the result of a GET request to "http://169.254.169.254/latest/meta-data/public-hostname/".

Should the directory `/var/lib/cloud` not be present the host is set to the result of `InetAddress.getLocalHost().getHostName()` or `localhost`.


Manually setting the host name and the port number
--------------------------------------------------

Swagger needs to be able to tell the client what hostname and port number to talk to, in the simple case where the user talks directly to the dropwizard process, that's easy, but users often stick a reverse proxy, such as nginx, in front of an application server, so the drop wizard process might listen on localhost:4242 while the client talks to it via nginx on api.example.com:80.

If you need to force swagger to generate urls for a different host and/or port number, then you need to override the getSwaggerBundleConfiguration method to load the host and/or port number from an instance of a SwaggerBundleConfiguration:

		@Override
        public void initialize(Bootstrap<YourConfiguration> bootstrap) {
            ...
            bootstrap.addBundle(new SwaggerBundle<YourConfiguration>() {
                @Override
                public SwaggerBundleConfiguration getSwaggerBundleConfiguration(YourConfigurationClass configuration) {
                    return new SwaggerBundleConfiguration("your_host_here", 4242);
                }
            });
            ...
        }


Contributors
------------

* Federico Recio [@federecio](http://twitter.com/federecio)
* Jochen Szostek [prefabsoft] (http://prefabsoft.com)
* Damien Raude-Morvan [drazzib] (https://github.com/drazzib)
* Marcel Stör [marcelstoer] (https://github.com/marcelstoer)
* Flemming Frandsen https://github.com/dren-dk
* Tristan Burch [tburch] (https://github.com/tburch)
* Matt Carrier [mattcarrier] (https://github.com/mattcarrier)
