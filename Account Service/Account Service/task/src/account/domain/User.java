package account.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "security_user")
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

    // @Size (min = 8, message = "Password must be at least 8 characters")
    @NotEmpty(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @Column(name = "role", nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;
    protected User() {

    }

    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String name, String lastName, String email, String password, List<Role> roles) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
