package com.example.eobrazovanje.service;

import com.example.eobrazovanje.dto.LozinkaDTO;
import com.example.eobrazovanje.model.User;
import com.example.eobrazovanje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<User> getOne(Long id) {
        return repo.findById(id);
    }

    public User getOne(String username) {
        return repo.getByUsername(username);
    }

    public User save(User user) {
        return repo.save(user);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void promenaLozinke(String username, LozinkaDTO dto) throws Exception {
        User user = repo.getByUsername(username);
        if (dto.getPassword().equals(dto.getRepeatPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            save(user);
        }
        throw new Exception("Lozinke se ne poklapaju");
    }

    public User findByUsername(String username) {
        return repo.getByUsername(username);
    }
}
