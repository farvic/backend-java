package account.services;

import account.domain.Event;
import account.dto.EventDto;

import java.util.List;

public interface EventService {

    void logEvent(Event event);

    List<EventDto> findAll();

}
