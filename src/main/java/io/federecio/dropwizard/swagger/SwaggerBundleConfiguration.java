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

import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import io.swagger.jaxrs.config.BeanConfig;

/**
 * For the meaning of all these properties please refer to Swagger documentation
 * or {@link io.swagger.jaxrs.config.BeanConfig}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwaggerBundleConfiguration {

    /**
     * This is the only property that is required for Swagger to work correctly.
     * <p/>
     * It is a comma separated list of the all the packages that contain the
     * {@link io.swagger.annotations.Api} annotated resources
     */
    @NotEmpty
    private String resourcePackage;

    private String title;
    private String version;
    private String description;
    private String termsOfServiceUrl;
    private String contact;
    private String license;
    private String licenseUrl;
    private String validatorUrl;
    private Boolean prettyPrint = true;

    /**
     * For most of the scenarios this property is not needed.
     * <p/>
     * This is not a property for Swagger but for bundle to set up Swagger UI
     * correctly. It only needs to be used of the root path or the context path
     * is set programmatically and therefore cannot be derived correctly. The
     * problem arises in that if you set the root path or context path in the
     * run() method in your Application subclass the bundle has already been
     * initialized by that time and so does not know you set the path
     * programmatically.
     */
    @JsonProperty
    private String uriPrefix;

    @JsonProperty
    public String getResourcePackage() {
        return resourcePackage;
    }

    @JsonProperty
    public void setResourcePackage(String resourcePackage) {
        this.resourcePackage = resourcePackage;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty
    public String getVersion() {
        return version;
    }

    @JsonProperty
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    @JsonProperty
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty
    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    @JsonProperty
    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    @JsonProperty
    public String getContact() {
        return contact;
    }

    @JsonProperty
    public void setContact(String contact) {
        this.contact = contact;
    }

    @JsonProperty
    public String getLicense() {
        return license;
    }

    @JsonProperty
    public void setLicense(String license) {
        this.license = license;
    }

    @JsonProperty
    public String getLicenseUrl() {
        return licenseUrl;
    }

    @JsonProperty
    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    @JsonProperty
    public String getUriPrefix() {
        return uriPrefix;
    }

    @JsonProperty
    public void setUriPrefix(String uriPrefix) {
        this.uriPrefix = uriPrefix;
    }

    @JsonProperty
    public String getValidatorUrl() {
        return validatorUrl;
    }

    @JsonProperty
    public void setValidatorUrl(final String validatorUrl) {
        this.validatorUrl = validatorUrl;
    }

    @JsonProperty
    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    @JsonProperty
    public void setIsPrettyPrint(final boolean isPrettyPrint) {
        this.prettyPrint = isPrettyPrint;
    }

    @JsonIgnore
    public BeanConfig build(String urlPattern) {
        if (Strings.isNullOrEmpty(resourcePackage)) {
            throw new IllegalStateException(
                    "Resource package needs to be specified"
                            + " for Swagger to correctly detect annotated resources");
        }

        final BeanConfig config = new BeanConfig();
        config.setTitle(title);
        config.setVersion(version);
        config.setDescription(description);
        config.setContact(contact);
        config.setLicense(license);
        config.setLicenseUrl(licenseUrl);
        config.setTermsOfServiceUrl(termsOfServiceUrl);
        config.setPrettyPrint(prettyPrint);
        config.setBasePath(urlPattern);
        config.setResourcePackage(resourcePackage);
        config.setScan(true);
        return config;
    }
}
