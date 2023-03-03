package account.services;

import account.domain.Event;
import account.dto.EventDto;
import account.repositories.EventRepository;
import account.utils.EventMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;




@Service
@Qualifier("EventService")
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final HttpServletRequest request;

    public EventServiceImpl(EventRepository eventRepository, HttpServletRequest request) {
        this.eventRepository = eventRepository;
        this.request = request;
    }

    @Override
    public void logEvent(Event event) {

        String path = request.getServletPath();

        if (event.getSubject() == null) {
            event.withSubject(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        if (event.getObject() == null) {
            event.withObject(path);
        }

        event.withDate(LocalDateTime.now().toString())
                .withPath(path);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll().stream().map(EventMapper::toDto).toList();
    }
}
