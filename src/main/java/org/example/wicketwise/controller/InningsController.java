package org.example.wicketwise.controller;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.InningsDto;
import org.example.wicketwise.service.InningsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/innings")
@RequiredArgsConstructor
public class InningsController {

    private final InningsService inningsService;

    @PostMapping
    public ResponseEntity<InningsDto> createInnings(@RequestBody InningsDto inningsDto) {
        InningsDto createdInnings = inningsService.createInnings(inningsDto);
        return new ResponseEntity<>(createdInnings, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InningsDto> getInningsById(@PathVariable Long id) {
        InningsDto inningsDto = inningsService.getInningsById(id);
        return ResponseEntity.ok(inningsDto);
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<InningsDto>> getInningsByMatch(@PathVariable Long matchId) {
        List<InningsDto> inningsList = inningsService.getInningsByMatch(matchId);
        return ResponseEntity.ok(inningsList);
    }
}
