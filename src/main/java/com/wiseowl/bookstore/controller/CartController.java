package com.wiseowl.bookstore.controller;

import com.wiseowl.bookstore.entity.CartItem;
import com.wiseowl.bookstore.entity.User;
import com.wiseowl.bookstore.repository.UserRepository;
import com.wiseowl.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    @GetMapping("/cart")
    public String showCart(Model model) {
        long userId = getLoggedInUserId();
        List<CartItem> cartItems = cartService.getCartItemsForUser(userId);

        // Get all cart items for the user
        model.addAttribute("cartItems", cartItems);

        // Calculate the total price of all items in the cart
        double cartTotal = 0;
        for (CartItem cartItem : cartItems) {
            cartTotal += cartItem.getTotal();
        }

        model.addAttribute("cartTotal", cartTotal);

        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("bookId") int bookId, RedirectAttributes redirectAttributes) {
        long userId = getLoggedInUserId();
        cartService.addToCart(userId, bookId);

        // Flash message
        redirectAttributes.addFlashAttribute("successMsg", "Book added to cart successfully!");

        // Redirect back to /books page
        return "redirect:/books";
    }


    @GetMapping("/cart/updateQty")
    public String updateQty(@RequestParam int id,
                            @RequestParam String action) {
        long userId = getLoggedInUserId();
        cartService.updateCartItemQuantity(userId, id, action);
        return "redirect:/cart";
    }

//    @PostMapping("/order-now")
//    public String orderNow(@RequestParam("bookId") int bookId,
//                           RedirectAttributes redirectAttributes) {
//        long userId = getLoggedInUserId();
//        orderService.createOrder(userId, bookId);
//
//        redirectAttributes.addFlashAttribute("successMessage", "Order placed successfully!");
//
//        // Redirect to orders or confirmation page (your choice)
//        return "redirect:/orders";
//    }


    private long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();

            // lookup User entity by email
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return user.getId();
        }
        return -1;  // Return an invalid ID or handle the case of unauthenticated user
    }


}
