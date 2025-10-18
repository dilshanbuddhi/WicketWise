package org.example.wicketwise.controller;

import jakarta.validation.Valid;
import org.example.wicketwise.dto.TeamDto;
import org.example.wicketwise.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@Valid @RequestBody TeamDto teamDto) {
        TeamDto createdTeam = teamService.createTeam(teamDto);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TeamDto> getTeamByName(@PathVariable String name) {
        return ResponseEntity.ok(teamService.getTeamByName(name));
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TeamDto>> searchTeams(@RequestParam String query) {
        return ResponseEntity.ok(teamService.searchTeams(query));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
