/*
 * Copyright © 2014 Federico Recio (N/A)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Copyright (C) 2014 Federico Recio
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.federecio.dropwizard.swagger;

import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;
import java.util.List;
import java.util.Map;

/**
 * This SwaggerSpecFilter checks for the presence of an access=internal @ApiParam annotation, and
 * hides the parameter if it is present.
 *
 * <p>This is primarily useful for hiding Dropwizard @Auth parameters.
 *
 * <p>From: https://www.reonomy.com/augmenting-dropwizard-with-swagger/
 */
public class AuthParamFilter implements SwaggerSpecFilter {
  @Override
  public boolean isOperationAllowed(
      Operation operation,
      ApiDescription api,
      Map<String, List<String>> params,
      Map<String, String> cookies,
      Map<String, List<String>> headers) {
    return true;
  }

  @Override
  public boolean isParamAllowed(
      Parameter parameter,
      Operation operation,
      ApiDescription api,
      Map<String, List<String>> params,
      Map<String, String> cookies,
      Map<String, List<String>> headers) {
    String access = parameter.getAccess();
    if (access != null && "internal".equals(access)) return false;
    return true;
  }

  @Override
  public boolean isPropertyAllowed(
      Model model,
      Property property,
      String propertyName,
      Map<String, List<String>> params,
      Map<String, String> cookies,
      Map<String, List<String>> headers) {
    return true;
  }
}
