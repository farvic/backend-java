package account.config;

import account.domain.Group;
import account.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private User user;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;

    private final boolean accountNonLocked;

    public UserDetailsImpl(User user) {
        username = user.getEmail().toLowerCase();
        password = user.getPassword();
        rolesAndAuthorities = getAuthorities(user);
        accountNonLocked = user.isAccountNonLocked();
    }

    public User getUser() {
        return user;
    }

    public List<GrantedAuthority> getAuthorities(User user) {

        Set<Group> groups = user.getSecurityGroup();

        return groups.stream()
                .map(group -> new SimpleGrantedAuthority(group.getRole().getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 4 remaining methods that just return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
