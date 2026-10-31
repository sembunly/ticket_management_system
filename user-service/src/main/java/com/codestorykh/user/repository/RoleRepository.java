package com.bunli.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    List<Role> findAllByNameIn(Set<String> roles);

    boolean existsByName(String name);

    List<Role> findAllByStatus(String status);

    Optional<Role> findFirstByName(String name);

    Optional<Role> findFirstById(Long id);

    List<Role> findAllByIdIn(Set<Long> ids);

    Optional<Role> findFirstByNameAndStatus(String name, String status);

    List<Role> findByNameIn(Set<String> roles);
}
