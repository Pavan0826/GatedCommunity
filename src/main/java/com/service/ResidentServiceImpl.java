package com.service;

import com.model.Resident;
import com.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public Optional<Resident> findByUsername(String username) {
        return residentRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return residentRepository.existsByUsername(username);
    }

    @Override
    public Optional<Resident> authenticate(String username, String password) {
        Optional<Resident> opt = residentRepository.findByUsername(username);
        if (opt.isEmpty()) return Optional.empty();
        Resident resident = opt.get();
        
        if (resident.getPassword() != null && resident.getPassword().equals(password)) {
            return Optional.of(resident);
        }
        return Optional.empty();
    }

    @Override
    public Resident register(String username, String password) {
        Resident r = new Resident();
        r.setUsername(username);
        r.setPassword(password);
        return residentRepository.save(r);
    }
}
