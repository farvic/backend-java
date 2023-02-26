package account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ChangePasswordDto {
    @JsonProperty("new_password")
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    private String password;

    public ChangePasswordDto() {
    }

    public ChangePasswordDto(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
