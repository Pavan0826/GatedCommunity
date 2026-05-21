package com.repository;

import com.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Long> {

    Optional<Resident> findByUsername(String username);

    boolean existsByUsername(String username);

}
