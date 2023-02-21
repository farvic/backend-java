package platform.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
// import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "codes")
public class Code {
    @Id
    @Column(name = "id")
    @JsonIgnore
    private String id;
    @Column(name = "code_snippet")
    private String code;

    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss.SSSSSS")
    private LocalDateTime date;

    @Column(name = "time")
    private long time;

    @JsonIgnore
    @Column(name = "time_restricted")
    private boolean timeRestricted;

    @Column(name = "views")
    private long views;

    @JsonIgnore
    @Column(name = "view_restricted")
    private boolean viewRestricted;

    public Code() {
    }

    public Code(String code) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.date = LocalDateTime.now();
        this.time = 0;
        this.timeRestricted = false;
        this.views = 0;
        this.viewRestricted = false;
    }

    public Code(String id, String code) {
        this.id = id;
        this.code = code;
        this.date = LocalDateTime.now();
        this.time = 0;
        this.timeRestricted = false;
        this.views = 0;
        this.viewRestricted = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public boolean isTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public boolean isViewRestricted() {
        return viewRestricted;
    }

    public void setViewRestricted(boolean viewRestricted) {
        this.viewRestricted = viewRestricted;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", timeRestricted=" + timeRestricted +
                ", views=" + views +
                ", viewRestricted=" + viewRestricted +
                '}';
    }
}
