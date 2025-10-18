package org.example.wicketwise.service;

import org.example.wicketwise.dto.MatchDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchService {
    MatchDto createMatch(MatchDto matchDto);
    MatchDto getMatchById(Long id);
    List<MatchDto> getAllMatches();
    List<MatchDto> getUpcomingMatches();
    List<MatchDto> getMatchesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<MatchDto> getMatchesByTeam(Long teamId);
    MatchDto updateMatch(Long id, MatchDto matchDto);
    void deleteMatch(Long id);
    MatchDto updateMatchStatus(Long id, String status);
    long countMatches();
}
