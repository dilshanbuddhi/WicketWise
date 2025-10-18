package org.example.wicketwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.wicketwise.entity.Player;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private Long id;
    private String name;
    private Integer age;
    private String role; // Batsman, Bowler, All-rounder, Wicket-keeper
    private String battingStyle;
    private String bowlingStyle;
    private TeamDto team;
    

}
