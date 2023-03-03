package account.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "security_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, updatable = false)
    private Role role;


    public Group() {


    }
    public Group(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return getRole() == group.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRole());
    }
}
