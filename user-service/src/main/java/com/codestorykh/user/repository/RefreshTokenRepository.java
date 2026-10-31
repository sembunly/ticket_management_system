package com.bunli.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codestorykh.user.entity.RefreshToken;
import com.codestorykh.user.entity.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteAllByUserIn(List<User> users);

    Optional<RefreshToken> findByUser(User user);
}
