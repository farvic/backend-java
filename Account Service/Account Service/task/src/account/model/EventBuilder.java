package account.model;

import account.domain.Event;

public class EventBuilder {

    private String date;

    private Log action;

    private String subject;

    private String object;

    private String path;

    public EventBuilder() {
    }

    public EventBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public EventBuilder setAction(Log action) {
        this.action = action;
        return this;
    }

    public EventBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EventBuilder setObject(String object) {
        this.object = object;
        return this;
    }

    public EventBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public Event createEvent() {
        return new Event(date, action, subject, object, path);
    }


}
