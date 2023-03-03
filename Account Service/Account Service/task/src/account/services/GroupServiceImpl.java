package account.services;

import account.domain.Group;
import account.domain.Role;
import account.errors.UserExistsException;
import account.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {



    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group getGroupByRole(Role role) {
        return groupRepository.findByRole(role).orElseThrow(
                () -> new UserExistsException("Not Found", HttpStatus.NOT_FOUND, "Role not found!")
        );
    }

    @Override
    @Transactional
    public Group save(Group group) {
        Group groupToSave = getGroupByRole(group.getRole());
        groupToSave.setId(group.getId());
        return groupRepository.save(group);
    }
}
