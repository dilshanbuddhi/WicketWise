package org.example.wicketwise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Innings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Match match;

    @ManyToOne
    private Team battingTeam;

    @ManyToOne
    private Team bowlingTeam;

    private Integer inningsNumber; // 1 or 2
    private Integer runs = 0;
    private Integer wickets = 0;
    private Double overs = 0.0;
    private Integer extras = 0;

    @OneToMany(mappedBy = "innings", cascade = CascadeType.ALL)
    private List<Delivery> deliveries;
}
