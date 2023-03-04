package account.services;

import account.config.UserDetailsImpl;

import account.domain.User;

import account.errors.UserExistsException;
import account.repositories.UserRepository;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;
    final static Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepository.findByEmail(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        if (!user.isAccountNonLocked()) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST,"User account is locked");
        }

        return new UserDetailsImpl(user);
    }

}
