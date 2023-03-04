package account.security;

import account.config.UserDetailsImpl;

import account.domain.Event;
import account.model.Log;
import account.services.AuthServiceImpl;
import account.services.EventServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final static String ACME_MAIL = "@acme.com";

    private final static Logger LOGGER = Logger.getLogger(AuthenticationFailureListener.class.getName());
    private final AuthServiceImpl authService;
    private final EventServiceImpl eventService;

    public AuthenticationFailureListener(AuthServiceImpl authService, EventServiceImpl eventService) {
        this.authService = authService;
        this.eventService = eventService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authFailureEvent) {

        String email = (String) authFailureEvent.getAuthentication().getPrincipal();

        if (!email.endsWith(ACME_MAIL)) {
            Event event = new Event()
                    .withAction(Log.LOGIN_FAILED)
                    .withSubject(email);
            eventService.logEvent(event);
            throw new BadCredentialsException("Bad credentials");
        }

        LOGGER.info("Failure: onApplicationEvent: " + email);

        authService.increaseFailedAttempts(email);
    }

}
