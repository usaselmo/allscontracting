package com.allscontracting.repo.fsimpl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.allscontracting.model.Lead;

@Repository
public interface LeadJpaRepository extends JpaRepository<Lead, String>{
	
}
