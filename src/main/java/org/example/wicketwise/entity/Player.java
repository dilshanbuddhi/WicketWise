package org.example.wicketwise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dob;

    private String role; // "Batsman", "Bowler", "Allrounder", "Wicket Keeper"
    private String battingStyle; // e.g. Right-hand bat
    private String bowlingStyle; // e.g. Left-arm spin

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
