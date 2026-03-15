package org.foodhub.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.foodhub.restaurant.model.food.Food;
import org.foodhub.restaurant.service.RestaurantFoodService;

/**
 * <p>
 * Manages restaurant food related operations and is responsible for receiving input through a REST API and processing it.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@RestController
@RequestMapping("/restaurant/food")
public final class RestaurantFoodController {

    @Autowired
    private RestaurantFoodService restaurantFoodService;

    /**
     * <p>
     * Adds food to the restaurant.
     * </p>
     *
     * @param food         Represents the food item to be added.
     * @param restaurantId Represents the ID of the restaurant.
     * @return A byte array containing the JSON response.
     */
    @PostMapping("/{restaurantId}")
    public byte[] addFood(@RequestBody final Food food, @PathVariable final long restaurantId) {
        return restaurantFoodService.addFood(food, restaurantId);
    }

    /**
     * <p>
     * Removes the food from the restaurant.
     * </p>
     *
     * @param foodId Represents the ID of the food to be removed.
     * @return A byte array containing the JSON response.
     */
    @DeleteMapping("/{foodId}")
    public byte[] removeFood(@PathVariable final long foodId) {
        return restaurantFoodService.removeFood(foodId);
    }

    /**
     * <p>
     * Retrieves the available quantity of the chosen food.
     * </p>
     *
     * @param foodId Represents the ID of the food.
     * @return A byte array containing the JSON response.
     */
    @GetMapping("/{foodId}")
    public byte[] getFoodQuantity(@PathVariable final long foodId) {
        return restaurantFoodService.getFoodQuantity(foodId);
    }

    /**
     * <p>
     * Retrieves the menu card from the restaurant.
     * </p>
     *
     * @param restaurantId Represents the ID of the restaurant.
     * @param menucardId   Represents the ID of the menu card.
     * @return A byte array containing the JSON response.
     */
    @GetMapping("/{restaurantId}/{foodTypeId}")
    public byte[] getMenuCard(@PathVariable final long restaurantId,
                              @PathVariable final int foodTypeId) {
        return restaurantFoodService.getMenuCard(restaurantId, foodTypeId);
    }
}