package taxi.controller;

import taxi.domain.Client;
import taxi.exception.ClientException;
import taxi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;

/**
 * Created by Вадим on 28.02.2016.
 */

@Controller
public class CreateClientServlet extends HttpServlet {
    @Autowired
    private ClientService service;

    @RequestMapping(value = "/client/add")
    public String orderAdd(Model model) {
        model.addAttribute("client", new Client());
        return "registerClient";
    }

    @RequestMapping(value = "/registerClient", method = RequestMethod.POST)
    public String clientRegister(Model model, @ModelAttribute("client") Client client, Errors errors) {
        String msg = "";
        try {
            if (service.createClient(client.getName(), client.getSurname(), client.getPhone(), client.getAddress())) {
                msg = "New client added successfully";
            }
        } catch (ClientException e) {
            msg = e.getMessage();
        }
        model.addAttribute("msg", msg);
        return "registerClient";
    }
}
