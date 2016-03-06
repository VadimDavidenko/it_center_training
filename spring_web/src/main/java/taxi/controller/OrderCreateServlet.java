package taxi.controller;

import taxi.domain.Order;
import taxi.exception.OrderException;
import taxi.service.ClientService;
import taxi.domain.Client;
import taxi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */

@Controller
@SuppressWarnings("unchecked")
public class OrderCreateServlet {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/order/add")
    public String orderAdd(Model model) {
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        model.addAttribute("order", new Order());
        return "orderAdd";
    }

    @RequestMapping(value = "/order/add/save", method = RequestMethod.POST)
    public String orderSave(Model model, @ModelAttribute("order") Order order, Errors errors){
        String msg = "";
        try {
            if (orderService.createOrder(order.getClient(), order.getAmount(),
                    order.getAddressFrom(), order.getAddressTo())) {

                Client client = clientService.findClientById(order.getClient().getId());
                client.setLastOrderDate(new Date());
                Double amount = (client.getAmount() != null) ? client.getAmount() + order.getAmount() : order.getAmount();
                client.setAmount(amount);
                clientService.updateClient(client);
                msg = "New order added successfully";
            }
        } catch (OrderException e) {
            msg = e.getMessage();
        }
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("order", order);
        model.addAttribute("clients", clients);
        model.addAttribute("msg", msg);

        return "orderAdd";
    }
}
