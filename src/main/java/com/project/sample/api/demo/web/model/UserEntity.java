package com.project.sample.api.demo.web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserEntity {
    private Long id;

    private String email;

    private String username;

    private String password;

    public UserEntity() {
    }

    @Builder
    public UserEntity(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
