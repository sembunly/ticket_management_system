package com.bunli.user.service;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.user.dto.request.CreateGroupRequest;
import com.bunli.user.dto.request.GroupMemberRequest;

public interface GroupService {

    ResponseErrorTemplate createGroup(CreateGroupRequest request);

    ResponseErrorTemplate updateGroup(Long id, CreateGroupRequest request);

    ResponseErrorTemplate addMembersToGroup(Long groupId, GroupMemberRequest groupMemberRequest);

    ResponseErrorTemplate removeMembersFromGroup(Long groupId, GroupMemberRequest groupMemberRequest);
}
