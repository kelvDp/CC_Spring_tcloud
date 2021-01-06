package tacos.starter_jpa.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.starter_jpa.Order;
import tacos.starter_jpa.User;
import tacos.starter_jpa.data.OrderRepo;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepo orderRepo;

    @Autowired
    public OrderController(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(@ModelAttribute Order order, @AuthenticationPrincipal User user) {

        if (order.getPersonName() == null) {
            order.setPersonName(user.getFullname());
        }

        if (order.getCity() == null) {
            order.setCity(user.getCity());
        }

        if (order.getState() == null) {
            order.setState(user.getState());
        }

        if (order.getStreet() == null) {
            order.setStreet(user.getStreet());
        }

        if (order.getZip() == null) {
            order.setZip(user.getZip());
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrders(@Valid Order orders, Errors errors, SessionStatus sessionStatus,
            @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        orders.setUser(user);

        orderRepo.save(orders);

        sessionStatus.setComplete();

        return "redirect:/";
    }
}
