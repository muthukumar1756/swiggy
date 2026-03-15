package org.foodhub.user.controller;

import org.foodhub.TestConfiguration;
import org.foodhub.common.json.JsonElement;
import org.foodhub.common.json.JsonFactory;
import org.foodhub.user.model.user.User;
import org.foodhub.user.service.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>
 * The UserTest class is designed to perform unit tests on the User controller class.It ensures that the methods and
 * functionalities of the User controller class are working correctly by providing a series of test cases.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = TestConfiguration.class)
final class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final JsonFactory jsonFactory = JsonFactory.getInstance();

    /**
     * <p>
     *  Verifies that the getUserById method correctly retrieves user details for given user ids.
     * </p>
     */
    @ParameterizedTest
    @ValueSource(longs = {1, 50, 47})
    void shouldReturnCorrectUserDetailsForGivenUserIds(final long userId) throws Exception {
        final User user = new User.UserBuilder().setId(userId).build();
        final byte[] expected = jsonFactory.createObjectNode().build(user).asBytes();

        Mockito.when(userService.getUserById(userId)).thenReturn(expected);

        mockMvc.perform(get("/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().bytes(expected));
    }

    /**
     * <p>
     * Checks the functionalities of the user profile creation is working correctly by providing a series of test cases.
     * </p>
     */
    @ParameterizedTest
    @CsvSource({
            "Muthu kumar,muthukumar@gmail.com,9832178952,Kumar@1234,User profile was created",
            "Mk,muthukumar@gmail.com,9832178952,Kumar@1234,Enter a valid name",
            "Muthu kumar,kumar@g,9832178952,Kumar@1234,Enter a valid email id",
            "Muthu kumar,muthukumar@gmail.com,74105,Kumar@1234,Enter a valid phone number",
            "Muthu kumar,muthukumar@gmail.com,9832178952,kumar,Enter a valid password"
    })
    void shouldValidateAndCreateUserProfileBasedOnInputData(final String name, final String emailId, final String phoneNumber,
                                  final String password, final String expectedStatus) throws Exception {
        final User user = new User.UserBuilder().setName(name).setEmailId(emailId).setPhoneNumber(phoneNumber)
                .setPassword(password).build();
        final byte[] expected = jsonFactory.createObjectNode().put("status", expectedStatus).asBytes();

        Mockito.when(userService.createUserProfile(user)).thenReturn(expected);

        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(jsonFactory.createObjectNode().build(user).toString()))
                .andExpect(status().isOk())
                .andExpect(content().bytes(expected));
    }

    /**
     * <p>
     * Checks the functionalities of the user profile creation is working correctly by providing a series of test cases.
     * </p>
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_data.csv")
    void shouldValidateAndCreateUserProfileBasedOnCsvData(final String name, final String emailId, final String phoneNumber,
                                  final String password, final String expectedStatus) throws Exception {
        final User user = new User.UserBuilder().setName(name).setEmailId(emailId).setPhoneNumber(phoneNumber)
                .setPassword(password).build();
        final byte[] expected = jsonFactory.createObjectNode().put("status", expectedStatus).asBytes();

        Mockito.when(userService.createUserProfile(user)).thenReturn(expected);

        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(jsonFactory.createObjectNode().build(user).toString()))
                .andExpect(status().isOk())
                .andExpect(content().bytes(expected));
    }
}