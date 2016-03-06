package taxi.controller;

import taxi.domain.Client;
import taxi.domain.Order;
import taxi.service.ClientService;
import taxi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

/**
 * Created by v.davidenko on 03.03.2016.
 */

@Controller
@SuppressWarnings("unchecked")
public class OrderEditServlet {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/order/edit/{id}")
    public String orderEdit(Model model, @PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        if (order == null) {
            model.addAttribute("msg", "No orders found!");
            return "dashboard";
        }
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        model.addAttribute("order", order);

        return "orderEdit";
    }

    @RequestMapping(value = "/order/edit/save", method = RequestMethod.POST)
    public String orderSave(Model model, @ModelAttribute("order") Order order, Errors errors){
        Date orderDate = orderService.findOrderById(order.getId()).getOrderDate();
        Double amount = orderService.findOrderById(order.getId()).getAmount();
        Client client = clientService.findClientById(order.getClient().getId());

        orderService.editOrder(order.getId(), client, orderDate, String.valueOf(order.getAmount()),
                order.getAddressFrom(), order.getAddressTo());

        Double newAmount = client.getAmount() - amount + order.getAmount();
        client.setAmount(newAmount);
        clientService.updateClient(client);
        String msg = "Order data saved successfully";

        List<Client> clients = clientService.findAllClients();
        model.addAttribute("order", order);
        model.addAttribute("clients", clients);
        model.addAttribute("msg", msg);

        return "orderEdit";
    }

}
