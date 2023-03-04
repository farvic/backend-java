package account.config;

import account.domain.Event;
import account.model.Log;
import account.repositories.UserRepository;
import account.services.EventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger LOG
            = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    private final EventServiceImpl eventService;

    @Autowired
    public CustomAccessDeniedHandler(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException, ServletException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.warn("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());
        }

        Event event = new Event()
                .withAction(Log.ACCESS_DENIED)
                .withSubject(auth.getName());
        eventService.logEvent(event);

        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "Access Denied!");
    }
}
