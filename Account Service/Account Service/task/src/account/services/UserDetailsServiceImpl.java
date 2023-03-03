package account.services;

import account.config.UserDetailsImpl;
import account.domain.User;
import account.repositories.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;

    Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));


        return new UserDetailsImpl(user);
    }



}
