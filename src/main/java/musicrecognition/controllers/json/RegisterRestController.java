package musicrecognition.controllers.json;

import musicrecognition.dto.UserDto;
import musicrecognition.entities.User;
import musicrecognition.exceptions.NoContentException;
import musicrecognition.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/rest/register")
public class RegisterRestController {
    @Autowired
    UserService userService;
    
    public RegisterRestController() {}
    
    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody UserDto userDto) {
        if (userDto == null || userDto.getUsername().isEmpty() ||
                userDto.getPassword().isEmpty() || userDto.getEmail().isEmpty())
            throw new NoContentException();
        
        User userEntity = userService.dtoToEntity(userDto);
        Integer id = userService.insert(userEntity);
        
        if (id == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity(HttpStatus.CREATED);
    }
}
