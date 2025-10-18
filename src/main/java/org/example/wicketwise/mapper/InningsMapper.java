package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.InningsDto;
import org.example.wicketwise.entity.Innings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InningsMapper {
    
    private final ModelMapper modelMapper;
    private final MatchMapper matchMapper;
    private final TeamMapper teamMapper;
    
    public InningsDto toDto(Innings innings) {
        if (innings == null) {
            return null;
        }
        return modelMapper.map(innings, InningsDto.class);
    }
    
    public Innings toEntity(InningsDto inningsDto) {
        if (inningsDto == null) {
            return null;
        }
        return modelMapper.map(inningsDto, Innings.class);
    }
    
    public void updateInningsFromDto(InningsDto inningsDto, Innings innings) {
        if (inningsDto == null || innings == null) {
            return;
        }
        modelMapper.map(inningsDto, innings);
    }
}
