package org.example.wicketwise.controller;

import jakarta.validation.Valid;
import org.example.wicketwise.dto.MatchDto;
import org.example.wicketwise.service.MatchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<MatchDto> createMatch(@Valid @RequestBody MatchDto matchDto) {
        MatchDto createdMatch = matchService.createMatch(matchDto);
        return new ResponseEntity<>(createdMatch, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @GetMapping
    public ResponseEntity<List<MatchDto>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MatchDto>> getUpcomingMatches() {
        return ResponseEntity.ok(matchService.getUpcomingMatches());
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<MatchDto>> getMatchesByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(matchService.getMatchesByTeam(teamId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<MatchDto>> getMatchesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(matchService.getMatchesByDateRange(startDate, endDate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDto> updateMatch(
            @PathVariable Long id,
            @Valid @RequestBody MatchDto matchDto) {
        return ResponseEntity.ok(matchService.updateMatch(id, matchDto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MatchDto> updateMatchStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(matchService.updateMatchStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
