package com.ivanboyukliev.schoolgradingapp.repository;

import com.ivanboyukliev.schoolgradingapp.domain.SchoolSystemCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolSystemCredentialRepository extends JpaRepository<SchoolSystemCredential, Long> {

    Optional<SchoolSystemCredential> findByUsername(String username);

}
