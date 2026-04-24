package com.gym.dto;

import lombok.Data;

@Data
public class UserDto {
    private java.util.UUID id;
    private String name;
    private String email;
    private String password;
    private String role;
}
