package account.dto;


public record UserDto(Long id, String name, String lastname, String email, java.util.List<String> roles) {

}
