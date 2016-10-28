package musicrecognition.controllers.json;

import musicrecognition.dto.UserDto;
import musicrecognition.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/rest/user")
public class UserRestController {
    @Autowired
    UserService userService;
    
    public UserRestController() {}
    
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll() {
        return userService.getAll();
    }
}
