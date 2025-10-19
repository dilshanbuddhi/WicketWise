package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wicketwise.dto.InningsDto;
import org.example.wicketwise.entity.Innings;
import org.example.wicketwise.entity.Team;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.InningsMapper;
import org.example.wicketwise.repository.InningsRepository;
import org.example.wicketwise.repository.TeamRepository;
import org.example.wicketwise.service.InningsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InningsServiceIMPL implements InningsService {

    private final InningsRepository inningsRepository;
    private final TeamRepository teamRepository;
    private final InningsMapper inningsMapper;

    @Override
    @Transactional
    public InningsDto createInnings(InningsDto inningsDto) {
        log.debug("Creating new innings");
        try {
            Innings innings = inningsMapper.toEntity(inningsDto);
            Innings savedInnings = inningsRepository.save(innings);
            log.info("Created new innings with ID: {}", savedInnings.getId());
            return inningsMapper.toDto(savedInnings);
        } catch (Exception e) {
            log.error("Error creating innings: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create innings", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public InningsDto getInningsById(Long id) {
        log.debug("Fetching innings with ID: {}", id);
        return inningsRepository.findById(id)
                .map(inningsMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Innings not found with ID: {}", id);
                    return new ResourceNotFoundException("Innings not found with id: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<InningsDto> getInningsByMatch(Long matchId) {
        log.debug("Fetching innings for match ID: {}", matchId);
        try {
            return inningsRepository.findByMatchId(matchId).stream()
                    .filter(obj -> obj instanceof Innings)
                    .map(Innings.class::cast)
                    .map(inningsMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching innings for match ID {}: {}", matchId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch innings for match ID: " + matchId, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<InningsDto> getInningsByTeam(Long teamId) {
        log.debug("Fetching innings for team ID: {}", teamId);
        try {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));
            
            return inningsRepository.findInningsByTeam(team).stream()
                    .filter(obj -> obj instanceof Innings)
                    .map(Innings.class::cast)
                    .map(inningsMapper::toDto)
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw ResourceNotFoundException as is
        } catch (Exception e) {
            log.error("Error fetching innings for team ID {}: {}", teamId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch innings for team ID: " + teamId, e);
        }
    }

    @Override
    @Transactional
    public InningsDto updateInnings(Long id, InningsDto inningsDto) {
        log.debug("Updating innings with ID: {}", id);
        
        return inningsRepository.findById(id)
                .map(existingInnings -> {
                    // Update the existing innings with new values
                    Innings updatedInnings = inningsMapper.toEntity(inningsDto);
                    updatedInnings.setId(id); // Ensure ID remains the same
                    updatedInnings.setMatch(existingInnings.getMatch()); // Preserve the match relationship
                    
                    Innings savedInnings = inningsRepository.save(updatedInnings);
                    log.info("Updated innings with ID: {}", id);
                    return inningsMapper.toDto(savedInnings);
                })
                .orElseThrow(() -> {
                    log.warn("Innings not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Innings not found with id: " + id);
                });
    }

    @Override
    @Transactional
    public void deleteInnings(Long id) {
        log.debug("Deleting innings with ID: {}", id);
        
        if (!inningsRepository.existsById(id)) {
            log.warn("Innings not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("Innings not found with id: " + id);
        }
        
        try {
            inningsRepository.deleteById(id);
            log.info("Deleted innings with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting innings with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete innings with ID: " + id, e);
        }
    }

    @Override
    @Transactional
    public InningsDto updateScore(Long id, int runs, int wickets, double overs) {
        log.debug("Updating score for innings ID: {} - Runs: {}, Wickets: {}, Overs: {}", id, runs, wickets, overs);
        
        return inningsRepository.findById(id)
                .map(innings -> {
                    innings.setRuns(runs);
                    innings.setWickets(wickets);
                    innings.setOvers(overs);
                    
                    Innings updatedInnings = inningsRepository.save(innings);
                    log.info("Updated score for innings ID: {}", id);
                    return inningsMapper.toDto(updatedInnings);
                })
                .orElseThrow(() -> {
                    log.warn("Innings not found for score update with ID: {}", id);
                    return new ResourceNotFoundException("Innings not found with id: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<InningsDto> getTopTeamScores(int limit) {
        log.debug("Fetching top {} team scores", limit);
        
        try {
            // Get all teams
            List<Team> teams = teamRepository.findAll();
            
            return teams.stream()
                    .map(team -> inningsRepository
                            .findTopScoresByTeam(team.getId(), PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "runs")))
                            .stream()
                            .filter(obj -> obj instanceof Innings)
                            .map(Innings.class::cast)
                            .findFirst()
                            .orElse(null)
                    )
                    .filter(innings -> innings != null)
                    .sorted(Comparator.comparingInt(Innings::getRuns).reversed())
                    .limit(limit)
                    .map(inningsMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching top team scores: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch top team scores", e);
        }
    }
}
