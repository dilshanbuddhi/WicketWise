package org.example.wicketwise.service;

import org.example.wicketwise.dto.PlayerStatsDto;

import java.util.List;

public interface PlayerStatsService {
    PlayerStatsDto createPlayerStats(PlayerStatsDto playerStatsDto);
    PlayerStatsDto getPlayerStatsById(Long id);
    PlayerStatsDto getPlayerStatsByPlayer(Long playerId);
    List<PlayerStatsDto> getAllPlayerStats();
    PlayerStatsDto updatePlayerStats(Long id, PlayerStatsDto playerStatsDto);
    void deletePlayerStats(Long id);
}
