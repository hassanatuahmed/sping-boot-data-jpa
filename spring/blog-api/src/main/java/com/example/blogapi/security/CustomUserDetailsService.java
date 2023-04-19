package com.example.blogapi.security;

import com.example.blogapi.model.Role;
import com.example.blogapi.model.User;
import com.example.blogapi.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(
                ()->new UsernameNotFoundException("User not found:"));

        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream().map((role -> new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),grantedAuthorities);
    }

//    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Set<Role> roles ){
//       return roles.stream().map(role ->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//
//
//    }
}
