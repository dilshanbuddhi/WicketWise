package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.PlayerDto;
import org.example.wicketwise.entity.Player;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.PlayerMapper;
import org.example.wicketwise.repository.PlayerRepository;
import org.example.wicketwise.service.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceIMPL implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public PlayerDto createPlayer(PlayerDto playerDto) {
        Player player = playerMapper.toEntity(playerDto);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDto(savedPlayer);
    }

    @Override
    public PlayerDto getPlayerById(Long id) {
        return playerRepository.findById(id)
                .map(playerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerDto> getPlayersByTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId).stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerDto> getPlayersByRole(String role) {
        return playerRepository.findByRole(role).stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDto updatePlayer(Long id, PlayerDto playerDto) {
        return playerRepository.findById(id)
                .map(existingPlayer -> {
                    playerMapper.updatePlayerFromDto(playerDto, existingPlayer);
                    Player updatedPlayer = playerRepository.save(existingPlayer);
                    return playerMapper.toDto(updatedPlayer);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
    }

    @Override
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Player not found with id: " + id);
        }
        playerRepository.deleteById(id);
    }

    @Override
    public List<PlayerDto> searchPlayers(String query) {
        // Implement search logic based on your requirements
      /*  return playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query).stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());*/

        return null;
    }

    @Override
    public long countPlayers() {
        return playerRepository.count();
    }
}
