package org.example.wicketwise.service;

import org.example.wicketwise.dto.InningsDto;

import java.util.List;

public interface InningsService {
    InningsDto createInnings(InningsDto inningsDto);
    InningsDto getInningsById(Long id);
    List<InningsDto> getInningsByMatch(Long matchId);
    List<InningsDto> getInningsByTeam(Long teamId);
    InningsDto updateInnings(Long id, InningsDto inningsDto);
    void deleteInnings(Long id);
    InningsDto updateScore(Long id, int runs, int wickets, double overs);
    List<InningsDto> getTopTeamScores(int limit);
}
