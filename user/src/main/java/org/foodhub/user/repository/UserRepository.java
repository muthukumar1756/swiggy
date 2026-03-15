package org.foodhub.user.repository;

import java.util.Collection;
import java.util.Optional;

import org.foodhub.user.model.address.Address;
import org.foodhub.user.model.user.User;

/**
 * <p>
 * Repository interface for User and Address operations.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public interface UserRepository {

    /**
     * <p>
     * Checks if user exists.
     * </p>
     *
     * @param phoneNumber The phone number
     * @param emailId The email id
     * @return True if exists
     */
    boolean isUserExist(String phoneNumber, String emailId);

    /**
     * <p>
     * Creates the user profile.
     * </p>
     *
     * @param user The user
     * @return True if created
     */
    boolean createUserProfile(User user);

    /**
     * <p>
     * Gets the user.
     * </p>
     *
     * @param userDataType The data type
     * @param userData The data
     * @param password The password
     * @return Optional of user
     */
    Optional<User> getUser(String userDataType, String userData, String password);

    /**
     * <p>
     * Gets user by id.
     * </p>
     *
     * @param userId The user id
     * @return Optional of user
     */
    Optional<User> getUserById(long userId);

    /**
     * <p>
     * Adds address.
     * </p>
     *
     * @param address The address
     * @return True if added
     */
    boolean addAddress(Address address);

    /**
     * <p>
     * Gets addresses by user id.
     * </p>
     *
     * @param userId The user id
     * @return Optional of collection of addresses
     */
    Optional<Collection<Address>> getAddress(long userId);

    /**
     * <p>
     * Updates user profile.
     * </p>
     *
     * @param userId The user id
     * @param userDataType The data type
     * @param userData The data
     * @return True if updated
     */
    boolean updateUserProfile(long userId, String userDataType, String userData);
}
