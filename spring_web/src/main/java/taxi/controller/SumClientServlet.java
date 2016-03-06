package taxi.controller;

import taxi.domain.Client;
import taxi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */

@Controller
@SuppressWarnings("unchecked")
public class SumClientServlet {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/clients/onSum")
    public String showOnSum(Model model, @RequestParam(required = true, defaultValue = "0") int sum) {
        List<Client> clients = (List<Client>) clientService.showClientsGtSum(sum);
        model.addAttribute("clientList", clients);
        model.addAttribute("paging", false);
        return "clients";
    }
}
