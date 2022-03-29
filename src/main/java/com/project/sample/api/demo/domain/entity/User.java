package com.project.sample.api.demo.domain.entity;

import com.project.sample.api.demo.domain.type.UserRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserRole role;

    @Column
    private LocalDateTime deletedDate;

    public User(String username, String email, LocalDateTime deletedDate) {
        this.username = username;
        this.email = email;
        this.deletedDate = deletedDate;
    }

    @Builder
    public User(Long id, String username, String password, String email, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public boolean isUnregister() {
        return nonNull(deletedDate);
    }

    public void unregister() {
        this.deletedDate = LocalDateTime.now();
    }
}