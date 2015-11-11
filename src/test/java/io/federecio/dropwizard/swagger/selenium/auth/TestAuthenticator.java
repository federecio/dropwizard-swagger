package io.federecio.dropwizard.swagger.selenium.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;

public class TestAuthenticator implements Authenticator<String, PrincipalImpl>{

    @Override
    public Optional<PrincipalImpl> authenticate(String token) throws AuthenticationException {
        if ("secret" .equals(token)) {
            return Optional.of(new PrincipalImpl(token));
        }
        return Optional.absent();
    }
}
