package org.foodhub.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.foodhub.user.model.address.Address;
import org.foodhub.user.model.user.User;
import org.foodhub.user.model.user.UserLoginDetails;
import org.foodhub.user.model.user.UserProfileUpdateDetails;
import org.foodhub.user.service.UserService;

/**
 * <p>
 * Handles the user related operation and responsible for processing user input through rest api
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public final class UserController {

    @Autowired
    private UserService userService;

    /**
     * <p>
     * Creates the new user.
     * </p>
     *
     * @param user Represents the user
     * @return byte array of json response
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public byte[] createUserProfile(@RequestBody final User user) {
        return userService.createUserProfile(user);
    }

    /**
     * <p>
     * Gets the user if the phone_number and password matches.
     * </p>
     *
     * @param userLoginDetails Represents the instance of user login dto
     * @return byte array of json response
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public byte[] getUser(@RequestBody final UserLoginDetails userLoginDetails) {
        return userService.getUser(userLoginDetails);
    }

    /**
     * <p>
     * Gets the user if the id matches.
     * </p>
     *
     * @param userId Represents the password of the current user
     * @return byte array of json response
     */
    @GetMapping(value = "/{userId}", produces = "application/json")
    public byte[] getUserById(@PathVariable final long userId) {
        return userService.getUserById(userId);
    }

    /**
     * <p>
     * Stores the address of the user.
     * </p>
     *
     * @param address Represents the address of the user
     * @return byte array of json response
     */
    @PostMapping(value = "/address", consumes = "application/json", produces = "application/json")
    public byte[] addAddress(@RequestBody final Address address) {
        return userService.addAddress(address);
    }

    /**
     * <p>
     * Displays all the addresses of the user.
     * </p>
     *
     * @param userId Represents the id of the user
     * @return byte array of json response
     */
    @GetMapping(value = "/address/{userId}", produces = "application/json")
    public byte[] getAddress(@PathVariable final long userId) {
        return userService.getAddress(userId);
    }

    /**
     * <p>
     * Updates the data of the current user.
     * </p>
     *
     * @param userProfileUpdateDetails Represents the instance of user profile update dto
     * @return byte array of json response
     */
    @PutMapping(consumes = "application/json", produces = "application/json")
    public byte[] updateUserProfile(@RequestBody final UserProfileUpdateDetails userProfileUpdateDetails) {
        return userService.updateUserProfile(userProfileUpdateDetails);
    }
}