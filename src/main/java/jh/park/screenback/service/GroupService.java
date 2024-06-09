package jh.park.screenback.service;

import jh.park.screenback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jh.park.screenback.model.UserGroup;
import jh.park.screenback.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public UserGroup save(UserGroup userGroup) {
        return groupRepository.save(userGroup);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    public Optional<UserGroup> findById(Long id) {
        return groupRepository.findById(id);
    }

    public List<UserGroup> findAllByOwner(User owner) {
        return groupRepository.findAllByOwner(owner);
    }

    public List<UserGroup> findUserGroupsByGroupMember(User groupMember) {
        return groupRepository.findUserGroupsByGroupMembers_Id(groupMember.getId());
    }

    // Add other necessary methods
}
