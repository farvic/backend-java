package account.domain;

import account.model.Log;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@SequenceGenerator(name = "event_id_seq", sequenceName = "event_id_seq", allocationSize = 1)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "event_id_seq")
    private Long id;

    private String date;

    private Log action;

    private String subject;

    private String object;

    private String path;

    public Event() {

    }

    public Event(String date, Log action, String subject, String object, String path) {
        this.date = date;
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Log getAction() {
        return action;
    }

    public void setAction(Log action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Event withDate(String date) {
        this.date = date;
        return this;
    }

    public Event withAction(Log action) {
        this.action = action;
        return this;
    }

    public Event withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Event withObject(String object) {
        this.object = object;
        return this;
    }

    public Event withPath(String path) {
        this.path = path;
        return this;
    }
}
