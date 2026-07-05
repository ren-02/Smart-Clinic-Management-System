package com.smartclinic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String tokenType;
    private Long id;
    private String name;
    private String email;
    private String role;
    private String message;

    public static LoginResponse success(String token, Long id, String name, String email, String role, String message) {
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .id(id)
                .name(name)
                .email(email)
                .role(role)
                .message(message)
                .build();
    }
}
