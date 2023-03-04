package account.config;

import account.security.CustomLoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import static account.domain.Role.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CustomLoginFailureHandler loginFailureHandler;





    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfig(RestAuthenticationEntryPoint restAuthenticationEntryPoint, AccessDeniedHandler accessDeniedHandler) {        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)// Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                    .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                    .mvcMatchers("/api/auth/changepass").hasAnyAuthority(ROLE_USER.getDescription(), ROLE_ADMINISTRATOR.getDescription(), ROLE_ACCOUNTANT.getDescription())
                    .mvcMatchers("/api/empl/payment").hasAnyAuthority(ROLE_ACCOUNTANT.getDescription(), ROLE_USER.getDescription())
                    .mvcMatchers("/api/admin/**").hasAuthority(ROLE_ADMINISTRATOR.getDescription())
                  .mvcMatchers("/api/security/**").hasAuthority(ROLE_AUDITOR.getDescription())
                    .mvcMatchers("/api/acct/**").hasAuthority(ROLE_ACCOUNTANT.getDescription())
                    .mvcMatchers(HttpMethod.GET,"/api/empl/payments").hasAnyAuthority(ROLE_ACCOUNTANT.getDescription(), ROLE_USER.getDescription())
                    .mvcMatchers("/api/acct/payments").hasAuthority(ROLE_ACCOUNTANT.getDescription())
                //.anyRequest().authenticated() // all other requests must be authenticated
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
//                .formLogin()
//                    .successHandler(loginSuccessHandler)
//                    .failureHandler(loginFailureHandler); // no session
    }




    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
