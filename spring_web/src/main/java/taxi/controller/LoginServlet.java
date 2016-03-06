package taxi.controller;

import taxi.domain.User;
import taxi.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Вадим on 03.03.2016.
 */

@Controller
public class LoginServlet {
    @Autowired
    private AuthorizationService service;

    @RequestMapping(value = "/index")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(Model model, @ModelAttribute("user") User user, Errors errors) {
        if (user.getLogin().equals("admin") && user.getPassword().equals("taxi")) {
            return "dashboard";
        }
        if (service.isRegistered(user.getLogin(), user.getPassword())) {
            return "dashboard";
        } else {
            model.addAttribute("msg", "Wrong login or password!");
            model.addAttribute("user", new User());
            return "login";
        }
    }
}
