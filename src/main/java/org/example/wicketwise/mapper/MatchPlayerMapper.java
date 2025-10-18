package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.MatchPlayerDto;
import org.example.wicketwise.entity.MatchPlayer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchPlayerMapper {
    
    private final ModelMapper modelMapper;
    private final MatchMapper matchMapper;
    private final TeamMapper teamMapper;
    private final PlayerMapper playerMapper;
    
    public MatchPlayerDto toDto(MatchPlayer matchPlayer) {
        if (matchPlayer == null) {
            return null;
        }
        return modelMapper.map(matchPlayer, MatchPlayerDto.class);
    }
    
    public MatchPlayer toEntity(MatchPlayerDto matchPlayerDto) {
        if (matchPlayerDto == null) {
            return null;
        }
        return modelMapper.map(matchPlayerDto, MatchPlayer.class);
    }
    
    public void updateMatchPlayerFromDto(MatchPlayerDto matchPlayerDto, MatchPlayer matchPlayer) {
        if (matchPlayerDto == null || matchPlayer == null) {
            return;
        }
        modelMapper.map(matchPlayerDto, matchPlayer);
    }
}
