package account.dto;

import account.domain.Group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;

public class UserRequest {
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
    private HashSet<Group> securityGroup = new HashSet<>();
    public UserRequest() {
    }

    public UserRequest(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
    }

    public UserRequest(String name, String lastName, String email, String password, HashSet<Group> securityGroup) {
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.securityGroup = securityGroup;
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

    public HashSet<Group> getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(HashSet<Group> securityGroup) {
        this.securityGroup = securityGroup;
    }
}
