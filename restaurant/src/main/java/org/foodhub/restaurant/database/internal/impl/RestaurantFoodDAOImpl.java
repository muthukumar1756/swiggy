package org.foodhub.restaurant.database.internal.impl;

import java.util.Collection;
import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import org.foodhub.database.connection.DataBaseConnection;
import org.foodhub.restaurant.database.persistenceservice.RestaurantFoodPersistenceService;
import org.foodhub.restaurant.database.resultsetextractor.RestaurantFoodResultSetExtractor;
import org.foodhub.restaurant.database.dao.RestaurantFoodDAO;
import org.foodhub.restaurant.exception.food.FoodDataNotFoundException;
import org.foodhub.restaurant.exception.food.InvalidFoodDataException;
import org.foodhub.restaurant.exception.food.MenuCardNotFoundException;
import org.foodhub.restaurant.model.food.Food;

/**
 * <p>
 * Implements the data base service for the restaurant related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Repository
public final class RestaurantFoodDAOImpl implements RestaurantFoodDAO {

    private final RestaurantFoodPersistenceService restaurantFoodPersistenceService;
    private final RestaurantFoodResultSetExtractor restaurantFoodResultSetExtractor;
    private final Connection connection;

    public RestaurantFoodDAOImpl() {
        restaurantFoodPersistenceService = RestaurantFoodPersistenceService.getInstance();
        restaurantFoodResultSetExtractor = RestaurantFoodResultSetExtractor.getInstance();
        connection = DataBaseConnection.get();
    }


    /**
     * {@inheritDoc}
     *
     * @param food         Represents the current food added by the restaurant
     * @param restaurantId Represents the id of the restaurant
     * @return True if food is added, false otherwise
     */
    @Override
    public boolean addFood(final Food food, final long restaurantId) {
        try {
            connection.setAutoCommit(false);
            final String query = restaurantFoodPersistenceService.addFood(food);

            try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                final Optional<Long> foodId = restaurantFoodResultSetExtractor.getFoodId(preparedStatement.executeQuery());

                mapFoodsWithRestaurant(foodId.get());
                connection.commit();

                return true;
            } catch (SQLException message) {
                connection.rollback();
                throw new InvalidFoodDataException(message.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException message) {
            throw new InvalidFoodDataException(message.getMessage());
        }
    }

    /**
     * <p>
     * Maps the food with restaurant.
     * </p>
     *
     * @param foodId Represents the id of the restaurant
     */
    private void mapFoodsWithRestaurant(final long foodId) {
        final String query = restaurantFoodPersistenceService.mapFoodsWithRestaurant(foodId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, foodId);
            preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new InvalidFoodDataException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param foodId Represents the id of the food
     * @return True if food is removed, false otherwise
     */
    @Override
    public boolean removeFood(final long foodId) {
        final String query = restaurantFoodPersistenceService.removeFood(foodId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new InvalidFoodDataException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param foodId Represents the id of the food
     * @return Available quantity of food from the restaurant
     */
    @Override
    public Optional<Integer> getFoodQuantity(final long foodId) {
        final String query = restaurantFoodPersistenceService.getFoodQuantity(foodId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return restaurantFoodResultSetExtractor.getFoodQuantity(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new FoodDataNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurantId Represents the id of the restaurant
     * @return The list of menucard having foods
     */
    @Override
    public Optional<Collection<Food>> getMenuCard(final long restaurantId, final int menucardId) {
        final String query = restaurantFoodPersistenceService.getMenuCard(restaurantId, menucardId);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return restaurantFoodResultSetExtractor.getMenuCard(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new MenuCardNotFoundException(message.getMessage());
        }
    }
}