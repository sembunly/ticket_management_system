package com.bunli.user.service;

import com.bunli.common.criteria.BaseSearchCriteria;
import com.bunli.common.dto.PageableRequestVO;
import com.bunli.common.dto.PageableResponseVO;
import com.bunli.user.dto.request.UserFilterRequest;
import com.bunli.user.entity.User;

public interface UserSearchService {
    PageableResponseVO<User> searchUsers(UserFilterRequest filterRequest);

    PageableResponseVO<User> searchUsersWithCriteria(BaseSearchCriteria searchCriteria, PageableRequestVO pageable);
}
