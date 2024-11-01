package com.example.GastroProject.dto;


import com.example.GastroProject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private Set<Role> roles;

    private String name;

    private String email;

    private String password;


}
