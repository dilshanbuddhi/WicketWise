package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.TeamDto;
import org.example.wicketwise.entity.Team;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamMapper {
    
    private final ModelMapper modelMapper;
    
    public TeamDto toDto(Team team) {
        if (team == null) {
            return null;
        }
        return modelMapper.map(team, TeamDto.class);
    }
    
    public Team toEntity(TeamDto teamDto) {
        if (teamDto == null) {
            return null;
        }
        return modelMapper.map(teamDto, Team.class);
    }
    
    public void updateTeamFromDto(TeamDto teamDto, Team team) {
        if (teamDto == null || team == null) {
            return;
        }
        modelMapper.map(teamDto, team);
    }
}
