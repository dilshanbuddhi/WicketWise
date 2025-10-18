package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.MatchDto;
import org.example.wicketwise.entity.Match;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchMapper {
    
    private final ModelMapper modelMapper;
    private final TeamMapper teamMapper;
    
    public MatchDto toDto(Match match) {
        if (match == null) {
            return null;
        }
        return modelMapper.map(match, MatchDto.class);
    }
    
    public Match toEntity(MatchDto matchDto) {
        if (matchDto == null) {
            return null;
        }
        return modelMapper.map(matchDto, Match.class);
    }
    
    public void updateMatchFromDto(MatchDto matchDto, Match match) {
        if (matchDto == null || match == null) {
            return;
        }
        modelMapper.map(matchDto, match);
    }
}
