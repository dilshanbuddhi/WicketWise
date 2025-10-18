package org.example.wicketwise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.wicketwise.entity.enums.Role;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, SCORER, VIEWER
}
