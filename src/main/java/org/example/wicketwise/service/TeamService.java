package org.example.wicketwise.service;

import org.example.wicketwise.dto.TeamDto;

import java.util.List;

public interface TeamService {
    TeamDto createTeam(TeamDto teamDto);
    TeamDto getTeamById(Long id);
    TeamDto getTeamByName(String name);
    List<TeamDto> getAllTeams();
    TeamDto updateTeam(Long id, TeamDto teamDto);
    void deleteTeam(Long id);
    List<TeamDto> searchTeams(String query);
    long countTeams();
}
