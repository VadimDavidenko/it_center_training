package taxi.controller;

import taxi.domain.Client;
import taxi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */

@Controller
@SuppressWarnings("unchecked")
public class MonthClientServlet {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/clients/ByLastMonth")
    public String showByPortion(Model model) {
        List<Client> clients = (List<Client>) clientService.showClientsLastMonth();
        model.addAttribute("clientList", clients);
        model.addAttribute("paging", false);
        return "clients";
    }
}
