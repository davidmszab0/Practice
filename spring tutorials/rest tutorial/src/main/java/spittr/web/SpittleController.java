package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spittr.data.SpittleRepository;

/**
 * Created by grace on 15/02/17.
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController( SpittleRepository spittleRepository) { // Inject SpittleRepository
        this.spittleRepository = spittleRepository;
    }

    /*
    * The Model is essentially a map (that is, a collection of key-value pairs)
    * that will be handed off to the view so that the data can be rendered to the client.
    * */
    @RequestMapping(method= RequestMethod.GET)
    public String spittles(Model model) {
        model.addAttribute("spittleList", //  name, Add spittles to model
                spittleRepository.findSpittles(Long.MAX_VALUE, 20));

        return "spittles"; // Return view name
    }
    /* or an alternative solution
     @RequestMapping(method=RequestMethod.GET)
        public List<Spittle> spittles() {
          return spittleRepository.findSpittles(Long.MAX_VALUE, 20));
      }
     */
}
