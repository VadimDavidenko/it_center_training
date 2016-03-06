package taxi.controller;

import taxi.domain.User;
import taxi.service.AuthorizationService;
import taxi.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Вадим on 28.02.2016.
 */

@Controller
public class RegisterServlet {
    @Autowired
    private AuthorizationService service;

    @RequestMapping(value = "/user/add")
    public String orderAdd(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String userRegister(Model model, @ModelAttribute("user") User user, Errors errors) {
        String msg = "";
        try {
            if (service.register(user.getLogin(), user.getIdNumber(), user.getPassword())) {
                msg = "New user added successfully";
            }
        } catch (AuthenticationException e) {
            msg = e.getMessage();
        }
        model.addAttribute("msg", msg);
        return "register";
    }
}
