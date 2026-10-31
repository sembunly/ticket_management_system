package com.bunli.user.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bunli.common.constant.ApiConstant;
import com.bunli.common.dto.EmptyObject;
import com.bunli.common.exception.CustomMessageException;
import com.bunli.user.entity.User;
import com.bunli.user.entity.Role;
import com.bunli.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Authentication income {}", authentication);
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        Optional<User> user;
        try {
            user = userRepository.findFirstByUsernameAndStatus(username, ApiConstant.ACTIVE.getKey());
        } catch (Exception ex) {
            log.error("{}", ex.getLocalizedMessage());
            throw new CustomMessageException(
                    "User not found.",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    new EmptyObject(),
                    HttpStatus.UNAUTHORIZED);
        }
        if (user.isEmpty()) {
            throw new CustomMessageException(
                    "User not found.",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    new EmptyObject(),
                    HttpStatus.UNAUTHORIZED);
        }
        final List<GrantedAuthority> grantedAuthorities = grantedAuthorities(user.get().getRoles().stream().toList());
        final Authentication authz = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);

        log.info("Authentication out come {}", authz);
        return authz;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    private List<GrantedAuthority> grantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<String> permissions = new HashSet<>();

        if (!roles.isEmpty()) {
            roles.forEach(role -> {
                permissions.add(role.getName());
            });
        }

        permissions.forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission)));
        return grantedAuthorities;
    }

}
