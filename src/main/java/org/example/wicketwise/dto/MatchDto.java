package org.example.wicketwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    private Long id;
    private TeamDto homeTeam;
    private TeamDto awayTeam;
    private String venue;
    private LocalDateTime startTime;
    private Integer overs;
    private String status;
    private String result;
    private UserDto createdBy;
    private List<MatchPlayerDto> matchPlayers;
    private List<InningsDto> inningsList;
    

}
