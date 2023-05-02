package com.zevseg.web.repository;

import com.zevseg.web.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * BranchRepository
 *
 * @author Sainjargal Ishdorj
 **/

public interface BranchRepository extends JpaRepository<Branch, Long> {

    int countByIdIn(List<Long> ids);

    Optional<Branch> findByName(String name);
}
