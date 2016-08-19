package musicrecognition.services.interfaces;

import org.springframework.security.core.userdetails.User;


public interface UserService {
    Integer insert(musicrecognition.entities.User user);
    
    User getSpringUserByUsername(String username);
    
    musicrecognition.entities.User dtoToEntity(musicrecognition.dto.User userDto);
}
