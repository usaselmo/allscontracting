package com.allscontracting.repo.fsimpl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allscontracting.model.Client;

public interface ClientJpaRepository extends JpaRepository<Client, Long> {

}
