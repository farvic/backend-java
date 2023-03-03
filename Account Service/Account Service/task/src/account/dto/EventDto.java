package account.dto;

import account.model.Log;

public record EventDto(String date, Log action, String subject, String object, String path) {
}
