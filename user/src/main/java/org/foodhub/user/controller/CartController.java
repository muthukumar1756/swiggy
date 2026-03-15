package org.foodhub.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.foodhub.user.model.cart.Cart;
import org.foodhub.user.service.CartService;

/**
 * <p>
 * Manages user cart-related operations and is responsible for receiving user input through a REST API and processing it.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@RestController
@RequestMapping("/cart")
public final class CartController {

    @Autowired
    private CartService cartService;

    /**
     * <p>
     * Adds the selected food to the user cart.
     * </p>
     *
     * @param cart Represents the cart of the user
     * @return byte array of json object
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public byte[] addFood(@RequestBody final Cart cart) {
        return cartService.addFood(cart);
    }

    /**
     * <p>
     * Gets the cart of the user.
     * </p>
     *
     * @param userId Represents the id of the user
     * @return byte array of json object
     */
    @GetMapping(value = "/{userId}", produces = "application/json")
    public byte[] getCart(@PathVariable final long userId) {
        return cartService.getCart(userId);
    }

    /**
     * <p>
     * Removes the food selected by the user.
     * </p>
     *
     * @param cartId Represents the id of the user cart
     * @return byte array of json object
     */
    @DeleteMapping(value = "/{cartId}", produces = "application/json")
    public byte[] removeFood(@PathVariable final long cartId) {
        return cartService.removeFood(cartId);
    }

    /**
     * <p>
     * Remove all the foods from the user cart.
     * </p>
     *
     * @param userId Represents the id of the user
     * @return byte array of json object
     */
    @DeleteMapping(value = "/clear/{userId}", produces = "application/json")
    public byte[] clearCart(@PathVariable final long userId) {
        return cartService.clearCart(userId);
    }
}