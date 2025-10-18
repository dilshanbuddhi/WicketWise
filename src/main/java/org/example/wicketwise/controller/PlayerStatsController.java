package org.example.wicketwise.controller;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.PlayerStatsDto;
import org.example.wicketwise.service.PlayerStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player-stats")
@RequiredArgsConstructor
public class PlayerStatsController {

    private final PlayerStatsService playerStatsService;

    @PostMapping
    public ResponseEntity<PlayerStatsDto> createPlayerStats(@RequestBody PlayerStatsDto playerStatsDto) {
        PlayerStatsDto createdPlayerStats = playerStatsService.createPlayerStats(playerStatsDto);
        return new ResponseEntity<>(createdPlayerStats, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerStatsDto> getPlayerStatsById(@PathVariable Long id) {
        PlayerStatsDto playerStatsDto = playerStatsService.getPlayerStatsById(id);
        return ResponseEntity.ok(playerStatsDto);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<PlayerStatsDto> getPlayerStatsByPlayer(@PathVariable Long playerId) {
        PlayerStatsDto playerStatsDto = playerStatsService.getPlayerStatsByPlayer(playerId);
        return ResponseEntity.ok(playerStatsDto);
    }

    @GetMapping
    public ResponseEntity<List<PlayerStatsDto>> getAllPlayerStats() {
        List<PlayerStatsDto> playerStatsList = playerStatsService.getAllPlayerStats();
        return ResponseEntity.ok(playerStatsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerStatsDto> updatePlayerStats(
            @PathVariable Long id,
            @RequestBody PlayerStatsDto playerStatsDto) {
        PlayerStatsDto updatedPlayerStats = playerStatsService.updatePlayerStats(id, playerStatsDto);
        return ResponseEntity.ok(updatedPlayerStats);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerStats(@PathVariable Long id) {
        playerStatsService.deletePlayerStats(id);
        return ResponseEntity.noContent().build();
    }
}
