package com.wiseowl.bookstore.service;

import com.wiseowl.bookstore.model.CartItem;
import com.wiseowl.bookstore.model.Book;
import com.wiseowl.bookstore.repository.BookRepository;
import com.wiseowl.bookstore.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    private final BookRepository bookRepository;

    public List<CartItem> getCartItemsForUser(long userId) {
        // Fetch cart items for the user
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        // fetching book details and adding them to the cart items
        for (CartItem cartItem : cartItems) {
            // Fetch book details by bookId and add it to cart
            Book book = bookRepository.findById(cartItem.getBookId()).orElse(null);
            cartItem.setBook(book);
        }
        return cartItems;
    }

    public void updateCartItemQuantity(long userId, int bookId, String action) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findByUserIdAndBookId(userId, bookId);

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();

            int currentQty = cartItem.getBookQty();

            if (action.equals("inc")) {
                cartItem.setBookQty(currentQty + 1);
            } else if (action.equals("dec")) {
                if (currentQty > 1) {
                    cartItem.setBookQty(currentQty - 1);
                } else {
                    // Option 1: Delete item if qty is 1 and user clicks "dec"
                    cartItemRepository.delete(cartItem);
                    return;
                }
            }
            cartItemRepository.save(cartItem);
        }
    }

    public void addToCart(long userId, int bookId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findByUserIdAndBookId(userId, bookId);

        if (optionalCartItem.isPresent()) {
            // If the item already exists in the cart, increment the quantity
            CartItem cartItem = optionalCartItem.get();
            cartItem.setBookQty(cartItem.getBookQty() + 1);
            cartItemRepository.save(cartItem);
        } else {
            // If the item does not exist in the cart, create a new CartItem
            CartItem newCartItem = new CartItem();
            newCartItem.setUserId(userId);
            newCartItem.setBookId(bookId);
            newCartItem.setBookQty(1); // Set initial quantity to 1
            cartItemRepository.save(newCartItem);
        }
    }
}
