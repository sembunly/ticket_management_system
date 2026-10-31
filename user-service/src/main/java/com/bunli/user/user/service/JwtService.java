package com.bunli.user.service;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.user.entity.CustomUserDetail;
import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {

    Claims extractClaims(String token);

    Key getKey();

    String generateToken(com.bunli.user.entity.CustomUserDetail customUserDetail);

    String refreshToken(CustomUserDetail customUserDetail);

    boolean isValidToken(String token);

    ResponseErrorTemplate verifyToken(String authorizationHeader);

}
