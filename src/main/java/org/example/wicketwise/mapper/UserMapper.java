package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.UserDto;
import org.example.wicketwise.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    
    private final ModelMapper modelMapper;
    
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }
    
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return modelMapper.map(userDto, User.class);
    }
    
    public void updateUserFromDto(UserDto userDto, User user) {
        if (userDto == null || user == null) {
            return;
        }
        modelMapper.map(userDto, user);
    }
}
