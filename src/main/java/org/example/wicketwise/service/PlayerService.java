package org.example.wicketwise.service;

import org.example.wicketwise.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    PlayerDto createPlayer(PlayerDto playerDto);
    PlayerDto getPlayerById(Long id);
    List<PlayerDto> getAllPlayers();
    List<PlayerDto> getPlayersByTeam(Long teamId);
    List<PlayerDto> getPlayersByRole(String role);
    PlayerDto updatePlayer(Long id, PlayerDto playerDto);
    void deletePlayer(Long id);
    List<PlayerDto> searchPlayers(String query);
    long countPlayers();
}
