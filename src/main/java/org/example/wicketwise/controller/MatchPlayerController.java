package org.example.wicketwise.controller;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.MatchPlayerDto;
import org.example.wicketwise.service.MatchPlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match-players")
@RequiredArgsConstructor
public class MatchPlayerController {

    private final MatchPlayerService matchPlayerService;

    @PostMapping
    public ResponseEntity<MatchPlayerDto> createMatchPlayer(@RequestBody MatchPlayerDto matchPlayerDto) {
        MatchPlayerDto createdMatchPlayer = matchPlayerService.createMatchPlayer(matchPlayerDto);
        return new ResponseEntity<>(createdMatchPlayer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchPlayerDto> getMatchPlayerById(@PathVariable Long id) {
        MatchPlayerDto matchPlayerDto = matchPlayerService.getMatchPlayerById(id);
        return ResponseEntity.ok(matchPlayerDto);
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<MatchPlayerDto>> getMatchPlayersByMatch(@PathVariable Long matchId) {
        List<MatchPlayerDto> matchPlayers = matchPlayerService.getMatchPlayersByMatch(matchId);
        return ResponseEntity.ok(matchPlayers);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<MatchPlayerDto>> getMatchPlayersByPlayer(@PathVariable Long playerId) {
        List<MatchPlayerDto> matchPlayers = matchPlayerService.getMatchPlayersByPlayer(playerId);
        return ResponseEntity.ok(matchPlayers);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<MatchPlayerDto>> getMatchPlayersByTeam(@PathVariable Long teamId) {
        List<MatchPlayerDto> matchPlayers = matchPlayerService.getMatchPlayersByTeam(teamId);
        return ResponseEntity.ok(matchPlayers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchPlayerDto> updateMatchPlayer(
            @PathVariable Long id,
            @RequestBody MatchPlayerDto matchPlayerDto) {
        MatchPlayerDto updatedMatchPlayer = matchPlayerService.updateMatchPlayer(id, matchPlayerDto);
        return ResponseEntity.ok(updatedMatchPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchPlayer(@PathVariable Long id) {
        matchPlayerService.deleteMatchPlayer(id);
        return ResponseEntity.noContent().build();
    }
}
