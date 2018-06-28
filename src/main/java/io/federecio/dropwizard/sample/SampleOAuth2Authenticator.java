/*
 * Copyright © 2018 Smoke Turner, LLC (contact@smoketurner.com)
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

import static io.federecio.dropwizard.sample.OAuth2Resource.MOCKED_OAUTH2_TOKEN;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import java.util.Optional;

public class SampleOAuth2Authenticator implements Authenticator<String, PrincipalImpl> {

  @Override
  public Optional<PrincipalImpl> authenticate(String token) throws AuthenticationException {
    if (MOCKED_OAUTH2_TOKEN.equals(token)) {
      return Optional.of(new PrincipalImpl("oauth2 user"));
    }
    return Optional.empty();
  }
}
