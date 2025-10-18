package org.example.wicketwise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "matches") // or cricket_match, etc.
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team homeTeam;

    @ManyToOne
    private Team awayTeam;

    private String venue;
    private LocalDateTime startTime;
    private Integer overs; // e.g. 20, 50

    private String status; // scheduled, live, finished
    private String result;

    @ManyToOne
    private User createdBy;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<MatchPlayer> matchPlayers;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Innings> inningsList;
}
