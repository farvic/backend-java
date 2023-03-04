package account.security;

import account.domain.Event;
import account.domain.User;
import account.model.Log;
import account.services.AuthServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private AuthServiceImpl authService;

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomLoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        LOGGER.info("onAuthenticationFailure: " + exception.getMessage());

        Optional<String> email = Optional.of(request.getParameter("email"));

        Event event = new Event().withAction(Log.BRUTE_FORCE).withSubject(email.orElse("Anonymous"));

        User user = authService.findUserByEmail(email.get());

        LOGGER.info("onAuthenticationFailure: " + email.get());

        if (user != null && !user.isAccountNonLocked()) {

                exception = new LockedException("User account is locked!");


        }

//        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
