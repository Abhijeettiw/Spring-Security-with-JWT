package com.example.jwt.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CustomAuthProvider implements AuthenticationProvider {
    private BCryptPasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    CustomAuthProvider(BCryptPasswordEncoder passwordEncoder,UserDetailsService userDetailsService){
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        if(username != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(userDetails != null){
                String existUsername = userDetails.getUsername();
                String existPassword = userDetails.getPassword();
                if(existUsername.equals(username) && passwordEncoder.matches(password,existPassword)){
                    return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
                }else throw new BadCredentialsException("Bad Credentials");
            }else throw new UsernameNotFoundException("User Do Not Exists !!!");
        } else
            throw new BadCredentialsException("Username Cannot be Empty");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
