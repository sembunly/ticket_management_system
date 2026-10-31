package com.bunli.user.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bunli.user.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByNameIn(Set<String> groupNames);

    boolean existsByName(String name);
}
