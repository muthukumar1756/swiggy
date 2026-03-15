package org.foodhub.user.database.dao.internal.impl;

import java.util.Collection;
import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import org.foodhub.database.connection.DataBaseConnection;
import org.foodhub.user.database.persistenceservice.UserPersistenceService;
import org.foodhub.user.database.resultsetextractor.UserResultSetExtractor;
import org.foodhub.user.exception.user.UserProfileCreationException;
import org.foodhub.user.model.address.Address;
import org.foodhub.user.exception.user.address.AddressDataNotFoundException;
import org.foodhub.user.database.dao.UserDAO;
import org.foodhub.user.exception.user.address.AddressDataPersistenceException;
import org.foodhub.user.exception.user.UserProfileNotFoundException;
import org.foodhub.user.exception.user.UserProfileUpdateException;
import org.foodhub.user.model.user.User;

/**
 * <p>
 * Implements the data base service of the user related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Repository
public final class UserDAOImpl implements UserDAO {

    private final UserPersistenceService userPersistenceService;
    private final UserResultSetExtractor userResultSetExtractor;
    private final Connection connection;

    public UserDAOImpl() {
        userPersistenceService = UserPersistenceService.getInstance();
        userResultSetExtractor = UserResultSetExtractor.getInstance();
        connection = DataBaseConnection.get();
    }


    /**
     * {@inheritDoc}
     *
     * @param user Represents the user
     * @return True if user is created, false otherwise
     */
    @Override
    public boolean createUserProfile(final User user) {
        final String query = userPersistenceService.createUserProfile(user);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new UserProfileCreationException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return True if user is exist, false otherwise
     */
    @Override
    public boolean isUserExist(final String phoneNumber, final String emailId) {
        final String query = userPersistenceService.isUserExist(phoneNumber, emailId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return userResultSetExtractor.isUserExist(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new UserProfileNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userLoginField     Represents the data type of the user
     * @param userLoginFieldData Represents the data of the user
     * @param password           Represents the password of the user
     * @return The user object
     */
    @Override
    public Optional<User> getUser(final String userLoginField, final String userLoginFieldData, final String password) {
        final String query = userPersistenceService.getUser(userLoginField, userLoginFieldData, password);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return userResultSetExtractor.getUser(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new UserProfileNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userId Represents the password of the user
     * @return The user object
     */
    @Override
    public Optional<User> getUserById(final long userId) {
        final String query = userPersistenceService.getUserById(userId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return userResultSetExtractor.getUser(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new UserProfileNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param address Represents the address of the user
     * @return True if the address is added, false otherwise
     */
    @Override
    public boolean addAddress(final Address address) {
        final String query = userPersistenceService.addAddress(address);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new AddressDataPersistenceException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userId Represents the id of the user
     * @return List of addresses of the user
     */
    @Override
    public Optional<Collection<Address>> getAddress(final long userId) {
        final String query = userPersistenceService.getAddress(userId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return userResultSetExtractor.getAddress(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new AddressDataNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userId       Represents the id of user
     * @param userData     Represents the data to be updated
     * @param userDataType Represents the type of data to be updated
     * @return True if user data is updated, false otherwise
     */
    @Override
    public boolean updateUserProfile(final long userId, final String userDataType, final String userData) {
        final String query = userPersistenceService.updateUserProfile(userId, userDataType, userData);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new UserProfileUpdateException(message.getMessage());
        }
    }
}