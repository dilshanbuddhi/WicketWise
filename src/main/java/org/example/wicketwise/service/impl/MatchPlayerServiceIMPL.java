package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wicketwise.dto.MatchPlayerDto;
import org.example.wicketwise.entity.MatchPlayer;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.MatchPlayerMapper;
import org.example.wicketwise.repository.MatchPlayerRepo;
import org.example.wicketwise.repository.MatchRepo;
import org.example.wicketwise.repository.PlayerRepo;
import org.example.wicketwise.repository.TeamRepo;
import org.example.wicketwise.service.MatchPlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MatchPlayerServiceIMPL implements MatchPlayerService {

    private final MatchPlayerRepo matchPlayerRepo;
    private final MatchRepo matchRepo;
    private final PlayerRepo playerRepo;
    private final TeamRepo teamRepo;
    private final MatchPlayerMapper matchPlayerMapper;

    @Override
    public MatchPlayerDto createMatchPlayer(MatchPlayerDto matchPlayerDto) {
        log.debug("Creating new match player record");
        
        // Validate match exists
        if (!matchRepo.existsById(matchPlayerDto.getMatch().getId())) {
            throw new ResourceNotFoundException("Match not found with id: " + matchPlayerDto.getMatch().getId());
        }
        
        // Validate player exists
        if (!playerRepo.existsById(matchPlayerDto.getPlayer().getId())) {
            throw new ResourceNotFoundException("Player not found with id: " + matchPlayerDto.getPlayer().getId());
        }
        
        // Validate team exists
        if (!teamRepo.existsById(matchPlayerDto.getTeam().getId())) {
            throw new ResourceNotFoundException("Team not found with id: " + matchPlayerDto.getTeam().getId());
        }
        
        try {
            MatchPlayer matchPlayer = matchPlayerMapper.toEntity(matchPlayerDto);
            MatchPlayer savedMatchPlayer = matchPlayerRepo.save(matchPlayer);
            log.info("Created new match player record with ID: {}", savedMatchPlayer.getId());
            return matchPlayerMapper.toDto(savedMatchPlayer);
        } catch (Exception e) {
            log.error("Error creating match player record: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create match player record", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MatchPlayerDto getMatchPlayerById(Long id) {
        log.debug("Fetching match player record with ID: {}", id);
        return matchPlayerRepo.findById(id)
                .map(matchPlayerMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Match player record not found with ID: {}", id);
                    return new ResourceNotFoundException("Match player record not found with id: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchPlayerDto> getMatchPlayersByMatch(Long matchId) {
        log.debug("Fetching match players for match ID: {}", matchId);
        
        if (!matchRepo.existsById(matchId)) {
            throw new ResourceNotFoundException("Match not found with id: " + matchId);
        }
        
        return matchPlayerRepo.findByMatchId(matchId).stream()
                .map(matchPlayerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchPlayerDto> getMatchPlayersByPlayer(Long playerId) {
        log.debug("Fetching match players for player ID: {}", playerId);
        
        if (!playerRepo.existsById(playerId)) {
            throw new ResourceNotFoundException("Player not found with id: " + playerId);
        }
        
        return matchPlayerRepo.findByPlayerId(playerId).stream()
                .map(matchPlayerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchPlayerDto> getMatchPlayersByTeam(Long teamId) {
        log.debug("Fetching match players for team ID: {}", teamId);
        
        if (!teamRepo.existsById(teamId)) {
            throw new ResourceNotFoundException("Team not found with id: " + teamId);
        }
        
        return matchPlayerRepo.findByTeamId(teamId).stream()
                .map(matchPlayerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MatchPlayerDto updateMatchPlayer(Long id, MatchPlayerDto matchPlayerDto) {
        log.debug("Updating match player record with ID: {}", id);
        
        return matchPlayerRepo.findById(id)
                .map(existingMatchPlayer -> {
                    matchPlayerMapper.updateMatchPlayerFromDto(matchPlayerDto, existingMatchPlayer);
                    MatchPlayer updatedMatchPlayer = matchPlayerRepo.save(existingMatchPlayer);
                    log.info("Updated match player record with ID: {}", id);
                    return matchPlayerMapper.toDto(updatedMatchPlayer);
                })
                .orElseThrow(() -> {
                    log.warn("Match player record not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Match player record not found with id: " + id);
                });
    }

    @Override
    public void deleteMatchPlayer(Long id) {
        log.debug("Deleting match player record with ID: {}", id);
        
        if (!matchPlayerRepo.existsById(id)) {
            log.warn("Match player record not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("Match player record not found with id: " + id);
        }
        
        try {
            matchPlayerRepo.deleteById(id);
            log.info("Deleted match player record with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting match player record with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete match player record with ID: " + id, e);
        }
    }
}
