package org.example.wicketwise.service;

import org.example.wicketwise.dto.MatchPlayerDto;

import java.util.List;

public interface MatchPlayerService {
    MatchPlayerDto createMatchPlayer(MatchPlayerDto matchPlayerDto);
    MatchPlayerDto getMatchPlayerById(Long id);
    List<MatchPlayerDto> getMatchPlayersByMatch(Long matchId);
    List<MatchPlayerDto> getMatchPlayersByPlayer(Long playerId);
    List<MatchPlayerDto> getMatchPlayersByTeam(Long teamId);
    MatchPlayerDto updateMatchPlayer(Long id, MatchPlayerDto matchPlayerDto);
    void deleteMatchPlayer(Long id);
}
