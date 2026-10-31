package com.bunli.user.service;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.user.dto.request.AuthenticationRequest;

public interface AuthService {

    ResponseErrorTemplate login(AuthenticationRequest authenticationRequest);
}
