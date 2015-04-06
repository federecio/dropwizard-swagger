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
 * @author Tristan Burch
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwaggerBundleConfiguration {

    @JsonProperty
    private String protocol = null;

    @JsonProperty
    private String host;

    @JsonProperty
    private Integer port = null;

    public SwaggerBundleConfiguration(String protocol, String host, Integer port) {
        this(host, port);
        this.protocol = protocol;
    }

    public SwaggerBundleConfiguration(String protocol, String host) {
        this(host);
        this.protocol = protocol;
    }

    public SwaggerBundleConfiguration(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public SwaggerBundleConfiguration(String host) {
        this.host = host;
    }

    public SwaggerBundleConfiguration(Integer port) {
        this.port = port;
    }

    public SwaggerBundleConfiguration() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "SwaggerBundleConfiguration{" +
                "protocol='" + protocol + "'" +
                ", host='" + host + "'" +
                ", port=" + port +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SwaggerBundleConfiguration that = (SwaggerBundleConfiguration) o;

        if (protocol != null ? !protocol.equals(that.protocol) : that.protocol != null) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((port == null) ? 0 : port.hashCode());
        result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
        return result;
    }
}
