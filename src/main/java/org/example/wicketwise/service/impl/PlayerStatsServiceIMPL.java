package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wicketwise.dto.PlayerStatsDto;
import org.example.wicketwise.entity.PlayerStats;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.PlayerMapper;
import org.example.wicketwise.mapper.PlayerStatsMapper;

import org.example.wicketwise.repository.PlayerRepository;
import org.example.wicketwise.repository.PlayerStatsRepository;
import org.example.wicketwise.service.PlayerStatsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlayerStatsServiceIMPL implements PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepo;
    private final PlayerRepository playerRepo;
    private final PlayerStatsMapper playerStatsMapper;
    private final PlayerMapper playerMapper;

    @Override
    public PlayerStatsDto createPlayerStats(PlayerStatsDto playerStatsDto) {
        log.debug("Creating new player stats record");
        
        // Validate player exists
        if (!playerRepo.existsById(playerStatsDto.getPlayer().getId())) {
            throw new ResourceNotFoundException("Player not found with id: " + playerStatsDto.getPlayer().getId());
        }
        
        // Check if stats already exist for this player
        playerStatsRepo.findByPlayerId(playerStatsDto.getPlayer().getId())
                .ifPresent(stats -> {
                    log.warn("Player stats already exist for player ID: {}", playerStatsDto.getPlayer().getId());
                    throw new IllegalStateException("Player stats already exist for this player");
                });
        
        try {
            PlayerStats playerStats = playerStatsMapper.toEntity(playerStatsDto);
            PlayerStats savedPlayerStats = playerStatsRepo.save(playerStats);
            log.info("Created new player stats with ID: {}", savedPlayerStats.getId());
            return playerStatsMapper.toDto(savedPlayerStats);
        } catch (Exception e) {
            log.error("Error creating player stats: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create player stats", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerStatsDto getPlayerStatsById(Long id) {
        log.debug("Fetching player stats with ID: {}", id);
        return playerStatsRepo.findById(id)
                .map(playerStatsMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Player stats not found with ID: {}", id);
                    return new ResourceNotFoundException("Player stats not found with id: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerStatsDto getPlayerStatsByPlayer(Long playerId) {
        log.debug("Fetching player stats for player ID: {}", playerId);
        
        if (!playerRepo.existsById(playerId)) {
            throw new ResourceNotFoundException("Player not found with id: " + playerId);
        }
        
        return playerStatsRepo.findByPlayerId(playerId)
                .map(playerStatsMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Player stats not found for player ID: {}", playerId);
                    return new ResourceNotFoundException("Player stats not found for player id: " + playerId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerStatsDto> getAllPlayerStats() {
        log.debug("Fetching all player stats");
        return playerStatsRepo.findAll().stream()
                .map(playerStatsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerStatsDto updatePlayerStats(Long id, PlayerStatsDto playerStatsDto) {
        log.debug("Updating player stats with ID: {}", id);
        
        // Validate player exists if being updated
        if (playerStatsDto.getPlayer() != null && playerStatsDto.getPlayer().getId() != null) {
            if (!playerRepo.existsById(playerStatsDto.getPlayer().getId())) {
                throw new ResourceNotFoundException("Player not found with id: " + playerStatsDto.getPlayer().getId());
            }
        }
        
        return playerStatsRepo.findById(id)
                .map(existingStats -> {
                    // Update the stats
                    if (playerStatsDto.getMatches() != null) {
                        existingStats.setMatches(playerStatsDto.getMatches());
                    }
                    if (playerStatsDto.getInnings() != null) {
                        existingStats.setInnings(playerStatsDto.getInnings());
                    }
                    if (playerStatsDto.getRuns() != null) {
                        existingStats.setRuns(playerStatsDto.getRuns());
                    }
                    if (playerStatsDto.getBallsFaced() != null) {
                        existingStats.setBallsFaced(playerStatsDto.getBallsFaced());
                    }
                    if (playerStatsDto.getFours() != null) {
                        existingStats.setFours(playerStatsDto.getFours());
                    }
                    if (playerStatsDto.getSixes() != null) {
                        existingStats.setSixes(playerStatsDto.getSixes());
                    }
                    if (playerStatsDto.getHighestScore() != null) {
                        existingStats.setHighestScore(playerStatsDto.getHighestScore());
                    }
                    if (playerStatsDto.getBattingAverage() != null) {
                        existingStats.setBattingAverage(playerStatsDto.getBattingAverage());
                    }
                    if (playerStatsDto.getBattingStrikeRate() != null) {
                        existingStats.setBattingStrikeRate(playerStatsDto.getBattingStrikeRate());
                    }
                    if (playerStatsDto.getFifties() != null) {
                        existingStats.setFifties(playerStatsDto.getFifties());
                    }
                    if (playerStatsDto.getHundreds() != null) {
                        existingStats.setHundreds(playerStatsDto.getHundreds());
                    }
                    if (playerStatsDto.getOversBowled() != null) {
                        existingStats.setOversBowled(playerStatsDto.getOversBowled());
                    }
                    if (playerStatsDto.getMaidens() != null) {
                        existingStats.setMaidens(playerStatsDto.getMaidens());
                    }
                    if (playerStatsDto.getRunsConceded() != null) {
                        existingStats.setRunsConceded(playerStatsDto.getRunsConceded());
                    }
                    if (playerStatsDto.getWickets() != null) {
                        existingStats.setWickets(playerStatsDto.getWickets());
                    }
                    if (playerStatsDto.getBestBowlingInnings() != null) {
                        existingStats.setBestBowlingInnings(playerStatsDto.getBestBowlingInnings());
                    }
                    if (playerStatsDto.getBestBowlingMatch() != null) {
                        existingStats.setBestBowlingMatch(playerStatsDto.getBestBowlingMatch());
                    }
                    if (playerStatsDto.getBowlingAverage() != null) {
                        existingStats.setBowlingAverage(playerStatsDto.getBowlingAverage());
                    }
                    if (playerStatsDto.getBowlingEconomy() != null) {
                        existingStats.setBowlingEconomy(playerStatsDto.getBowlingEconomy());
                    }
                    if (playerStatsDto.getBowlingStrikeRate() != null) {
                        existingStats.setBowlingStrikeRate(playerStatsDto.getBowlingStrikeRate());
                    }
                    if (playerStatsDto.getFourWickets() != null) {
                        existingStats.setFourWickets(playerStatsDto.getFourWickets());
                    }
                    if (playerStatsDto.getFiveWickets() != null) {
                        existingStats.setFiveWickets(playerStatsDto.getFiveWickets());
                    }
                    
                    PlayerStats updatedStats = playerStatsRepo.save(existingStats);
                    log.info("Updated player stats with ID: {}", id);
                    return playerStatsMapper.toDto(updatedStats);
                })
                .orElseThrow(() -> {
                    log.warn("Player stats not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Player stats not found with id: " + id);
                });
    }

    @Override
    public void deletePlayerStats(Long id) {
        log.debug("Deleting player stats with ID: {}", id);
        
        if (!playerStatsRepo.existsById(id)) {
            log.warn("Player stats not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("Player stats not found with id: " + id);
        }
        
        try {
            playerStatsRepo.deleteById(id);
            log.info("Deleted player stats with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting player stats with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete player stats with ID: " + id, e);
        }
    }
}
