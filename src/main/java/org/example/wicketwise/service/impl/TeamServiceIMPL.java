package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.TeamDto;
import org.example.wicketwise.entity.Team;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.TeamMapper;
import org.example.wicketwise.repository.TeamRepository;
import org.example.wicketwise.service.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceIMPL implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        Team team = teamMapper.toEntity(teamDto);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.toDto(savedTeam);
    }

    @Override
    public TeamDto getTeamById(Long id) {
        return teamRepository.findById(id)
                .map(teamMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
    }

    @Override
    public TeamDto getTeamByName(String name) {
        return teamRepository.findByName(name)
                .map(teamMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with name: " + name));
    }

    @Override
    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        return teamRepository.findById(id)
                .map(existingTeam -> {
                    teamMapper.updateTeamFromDto(teamDto, existingTeam);
                    Team updatedTeam = teamRepository.save(existingTeam);
                    return teamMapper.toDto(updatedTeam);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
    }

    @Override
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }

    @Override
    public List<TeamDto> searchTeams(String query) {
        return teamRepository.findByNameContainingIgnoreCase(query).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public long countTeams() {
        return teamRepository.count();
    }
}
