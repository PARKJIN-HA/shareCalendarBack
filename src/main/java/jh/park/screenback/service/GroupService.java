package jh.park.screenback.service;

import jh.park.screenback.dto.UserGroupDTO;
import jh.park.screenback.model.User;
import jh.park.screenback.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

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

    public UserGroup update(Long id, UserGroupDTO userGroup) {
        Optional<UserGroup> optionalUserGroup = groupRepository.findById(id);
        if (optionalUserGroup.isPresent()) {
            UserGroup existingUserGroup = optionalUserGroup.get();
            existingUserGroup.setName(userGroup.getName());

            String email = userGroup.getInviteEmail();

            if (email != null) {
                User user = userService.findByEmail(email);
                if (user != null) {
                    existingUserGroup.getGroupMembers().add(user.getId());
                    UserGroup updatedGroup = groupRepository.save(existingUserGroup);
                    eventPublisher.publishEvent(new JoinRequestAcceptedEvent(this, updatedGroup, user));
                    return updatedGroup;
                }
            }

            return groupRepository.save(existingUserGroup);
        }
        return null;
    }

    public UserGroup acceptJoinRequest(Long groupId, Long userId) {
        Optional<UserGroup> optionalUserGroup = groupRepository.findById(groupId);
        if (optionalUserGroup.isPresent()) {
            UserGroup userGroup = optionalUserGroup.get();
            User user = userService.findById(userId);

            if (user != null) {
                List<User> members = new ArrayList<>();
                members.add(user);
                for (Long groupMember : userGroup.getGroupMembers()) {
                    User temp = userService.findById(groupMember);
                    members.add(temp);
                }
                userGroup.setGroupMembers(members);
                UserGroup updatedGroup = groupRepository.save(userGroup);
                eventPublisher.publishEvent(new JoinRequestAcceptedEvent(this, updatedGroup, user));
                return updatedGroup;
            }
        }
        return null;
    }

    public void sendJoinRequestNotification(UserGroup group, User user) {
        eventPublisher.publishEvent(new JoinRequestEvent(this, group, user));
    }

    public Iterable<Object> findAllByGroupMember(User user) {
        return groupRepository.findAllByGroupMembers_Id(user.getId());
    }
}
