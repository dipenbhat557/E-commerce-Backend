package com.eCom.Payload;

import com.eCom.Model.User;

import lombok.Data;

@Data
public class JwtResponse {
    
    private String token;
    private User user;
    
}
