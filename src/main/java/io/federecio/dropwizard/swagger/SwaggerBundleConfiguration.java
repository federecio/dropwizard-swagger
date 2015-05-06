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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * For the meaning of all these properties please refer to Swagger documentation or {@link com.wordnik.swagger.jaxrs.config.BeanConfig}
 *
 * @author Tristan Burch
 * @author Federico Recio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwaggerBundleConfiguration {

    /**
     * This is the only property that is required for Swagger to work correctly.
     * <p/>
     * It is a comma separated list of the all the packages that contain the {@link com.wordnik.swagger.annotations.Api}
     * annoted resources
     */
    @JsonProperty
    private String resourcePackage;

    @JsonProperty
    private String title;

    @JsonProperty
    private String version;

    @JsonProperty
    private String description;

    @JsonProperty
    private String termsOfServiceUrl;

    @JsonProperty
    private String contact;

    @JsonProperty
    private String license;

    @JsonProperty
    private String licenseUrl;

    @JsonProperty
    private boolean isValidationUrlDisabled;

    /**
     * For most of the scenarios this property is not needed.
     * <p/>
     * This is not a property for Swagger but for bundle to set up Swagger UI correctly.
     * It only needs to be used of the root path or the context path is set programatically
     * and therefore cannot be derived correctly. The problem arises in that if you set the
     * root path or context path in the run() method in your Application subclass the bundle
     * has already been initialized by that time and so does not know you set the path programatically.
     */
    @JsonProperty
    private String uriPrefix;

    public String getResourcePackage() {
        return resourcePackage;
    }

    public void setResourcePackage(String resourcePackage) {
        this.resourcePackage = resourcePackage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getUriPrefix() {
        return uriPrefix;
    }

    public void setUriPrefix(String uriPrefix) {
        this.uriPrefix = uriPrefix;
    }

    public boolean isValidationUrlDisabled() {
        return isValidationUrlDisabled;
    }

    public void setIsValidationUrlDisabled(final boolean isValidationUrlDisabled) {
        this.isValidationUrlDisabled = isValidationUrlDisabled;
    }

    @Override
    public String toString() {
        return "SwaggerBundleConfiguration{" +
                "resourcePackage='" + resourcePackage + '\'' +
                ", title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", termsOfServiceUrl='" + termsOfServiceUrl + '\'' +
                ", contact='" + contact + '\'' +
                ", license='" + license + '\'' +
                ", licenseUrl='" + licenseUrl + '\'' +
                ", isValidationUrlDisabled='" + isValidationUrlDisabled + '\'' +
                '}';
    }
}
