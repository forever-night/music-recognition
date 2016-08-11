package musicrecognition.controllers.view;

import musicrecognition.util.Global;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/add")
public class AddController {
    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "add";
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "status")
    public String getStatus(@RequestParam Integer status, Model model) {
        Global.Message message;
        
        switch (status) {
            case 201:
                message = Global.Message.SUCCESS;
                break;
            case 204:
                message = Global.Message.FIELD_EMPTY;
                break;
            case 415:
                message = Global.Message.UNSUPPORTED_TYPE;
                break;
            case 500:
                message = Global.Message.INTERNAL_ERROR;
                break;
            default:
                message = Global.Message.ERROR;
        }
        
        
        if (message == Global.Message.ERROR)
            model.addAttribute("message", message.getMessage() + " " + status);
        else
            model.addAttribute("message", message.getMessage());
        
        return "addStatus";
    }
}
