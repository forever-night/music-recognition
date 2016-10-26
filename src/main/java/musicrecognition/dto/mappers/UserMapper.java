package musicrecognition.dto.mappers;

import musicrecognition.dto.UserDto;
import musicrecognition.entities.User;
import org.springframework.stereotype.Service;


@Service
public class UserMapper {
    public User toEntity(UserDto userDto) {
        User user = new User();
        
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setRole(userDto.getRole());
        user.setEnabled(userDto.getEnabled());
        
        return user;
    }
    
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setEnabled(user.getEnabled());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setRole(user.getRole());
        
        return userDto;
    }
}
