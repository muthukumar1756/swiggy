package org.foodhub.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.foodhub.restaurant.model.restaurant.Restaurant;
import org.foodhub.restaurant.model.restaurant.RestaurantLoginDetails;
import org.foodhub.restaurant.model.restaurant.RestaurantProfileUpdateDetails;
import org.foodhub.restaurant.service.RestaurantProfileService;

/**
 * <p>
 * Manages restaurant profile related operations and is responsible for receiving input through a REST API and processing it.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@RestController
@RequestMapping("/restaurant")
public final class RestaurantProfileController {

    @Autowired
    private RestaurantProfileService restaurantProfileService;

    /**
     * <p>
     * Creates the new restaurant profile.
     * </p>
     *
     * @param restaurant represents the restaurant
     * @return A byte array containing the JSON response.
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public byte[] createRestaurantProfile(@RequestBody final Restaurant restaurant) {
        return restaurantProfileService.createRestaurantProfile(restaurant);
    }

    /**
     * <p>
     * Gets the restaurant if the id matches.
     * </p>
     *
     * @param restaurantId Represents the id of the restaurant
     * @return A byte array containing the JSON response.
     */
    @GetMapping(value = "/{restaurantId}", produces = "application/json")
    public byte[] getRestaurantById(@PathVariable final long restaurantId) {
        return restaurantProfileService.getRestaurantById(restaurantId);
    }

    /**
     * <p>
     * Retrieves the restaurant profile if the phone_number and password matches.
     * </p>
     *
     * @param restaurantLoginDetails Represents the instance of restaurant login details
     * @return A byte array containing the JSON response.
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public byte[] getRestaurant(@RequestBody final RestaurantLoginDetails restaurantLoginDetails) {
        return restaurantProfileService.getRestaurant(restaurantLoginDetails);
    }

    /**
     * <p>
     * Updates the profile of the restaurant.
     * </p>
     *
     * @param restaurantProfileUpdateDetails Represents the instance of restaurant profile update details
     * @return A byte array containing the JSON response.
     */
    @PutMapping(consumes = "application/json", produces = "application/json")
    public byte[] updateRestaurantProfile(@RequestBody final RestaurantProfileUpdateDetails restaurantProfileUpdateDetails) {
        return restaurantProfileService.updateRestaurantData(restaurantProfileUpdateDetails);
    }

    /**
     * <p>
     * Gets all the restaurants.
     * </p>
     *
     * @return A byte array containing the JSON response.
     */
    @GetMapping(produces = "application/json")
    public byte[] getAllRestaurants() {
        return restaurantProfileService.getAllRestaurants();
    }
}