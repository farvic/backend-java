package account.utils;

import account.domain.Event;
import account.dto.EventDto;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public static EventDto toDto(Event event) {
        return new EventDto(
                event.getDate(),
                event.getAction(),
                event.getSubject(),
                event.getObject(),
                event.getPath()
        );
    }

//    public static Event toEntity(EventDto eventDto) {
//        Event event = new Event(
//                eventDto.getDate(),
//                eventDto.getAction(),
//                eventDto.getSubject(),
//                eventDto.getObject(),
//                eventDto.getPath()
//        );
////        event.setDate(eventDto.getDate());
////        event.setAction(eventDto.getAction());
////        event.setSubject(eventDto.getSubject());
////        event.setObject(eventDto.getObject());
////        event.setPath(eventDto.getPath());
//        return event;
//    }

}
