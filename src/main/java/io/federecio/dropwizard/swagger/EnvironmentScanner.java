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

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import io.dropwizard.setup.Environment;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.jersey.server.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link com.wordnik.swagger.jaxrs.config.JaxrsScanner} that allows Swagger
 * to scan only those resources which are registered in Dropwizard's Jersey
 * environment. This scanner avoids documenting resources which may have fallen
 * out of use or have not yet been implemented.
 *
 * @author Thomas Wilson
 */
public class EnvironmentScanner extends BeanConfig {

    private final Set<Class<?>> classes = new HashSet<>();
    private final Environment environment;

    public EnvironmentScanner(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Set<Class<?>> classes() {
        if (!classes.isEmpty()) {
            return classes;
        }
        if (getResourcePackage() == null) {
            setResourcePackage("");
        }
        super.classes();
        
        
        Set<Resource> resources = environment.jersey().getResourceConfig().getResources();
        for (Resource resource : resources) {
            scan(resource.getHandlerClasses());
        }
        Set<Class<?>> registered = environment.jersey().getResourceConfig().getClasses();
        scan(registered);
        Set<Object> instances = environment.jersey().getResourceConfig().getInstances();
        for (Object obj : instances) {
            scan(obj.getClass());
        }
        Set<Object> singletons = environment.jersey().getResourceConfig().getSingletons();
        for (Object obj : singletons) {
            scan(obj.getClass());
        }
        return classes;
    }

    @Override
    public void setScan(boolean shouldScan) {
        if (getResourcePackage() != null && !getResourcePackage().isEmpty()) {
            super.setScan(shouldScan);
        }
        ScannerFactory.setScanner(this);
    }
    
    protected void scan(final Set<Class<?>> input) {
        for (Class<?> cls : input) {
            scan(cls);
        }
    }
    protected void scan(final Class<?> cls) {
        if (cls.isAnnotationPresent(Api.class)) {
            classes.add(cls);
        }
    }
}
