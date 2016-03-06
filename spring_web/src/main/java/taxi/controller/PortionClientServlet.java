package taxi.controller;

import taxi.domain.Client;
import taxi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */

@Controller
@SuppressWarnings("unchecked")
public class PortionClientServlet {
    @Autowired
    private ClientService clientService;

    private final int PORTION_SIZE = 10;

    @RequestMapping(value = "/clients/ByPortion")
    public String showByPortion(Model model) {
        List<Client> clients = (List<Client>) clientService.showClientsByPortion(PORTION_SIZE);
        model.addAttribute("clientList", clients);
        model.addAttribute("paging", true);
        return "clients";
    }

    @RequestMapping(value = "/clients/reportByPortion/{direction}")
    public String reportPaging(Model model, @PathVariable int direction) {
        List<Client> clients = (List<Client>) clientService.showClientsByPortion(PORTION_SIZE * direction);
        model.addAttribute("clientList", clients);
        return "clients";
    }

    @RequestMapping(value = "/clients/reportBack")
    public String reportBack() {
        clientService.showClientsByPortion(0);
        return "dashboard";
    }
}
