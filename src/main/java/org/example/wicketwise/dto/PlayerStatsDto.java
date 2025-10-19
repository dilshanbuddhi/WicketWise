package org.example.wicketwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatsDto {
    private Long id;
    private PlayerDto player;
    
    // Batting stats
    private Integer matches;
    private Integer innings;
    private Integer runs;
    private Integer ballsFaced;
    private Integer fours;
    private Integer sixes;
    private Integer highestScore;
    private Double battingAverage;
    private Double battingStrikeRate;
    private Integer fifties;
    private Integer hundreds;
    
    // Bowling stats
    private Integer wickets;
    private Integer oversBowled;
    private Integer maidens;
    private Integer runsConceded;
    private String bestBowlingInnings;
    private String bestBowlingMatch;
    private Double bowlingAverage;
    private Double bowlingEconomy;
    private Double bowlingStrikeRate;
    private Integer fourWickets;
    private Integer fiveWickets;
    private Integer ballsBowled;
}
