package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wicketwise.dto.InningsDto;
import org.example.wicketwise.entity.Innings;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.InningsMapper;
import org.example.wicketwise.mapper.MatchMapper;
import org.example.wicketwise.mapper.TeamMapper;
import org.example.wicketwise.repository.InningsRepo;
import org.example.wicketwise.repository.MatchRepo;
import org.example.wicketwise.repository.TeamRepo;
import org.example.wicketwise.service.InningsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InningsServiceIMPL implements InningsService {

    private final InningsRepo inningsRepo;
    private final MatchRepo matchRepo;
    private final TeamRepo teamRepo;
    private final InningsMapper inningsMapper;
    private final MatchMapper matchMapper;
    private final TeamMapper teamMapper;

    @Override
    public InningsDto createInnings(InningsDto inningsDto) {
        log.debug("Creating new innings");
        
        // Validate match exists
        if (!matchRepo.existsById(inningsDto.getMatch().getId())) {
            throw new ResourceNotFoundException("Match not found with id: " + inningsDto.getMatch().getId());
        }
        
        // Validate team exists
        if (!teamRepo.existsById(inningsDto.getBattingTeam().getId())) {
            throw new ResourceNotFoundException("Team not found with id: " + inningsDto.getBattingTeam().getId());
        }
        
        try {
            Innings innings = inningsMapper.toEntity(inningsDto);
            Innings savedInnings = inningsRepo.save(innings);
            log.info("Created new innings with ID: {}", savedInnings.getId());
            return inningsMapper.toDto(savedInnings);
        } catch (Exception e) {
            log.error("Error creating innings: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create innings", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public InningsDto getInningsById(Long id) {
        log.debug("Fetching innings with ID: {}", id);
        return inningsRepo.findById(id)
                .map(inningsMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Innings not found with ID: {}", id);
                    return new ResourceNotFoundException("Innings not found with id: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<InningsDto> getInningsByMatch(Long matchId) {
        log.debug("Fetching all innings for match ID: {}", matchId);
        
        if (!matchRepo.existsById(matchId)) {
            throw new ResourceNotFoundException("Match not found with id: " + matchId);
        }
        
        return inningsRepo.findByMatchId(matchId).stream()
                .map(inningsMapper::toDto)
                .collect(Collectors.toList());
    }
}
