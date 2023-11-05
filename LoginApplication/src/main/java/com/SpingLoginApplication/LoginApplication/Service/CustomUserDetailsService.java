package com.SpingLoginApplication.LoginApplication.Service;

import com.SpingLoginApplication.LoginApplication.Model.UserEntity;
import com.SpingLoginApplication.LoginApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmail(username);
        if (user==null) {
            throw new UsernameNotFoundException("user not found");
        }
            return new CustomUserDetail(user);
    }
}
