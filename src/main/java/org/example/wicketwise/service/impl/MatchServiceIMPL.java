package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.MatchDto;
import org.example.wicketwise.entity.Match;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.MatchMapper;
import org.example.wicketwise.repository.MatchRepository;
import org.example.wicketwise.service.MatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchServiceIMPL implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Override
    public MatchDto createMatch(MatchDto matchDto) {
        Match match = matchMapper.toEntity(matchDto);
        Match savedMatch = matchRepository.save(match);
        return matchMapper.toDto(savedMatch);
    }

    @Override
    public MatchDto getMatchById(Long id) {
        return matchRepository.findById(id)
                .map(matchMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));
    }

    @Override
    public List<MatchDto> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchDto> getUpcomingMatches() {
        return matchRepository.findByStartTimeAfter(LocalDateTime.now()).stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchDto> getMatchesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return matchRepository.findByStartTimeBetween(startDate, endDate).stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchDto> getMatchesByTeam(Long teamId) {
        return matchRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId).stream()
                .map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MatchDto updateMatch(Long id, MatchDto matchDto) {
        return matchRepository.findById(id)
                .map(existingMatch -> {
                    matchMapper.updateMatchFromDto(matchDto, existingMatch);
                    Match updatedMatch = matchRepository.save(existingMatch);
                    return matchMapper.toDto(updatedMatch);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));
    }

    @Override
    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match not found with id: " + id);
        }
        matchRepository.deleteById(id);
    }

    @Override
    public MatchDto updateMatchStatus(Long id, String status) {
        return matchRepository.findById(id)
                .map(match -> {
                    match.setStatus(status);
                    Match updatedMatch = matchRepository.save(match);
                    return matchMapper.toDto(updatedMatch);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));
    }

    @Override
    public long countMatches() {
        return matchRepository.count();
    }
}
