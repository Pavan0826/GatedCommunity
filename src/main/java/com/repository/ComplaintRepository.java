package com.repository;

import com.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
	
	java.util.List<Complaint> findByResidentUsername(String username);
}
