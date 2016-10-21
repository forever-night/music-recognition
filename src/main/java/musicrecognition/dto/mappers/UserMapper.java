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
        
        if (userDto.getPassword() != null)
            user.setPassword(userDto.getPassword());
        
        if (userDto.getCreatedAt() != null)
            user.setCreatedAt(userDto.getCreatedAt());
    
        if (userDto.getRole() != null)
            user.setRole(userDto.getRole());
        else
            user.setRole(null);
    
        if (userDto.getEnabled() != null)
            user.setEnabled(userDto.getEnabled());
        else
            user.setEnabled(null);
        
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
