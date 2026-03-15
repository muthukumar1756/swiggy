package org.foodhub.user.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.foodhub.user.model.order.Order;
import org.foodhub.user.service.OrderService;

/**
 * <p>
 * Manages order related operations and is responsible for receiving user input through a REST API and processing it.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@RestController
@RequestMapping("/order")
public final class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * <p>
     * places the user orders.
     * </p>
     *
     * @param orderList Represents the list of order items
     * @return byte array of json response
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public byte[] placeOrder(@RequestBody final Collection<Order> orderList) {
        return orderService.placeOrder(orderList);
    }

    /**
     * <p>
     * Gets the orders placed by the user.
     * </p>
     *
     * @param userId Represents the id of the user
     * @return byte array of json response
     */
    @GetMapping(value = "/{userId}", produces = "application/json")
    public byte[] getOrders(@PathVariable final long userId) {
        return orderService.getOrders(userId);
    }
}