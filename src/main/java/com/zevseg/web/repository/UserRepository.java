package com.zevseg.web.repository;

import com.zevseg.web.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository
 *
 * @author Sainjargal Ishdorj
 **/

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

//    List<User> findAllByBranchesInAndRolesIn(List<Branch> branches, List<Role> roles);
//
//    List<User> findAllByBranchesIn(List<Branch> branches);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

}
