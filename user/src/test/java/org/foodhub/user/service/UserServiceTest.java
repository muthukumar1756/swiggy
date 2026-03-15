package org.foodhub.user.service;

import org.foodhub.TestConfiguration;
import org.foodhub.common.json.JsonElement;
import org.foodhub.common.json.JsonFactory;
import org.foodhub.user.model.user.User;
import org.foodhub.user.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

/**
 * <p>
 * The UserTest class is designed to perform unit tests on the User service class.It ensures that the methods and
 * functionalities of the User service class are working correctly by providing a series of test cases.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
class UserServiceTest {

    private static final String STATUS = "status";

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private final JsonFactory jsonFactory = JsonFactory.getInstance();

    /**
     * <p>
     *  Verifies that the getUserById method correctly retrieves user details for given user ids.
     * </p>
     */
    @ParameterizedTest
    @ValueSource(longs = {1, 50, 47})
    void shouldReturnCorrectUserDetailsForGivenUserIds(final long userId) {
        final User user = new User.UserBuilder().setId(userId).build();

        Mockito.when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        final byte[] result = userService.getUserById(userId);
        final JsonElement jsonElement = jsonFactory.asJsonObject(result);

        if (jsonElement.hasElement("id")) {
            final String value = jsonElement.getValue("id");

            Assertions.assertEquals(String.valueOf(userId), value);
        }
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
                                                             final String password, final String expectedStatus) {
        final User user = new User.UserBuilder().setName(name).setEmailId(emailId).setPhoneNumber(phoneNumber)
                .setPassword(password).build();

        Mockito.when(userRepository.isUserExist(phoneNumber, emailId)).thenReturn(false);
        Mockito.when(userRepository.createUserProfile(user)).thenReturn(true);
        final byte[] result = userService.createUserProfile(user);
        final JsonElement jsonElement = jsonFactory.asJsonObject(result);

        if (jsonElement.hasElement(STATUS)) {
            final String value = jsonElement.getValue(STATUS);

            Assertions.assertEquals(expectedStatus, value);
        }
    }

    /**
     * <p>
     * Checks the functionalities of the user profile creation is working correctly by providing a series of test cases.
     * </p>
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_data.csv")
    void shouldValidateAndCreateUserProfileBasedOnCsvData(final String name, final String emailId, final String phoneNumber,
                                                           final String password, final String expectedStatus) {
        final User user = new User.UserBuilder().setName(name).setEmailId(emailId).setPhoneNumber(phoneNumber)
                .setPassword(password).build();

        Mockito.when(userRepository.isUserExist(phoneNumber, emailId)).thenReturn(false);
        Mockito.when(userRepository.createUserProfile(user)).thenReturn(true);
        final byte[] result = userService.createUserProfile(user);
        final JsonElement jsonElement = jsonFactory.asJsonObject(result);

        if (jsonElement.hasElement(STATUS)) {
            final String value = jsonElement.getValue(STATUS);

            Assertions.assertEquals(expectedStatus, value);
        }
    }
}
