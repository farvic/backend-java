package account.data;

import account.domain.Group;
import account.domain.Role;
import account.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    final private GroupRepository groupRepository;

    @Autowired
    public DataLoader(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        createRoles();
    }

    private void createRoles() {
        try {
            groupRepository.save(new Group(Role.ROLE_ADMINISTRATOR));
            groupRepository.save(new Group(Role.ROLE_USER));
            groupRepository.save(new Group(Role.ROLE_ACCOUNTANT));
            groupRepository.save(new Group(Role.ROLE_AUDITOR));
        } catch (Exception e) {

        }
    }
}
