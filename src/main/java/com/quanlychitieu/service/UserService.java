package com.quanlychitieu.service;

import com.quanlychitieu.model.User;
import com.quanlychitieu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    public User getUser(Long id){

        return repository.findById(id).orElse(null);
    }

    public void save(User user){

        repository.save(user);
    }
    
    @Override
    public UserDetails loadUserByUsername(
            String email
    ) throws UsernameNotFoundException {

        User user = repository.findByEmail(email);

        if(user == null){

            throw new UsernameNotFoundException(
                    "User not found"
            );
        }

        return user;
    }
    
    public User findByEmail(String email){

        return repository.findByEmail(email);
    }
}