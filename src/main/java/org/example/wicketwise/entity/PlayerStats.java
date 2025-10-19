package org.example.wicketwise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Player player;

    // Batting stats
    private Integer matches = 0;
    private Integer innings = 0;
    private Integer runs = 0;
    private Integer ballsFaced = 0;
    private Integer fours = 0;
    private Integer sixes = 0;
    private Integer highestScore = 0;
    private Double battingAverage = 0.0;
    private Double battingStrikeRate = 0.0;
    private Integer fifties = 0;
    private Integer hundreds = 0;

    // Bowling stats
    private Integer wickets = 0;
    private Integer oversBowled = 0;
    private Integer maidens = 0;
    private Integer runsConceded = 0;
    private String bestBowlingInnings;
    private String bestBowlingMatch;
    private Double bowlingAverage = 0.0;
    private Double bowlingEconomy = 0.0;
    private Double bowlingStrikeRate = 0.0;
    private Integer fourWickets = 0;
    private Integer fiveWickets = 0;
    private Integer ballsBowled = 0;
}
