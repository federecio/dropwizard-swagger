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
package io.federecio.dropwizard.sample;

import com.google.common.collect.Lists;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.chained.ChainedAuthFilter;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import java.util.List;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class SampleApplication extends Application<SampleConfiguration> {

  public static void main(final String[] args) throws Exception {
    new SampleApplication().run(args);
  }

  @Override
  public String getName() {
    return "sample";
  }

  @Override
  public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
    bootstrap.addBundle(
        new SwaggerBundle<SampleConfiguration>() {
          @Override
          protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
              SampleConfiguration configuration) {
            return configuration.getSwagger();
          }
        });
  };

  @Override
  public void run(SampleConfiguration configuration, Environment environment) throws Exception {

    final BasicCredentialAuthFilter<PrincipalImpl> basicCredentialAuthFilter =
        new BasicCredentialAuthFilter.Builder<PrincipalImpl>()
            .setAuthenticator(new SampleBasicAuthenticator())
            .setPrefix("Basic")
            .buildAuthFilter();

    final OAuthCredentialAuthFilter<PrincipalImpl> oauthCredentialAuthFilter =
        new OAuthCredentialAuthFilter.Builder<PrincipalImpl>()
            .setAuthenticator(new SampleOAuth2Authenticator())
            .setPrefix("Bearer")
            .buildAuthFilter();

    final List<AuthFilter<?, PrincipalImpl>> filters =
        Lists.newArrayList(basicCredentialAuthFilter, oauthCredentialAuthFilter);
    environment.jersey().register(new AuthDynamicFeature(new ChainedAuthFilter(filters)));
    environment.jersey().register(RolesAllowedDynamicFeature.class);
    // If you want to use @Auth to inject a custom Principal type into your
    // resource
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(PrincipalImpl.class));

    // resources
    environment.jersey().register(new SampleResource());
    environment
        .jersey()
        .register(new OAuth2Resource(configuration.getSwagger().getSwaggerOAuth2Configuration()));
  }
}
