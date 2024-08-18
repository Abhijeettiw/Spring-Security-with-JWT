package com.example.jwt.Security;

import com.example.jwt.Entities.AppUsers;
import com.example.jwt.Repositories.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUsers> byUsername = userRepo.findByUsername(username);
        if (byUsername.isPresent()){
            return byUsername.get();
        }else {
            throw new UsernameNotFoundException("User Do Not Exists !!!");
        }
    }
}
