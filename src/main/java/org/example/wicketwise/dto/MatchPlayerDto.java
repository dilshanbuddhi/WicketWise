package org.example.wicketwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchPlayerDto {
    private Long id;
    private MatchDto match;
    private TeamDto team;
    private PlayerDto player;
    private Boolean isPlaying;
    private Integer battingPosition;

}
