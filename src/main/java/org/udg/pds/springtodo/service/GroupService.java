package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.repository.GroupRepository;

import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    UserService userService;

    public GroupRepository crud() {
        return groupRepository;
    }

    @Transactional
    public IdObject addGroup(Long userId, String name, String description) {
        try {
            User user = userService.getUser(userId);

            Group g = new Group(name, description);

            g.setUser(user);

            user.addGroup(g);

            groupRepository.save(g);
            return new IdObject(g.getId());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public void addMemberToGroup(Long ownerId, Long groupId, Long userId) {
        Group g = this.getGroup(groupId);
        User owner = g.getOwner();
        Long Id = owner.getId();
        User member = userService.getUser(userId);

        if(ownerId != Id)
            throw new ServiceException("This user is not the owner of the task");

        try {
            g.addMember(member);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public Group getGroup(Long id) {
        Optional<Group> g = groupRepository.findById(id);
        if (!g.isPresent())
            throw new ServiceException("Unknown group");
        return g.get();
    }

}
