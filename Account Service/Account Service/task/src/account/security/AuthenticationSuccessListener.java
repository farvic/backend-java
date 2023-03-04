package account.security;

import account.config.UserDetailsImpl;
import account.services.AuthServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final AuthServiceImpl authService;

    public AuthenticationSuccessListener(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authSuccessEvent) {

        String email = authSuccessEvent.getAuthentication().getName();

        authService.resetFailedAttempts(email);
    }
}
