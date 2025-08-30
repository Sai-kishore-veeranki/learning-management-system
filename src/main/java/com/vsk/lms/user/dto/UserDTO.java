package com.vsk.lms.user.dto;


import com.vsk.lms.user.entity.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String bio;
    private String profilePicture;
    private Role role;
}
