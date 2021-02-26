package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.GroupService;
import org.udg.pds.springtodo.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

// This class is used to process all the authentication related URLs
@RequestMapping(path="/groups")
@RestController
public class GroupController extends BaseController {

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @PostMapping
    @JsonView(Views.Complete.class)
    public IdObject addGroup(HttpSession session, @Valid @RequestBody CreateGroup group) {

        Long userId = getLoggedUser(session);
        return groupService.addGroup(userId, group.name, group.description);

    }

    @PostMapping(path="/{gid}/members/{uid}")
    public String addMember(HttpSession session,
                          @PathVariable("gid") Long groupId,
                          @PathVariable("uid") Long userId) {

        Long ownerId = getLoggedUser(session);
        groupService.addMemberToGroup(ownerId, groupId, userId);
        return BaseController.OK_MESSAGE;
    }

    static class CreateGroup {
        @NotNull
        public String name;
        @NotNull
        public String description;
    }

}
