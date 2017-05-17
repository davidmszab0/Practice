package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by grace on 15/02/17.
 */
@Controller
@RequestMapping({"/", "/homepage"})
public class HomeController {

    //@RequestMapping(value="/", method=GET)
    @RequestMapping(method=GET)
    public String home() {
        return "home";
    }
}
