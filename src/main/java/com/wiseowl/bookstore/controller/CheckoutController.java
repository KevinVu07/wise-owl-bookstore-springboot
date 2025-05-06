package com.wiseowl.bookstore.controller;

import com.wiseowl.bookstore.entity.CartItem;
import com.wiseowl.bookstore.entity.Order;
import com.wiseowl.bookstore.entity.OrderItems;
import com.wiseowl.bookstore.entity.User;
import com.wiseowl.bookstore.repository.OrderItemRepository;
import com.wiseowl.bookstore.repository.UserRepository;
import com.wiseowl.bookstore.service.CartService;
import com.wiseowl.bookstore.service.OrderItemsService;
import com.wiseowl.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final OrderService orderService;
    private final OrderItemsService orderItemsService;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    @PostMapping("/checkout")
    public String createOrder(RedirectAttributes redirectAttributes) {
        User user = getCurrentUser();

        // Get items from the "current cart" (pending order)
        List<CartItem> cartItems = cartService.getCartItemsForUser(user.getId());

        // Create the order
        orderService.createOrder(user, cartItems);

        // Redirect back to checkout page to show the order summary
        return "redirect:/checkout";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        User user = getCurrentUser();
        Order currentOrder = orderItemsService.getOrCreateCurrentOrderForUser(user);
        Long orderId = currentOrder.getId();
        List<OrderItems> orderItems = currentOrder.getOrderItems();
        BigDecimal subTotal = calculateSubTotal(orderItems);
        BigDecimal shippingFee;
        BigDecimal taxRate = new BigDecimal("0.10"); // 10% gst

        // Free shipping threshold = $50
        BigDecimal freeShippingThreshold = new BigDecimal("50.00");
        BigDecimal standardShippingFee = new BigDecimal("7.00");

        if (subTotal.compareTo(freeShippingThreshold) >= 0) {
            shippingFee = BigDecimal.ZERO;
        } else {
            shippingFee = standardShippingFee;
        }

        BigDecimal tax = subTotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal orderTotal = subTotal.add(shippingFee).add(tax);

        model.addAttribute("orderId", orderId);
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("subTotal", subTotal);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("tax", tax);
        model.addAttribute("orderTotal", orderTotal);

        return "checkout";
    }

    private BigDecimal calculateSubTotal(List<OrderItems> orderItems) {
        return orderItems.stream()
                .map(item -> BigDecimal.valueOf(item.getOrderItemTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return user;
    }


}




