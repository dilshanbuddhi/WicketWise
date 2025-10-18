package org.example.wicketwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InningsDto {
    private Long id;
    private MatchDto match;
    private TeamDto battingTeam;
    private TeamDto bowlingTeam;
    private Integer inningsNumber;
    private Integer runs;
    private Integer wickets;
    private Double overs;
    private Integer extras;
    private List<DeliveryDto> deliveries;

}
