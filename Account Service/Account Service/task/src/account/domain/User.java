package account.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.HashSet;

import java.util.Set;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @NotEmpty(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastname;

//    @Email(regexp = ".*@acme\\.com",message = "Email must end with @acme.com", flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotNull(message = "Email is required")
    @Pattern(regexp = ".*@acme\\.com", message = "Email must end with @acme.com", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @Column(name = "group")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_security_group",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    Set<Group> securityGroup = new HashSet<>();

    private boolean enabled;

    private boolean accountNonLocked;

    private int failedLoginAttempts;


    public User() {

    }

    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.accountNonLocked = true;
    }

    public User(String name, String lastName, String email, String password, HashSet<Group> securityGroup) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.securityGroup = securityGroup;
        this.accountNonLocked = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Group> getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(Set<Group> securityGroup) {
        this.securityGroup = securityGroup;
    }
    public void addSecurityGroup(Group securityGroup) {
        this.securityGroup.add(securityGroup);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

}
