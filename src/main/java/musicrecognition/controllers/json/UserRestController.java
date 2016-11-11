package musicrecognition.controllers.json;

import musicrecognition.dto.UserDto;
import musicrecognition.dto.mappers.UserMapper;
import musicrecognition.exceptions.NoContentException;
import musicrecognition.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/rest/user")
public class UserRestController {
    @Autowired
    UserService userService;
    
    @Autowired
    UserMapper userMapper;
    
    public UserRestController() {}
    
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll() {
        return userService.getAll();
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity put(@RequestBody UserDto userDto) {
        if (userDto == null || userDto.getUsername() == null || userDto.getUsername().isEmpty() ||
                userDto.getRole() == null || userDto.getEnabled() == null)
            throw new NoContentException();
                
        UserDto newUserDto = userService.update(userDto);
        
        if (newUserDto == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity(newUserDto, HttpStatus.OK);
    }
}
