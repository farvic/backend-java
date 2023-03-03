package account.services;

import account.domain.Group;
import account.domain.Role;

import java.util.Optional;

public interface GroupService {

    Group getGroupByRole(Role role);

    Group save(Group group);

}
