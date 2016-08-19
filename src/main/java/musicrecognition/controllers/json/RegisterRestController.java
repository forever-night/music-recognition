package musicrecognition.controllers.json;

import musicrecognition.dto.User;
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
    
//    for tests
    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody User userDto) {
        if (userDto == null || userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        
        musicrecognition.entities.User userEntity = userService.dtoToEntity(userDto);
        Integer id = userService.insert(userEntity);
        
        if (id > 0)
            return new ResponseEntity(HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
