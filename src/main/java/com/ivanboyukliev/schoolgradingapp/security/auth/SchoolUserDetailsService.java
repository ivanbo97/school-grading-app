package com.ivanboyukliev.schoolgradingapp.security.auth;

import com.ivanboyukliev.schoolgradingapp.domain.SchoolSystemCredential;
import com.ivanboyukliev.schoolgradingapp.repository.SchoolSystemCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolUserDetailsService implements UserDetailsService {

    private SchoolSystemCredentialRepository credentialRepository;

    @Autowired
    public SchoolUserDetailsService(SchoolSystemCredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SchoolSystemCredential> retrievedUserDetails = credentialRepository.findByUsername(username);
        if (retrievedUserDetails.isEmpty()) {
            throw new UsernameNotFoundException(username + " NOT FOUND");
        }
        return new SchoolUserDetails(retrievedUserDetails.get());
    }
}
