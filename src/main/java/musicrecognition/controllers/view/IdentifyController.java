package musicrecognition.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/identify")
public class IdentifyController {
    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "identify";
    }
}