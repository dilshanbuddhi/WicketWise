package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.PlayerStatsDto;
import org.example.wicketwise.entity.PlayerStats;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerStatsMapper {
    
    private final ModelMapper modelMapper;
    private final PlayerMapper playerMapper;
    
    public PlayerStatsDto toDto(PlayerStats playerStats) {
        if (playerStats == null) {
            return null;
        }
        return modelMapper.map(playerStats, PlayerStatsDto.class);
    }
    
    public PlayerStats toEntity(PlayerStatsDto playerStatsDto) {
        if (playerStatsDto == null) {
            return null;
        }
        return modelMapper.map(playerStatsDto, PlayerStats.class);
    }
    
    public void updatePlayerStatsFromDto(PlayerStatsDto playerStatsDto, PlayerStats playerStats) {
        if (playerStatsDto == null || playerStats == null) {
            return;
        }
        modelMapper.map(playerStatsDto, playerStats);
    }
}
