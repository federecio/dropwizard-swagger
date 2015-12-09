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

import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.ext.SwaggerExtension;
import io.swagger.models.parameters.Parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DropwizardSwaggerExtension implements SwaggerExtension {
    @Override
    public String extractOperationMethod(ApiOperation apiOperation, Method method, Iterator<SwaggerExtension> chain) {
        if (chain.hasNext()) {
            return chain.next().extractOperationMethod(apiOperation, method, chain);
        }
        return null;
    }

    @Override
    public List<Parameter> extractParameters(List<Annotation> annotations, Type type, Set<Type> typesToSkip, Iterator<SwaggerExtension> chain) {
        if (shouldIgnoreType(annotations, type, typesToSkip)) {
            return Collections.emptyList();
        }
        if (chain.hasNext()) {
            return chain.next().extractParameters(annotations, type, typesToSkip, chain);
        }
        return Collections.emptyList();
    }

    private boolean shouldIgnoreType(List<Annotation> annotations, Type type, Set<Type> typesToSkip) {
        for (Annotation item : annotations) {
            if ("io.dropwizard.auth.Auth".equals(item.annotationType().getName())) {
                typesToSkip.add(type);
                return true;
            }
        }
        return false;
    }
}
