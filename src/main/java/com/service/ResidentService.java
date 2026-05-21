package com.service;

import com.model.Resident;
import java.util.Optional;

public interface ResidentService {

    Optional<Resident> findByUsername(String username);

    boolean existsByUsername(String username);

    
    Optional<Resident> authenticate(String username, String password);

   
    Resident register(String username, String password);

}
