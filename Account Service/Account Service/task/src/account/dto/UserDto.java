package account.dto;

import account.domain.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Last name is required")
    private String lastname;
    @NotNull(message = "Email is required")
    @Pattern(regexp = ".*@acme\\.com", message = "Email must end with @acme.com", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @NotEmpty(message = "Password length must be 12 chars minimum!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private List<Role> roles;
    public UserDto() {
    }

    public UserDto(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
    }

    public UserDto(String name, String lastName, String email, String password, List<Role> roles) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
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
