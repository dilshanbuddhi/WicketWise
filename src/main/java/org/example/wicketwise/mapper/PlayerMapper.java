package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.PlayerDto;
import org.example.wicketwise.entity.Player;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    
    private final ModelMapper modelMapper;
    
    public PlayerDto toDto(Player player) {
        if (player == null) {
            return null;
        }
        return modelMapper.map(player, PlayerDto.class);
    }
    
    public Player toEntity(PlayerDto playerDto) {
        if (playerDto == null) {
            return null;
        }
        return modelMapper.map(playerDto, Player.class);
    }
    
    public void updatePlayerFromDto(PlayerDto playerDto, Player player) {
        if (playerDto == null || player == null) {
            return;
        }
        modelMapper.map(playerDto, player);
    }
}
