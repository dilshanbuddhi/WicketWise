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
    private Integer matches;
    private Integer innings;
    private Integer runs;
    private Integer ballsFaced;
    private Integer fifties;
    private Integer hundreds;
    private Double average;
    private Double strikeRate;
    private Integer wickets;
    private Integer ballsBowled;
    private Integer runsConceded;
    private Double economy;

}
