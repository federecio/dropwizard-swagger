/**
 * Copyright (C) 2014 Federico Recio
 *
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

/**
 * @author Federico Recio
 */
public class Constants {
    /**
     * The project's directory in which Swagger static assets live
     */
    public static final String SWAGGER_RESOURCES_PATH = "/swagger-static";
    /**
     * The path with which all HTTP requests for Swagger assets should be prefixed.
     */
    public static final String SWAGGER_URI_PATH = SWAGGER_RESOURCES_PATH;
    /**
     * The name of the {@link io.dropwizard.assets.AssetsBundle} to register.
     */
    public static final String SWAGGER_ASSETS_NAME = "swagger-assets";
    /**
     * Default host name will be used if the host cannot be determined by other means.
     */
    public static final String DEFAULT_SWAGGER_HOST = "localhost";
    /**
     * The URL to use to determine this host's name when running in AWS.
     */
    public static final String AWS_HOST_NAME_URL = "http://169.254.169.254/latest/meta-data/public-hostname/";
    /**
     * The file to check for its existence to determine if the server is running on AWS.
     */
    public static final String AWS_FILE_TO_CHECK = "/var/lib/cloud/";
    /**
     * The path to which Swagger resources are bound to
     */
    public static final String SWAGGER_PATH = "/swagger";
}
