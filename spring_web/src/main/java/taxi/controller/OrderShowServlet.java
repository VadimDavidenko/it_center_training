package taxi.controller;

import taxi.domain.Order;
import taxi.service.OrderService;
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
public class OrderShowServlet {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/orders/onSum")
    public String showOnSum(Model model, @RequestParam(required = true, defaultValue = "0") int sumFrom,
                                @RequestParam(required = true, defaultValue = "0") int sumTo) {
        List<Order> orders = (List<Order>) orderService.showOrdersBetweenSumRange(sumFrom, sumTo);
        model.addAttribute("orderList", orders);
        model.addAttribute("paging", false);
        return "orders";
    }

    @RequestMapping(value = "/orders/reportBack")
    public String reportBack() {
        orderService.showOrdersByPortion(0);
        return "dashboard";
    }
}
