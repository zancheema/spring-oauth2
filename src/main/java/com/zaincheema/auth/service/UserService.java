package com.zaincheema.auth.service;

import com.zaincheema.auth.auth.UserPrincipal;
import com.zaincheema.auth.model.User;
import com.zaincheema.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found."));
        return UserPrincipal.create(user);
    }

    public UserPrincipal loadUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow();
        return UserPrincipal.create(user);
    }
}
