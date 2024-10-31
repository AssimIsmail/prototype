package com.backend.crm1.dto;

import com.backend.crm1.model.Role;
import lombok.Data;

@Data
public class UserDTO {

    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private String profile;
    private Long centreId;
    private String token;
}
