package org.example.wicketwise.controller;

import jakarta.validation.Valid;
import org.example.wicketwise.dto.PlayerDto;
import org.example.wicketwise.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        PlayerDto createdPlayer = playerService.createPlayer(playerDto);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDto>> getPlayersByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(playerService.getPlayersByTeam(teamId));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<PlayerDto>> getPlayersByRole(@PathVariable String role) {
        return ResponseEntity.ok(playerService.getPlayersByRole(role));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlayerDto>> searchPlayers(@RequestParam String query) {
        return ResponseEntity.ok(playerService.searchPlayers(query));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(
            @PathVariable Long id,
            @Valid @RequestBody PlayerDto playerDto) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
