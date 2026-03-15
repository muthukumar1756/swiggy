package org.foodhub.restaurant.database.internal.impl;

import java.util.Collection;
import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import org.foodhub.database.connection.DataBaseConnection;
import org.foodhub.restaurant.database.dao.RestaurantProfileDAO;
import org.foodhub.restaurant.database.persistenceservice.RestaurantProfilePersistenceService;
import org.foodhub.restaurant.exception.restaurant.RestaurantProfileCreationException;
import org.foodhub.restaurant.exception.restaurant.RestaurantProfileUpdateException;
import org.foodhub.restaurant.database.resultsetextractor.RestaurantProfileResultSetExtractor;
import org.foodhub.restaurant.exception.restaurant.RestaurantDataNotFoundException;
import org.foodhub.restaurant.model.restaurant.Restaurant;

/**
 * <p>
 * Implements the data base service for the restaurant profile related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Repository
public final class RestaurantProfileDAOImpl implements RestaurantProfileDAO {

    private final RestaurantProfilePersistenceService restaurantProfilePersistenceService;
    private final RestaurantProfileResultSetExtractor restaurantProfileResultSetExtractor;
    private final Connection connection;

    public RestaurantProfileDAOImpl() {
        restaurantProfilePersistenceService = RestaurantProfilePersistenceService.getInstance();
        restaurantProfileResultSetExtractor = RestaurantProfileResultSetExtractor.getInstance();
        connection = DataBaseConnection.get();
    }

    /**
     * <p>
     * Creates the restaurant profile.
     * </p>
     *
     * @param restaurant Represents the restaurant
     * @return True if restaurant profile is created, false otherwise
     */
    @Override
    public boolean createRestaurantProfile(final Restaurant restaurant) {
        final String query = restaurantProfilePersistenceService.createRestaurantProfile(restaurant);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new RestaurantProfileCreationException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param emailId     Represents the email id of the user
     * @param phoneNumber Represents the phone number of the user
     * @return True if restaurant is exist, false otherwise
     */
    @Override
    public boolean isRestaurantExist(final String phoneNumber, final String emailId) {
        final String query = restaurantProfilePersistenceService.isRestaurantExist(phoneNumber, emailId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return restaurantProfileResultSetExtractor.isRestaurantExist(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new RestaurantDataNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurantDataType Represents the type of data of the restaurant
     * @param restaurantData     Represents the data of the restaurant
     * @param password           Represents the password of the restaurant
     * @return The restaurant object
     */
    @Override
    public Optional<Restaurant> getRestaurant(final String restaurantDataType, final String restaurantData,
                                              final String password) {
        final String query = restaurantProfilePersistenceService.restaurantLogin(restaurantDataType, restaurantData, password);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return restaurantProfileResultSetExtractor.getRestaurant(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new RestaurantDataNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurantId Represents the id of the restaurant
     * @return The restaurant object
     */
    @Override
    public Optional<Restaurant> getRestaurantById(final long restaurantId) {
        final String query = restaurantProfilePersistenceService.getRestaurantById(restaurantId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return restaurantProfileResultSetExtractor.getRestaurant(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new RestaurantDataNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurantId   Represents the id of the restaurant
     * @param restaurantData Represents the data of the restaurant to be updated
     * @param type           Represents the type of data of the restaurant to be updated
     * @return True if data is updated, false otherwise
     */
    @Override
    public boolean updateRestaurantProfile(final long restaurantId, final String type, final String restaurantData) {
        final String query = restaurantProfilePersistenceService.updateRestaurantProfile(restaurantId, type, restaurantData);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new RestaurantProfileUpdateException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of all restaurants
     */
    @Override
    public Optional<Collection<Restaurant>> getAllRestaurants() {
        final String query = restaurantProfilePersistenceService.getAllRestaurants();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return restaurantProfileResultSetExtractor.getAllRestaurants(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new RestaurantDataNotFoundException(message.getMessage());
        }
    }
}
