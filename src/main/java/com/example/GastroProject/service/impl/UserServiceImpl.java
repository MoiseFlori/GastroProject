package com.example.GastroProject.service.impl;


import com.example.GastroProject.entity.*;
import com.example.GastroProject.repository.UserRepository;
import com.example.GastroProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        user.setAuthorities(authorities);
        return user;
    }


    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }


    public String determineRedirectURL(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isPatient = authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_PATIENT"));
        boolean isDoctor = authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"));

        if (isPatient) {
            return "redirect:/user-page";
        } else if (isDoctor) {
            return "redirect:/doctor-page";
        } else {
            return "redirect:/welcome";
        }
    }
}
