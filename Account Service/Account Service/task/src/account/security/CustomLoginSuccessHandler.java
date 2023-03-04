//package account.security;
//
//import account.config.UserDetailsImpl;
//import account.domain.User;
//import account.services.AuthService;
//
//import org.springframework.security.core.Authentication;
//
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.logging.Logger;
//
//@Component
//public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final AuthService authService;
//
//    private final static Logger LOGGER = Logger.getLogger(CustomLoginSuccessHandler.class.getName());
//
//    public CustomLoginSuccessHandler(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        User user = userDetails.getUser();
//        if (user.getFailedLoginAttempts() > 0) {
//            authService.resetFailedAttempts(user);
//        }
//
//        LOGGER.info("onAuthenticationSuccess: " + user.getEmail());
//
//
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//
//}
