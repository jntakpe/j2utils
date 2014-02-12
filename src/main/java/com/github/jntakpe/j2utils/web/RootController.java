package com.github.jntakpe.j2utils.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contrôleur appelé au démarrage de l'application
 *
 * @author jntakpe
 */
@Controller
public class RootController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }
}
