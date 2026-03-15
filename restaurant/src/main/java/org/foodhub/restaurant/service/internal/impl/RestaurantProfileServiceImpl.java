package org.foodhub.restaurant.service.internal.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.foodhub.common.hibernate.HibernateEntityValidator;
import org.foodhub.restaurant.database.dao.RestaurantProfileDAO;
import org.foodhub.restaurant.model.restaurant.Restaurant;
import org.foodhub.restaurant.model.restaurant.RestaurantLoginDetails;
import org.foodhub.restaurant.model.restaurant.RestaurantProfileField;
import org.foodhub.restaurant.model.restaurant.RestaurantProfileUpdateDetails;
import org.foodhub.restaurant.service.RestaurantProfileService;
import org.foodhub.common.hashgenerator.PasswordHashGenerator;
import org.foodhub.common.json.JsonArray;
import org.foodhub.common.json.JsonFactory;
import org.foodhub.common.json.JsonObject;
import org.foodhub.common.hibernate.impl.HibernateEntityValidatorImpl;
import org.foodhub.common.hibernate.validatorgroup.Restaurant.GetRestaurantValidator;
import org.foodhub.common.hibernate.validatorgroup.Restaurant.LoginRestaurantValidator;
import org.foodhub.common.hibernate.validatorgroup.Restaurant.CreateRestaurantValidator;
import org.foodhub.common.hibernate.validatorgroup.Restaurant.UpdateRestaurantValidator;

/**
 * <p>
 * Implements the service of the restaurant profile related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Service
public final class RestaurantProfileServiceImpl implements RestaurantProfileService {

    private static final String STATUS = "status";
    private final JsonFactory jsonFactory;
    private final HibernateEntityValidator validatorFactory;

    @Autowired
    private RestaurantProfileDAO restaurantProfileDAO;

    public RestaurantProfileServiceImpl() {
        jsonFactory = JsonFactory.getInstance();
        validatorFactory = HibernateEntityValidatorImpl.getInstance();
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurant Represents the restaurant
     * @return The response for the restaurant profile creation
     */
    @Override
    public byte[] createRestaurantProfile(final Restaurant restaurant) {
        final JsonObject jsonObject = validatorFactory.validate(restaurant, CreateRestaurantValidator.class);

        if (jsonObject.isEmpty()) {

            if (restaurantProfileDAO.isRestaurantExist(restaurant.getPhoneNumber(), restaurant.getEmailId())) {
                return jsonObject.put(STATUS, "Restaurant is already exist").asBytes();
            }

            return restaurantProfileDAO.createRestaurantProfile(restaurant) ?
                    jsonObject.put(STATUS, "Restaurant profile was created").asBytes() :
                    jsonObject.put(STATUS, "Restaurant profile creation failed").asBytes();
        }

        return jsonObject.asBytes();
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurantLoginDetails Represents the instance of restaurant login details
     * @return The restaurant object
     */
    @Override
    public byte[] getRestaurant(final RestaurantLoginDetails restaurantLoginDetails) {
        final JsonObject jsonObject = validatorFactory.validate(restaurantLoginDetails, LoginRestaurantValidator.class);

        if (jsonObject.isEmpty()) {
            final String hashPassword = PasswordHashGenerator.getInstance().hashPassword(restaurantLoginDetails.password());

            final Optional<Restaurant> restaurant = switch (restaurantLoginDetails.loginType()) {
                case PHONE_NUMBER -> restaurantProfileDAO.getRestaurant(RestaurantProfileField.PHONE_NUMBER.name(),
                        restaurantLoginDetails.phoneNumber(), hashPassword);
                case EMAIL_ID -> restaurantProfileDAO.getRestaurant(RestaurantProfileField.EMAIL_ID.name(),
                        restaurantLoginDetails.emailId(), hashPassword);
                default -> Optional.empty();
            };

            return restaurant.isPresent() ?
                    jsonObject.put(STATUS, String.join(" ", "Restaurant login successful welcome",
                            restaurant.get().getName())).asBytes() :
                    jsonObject.put(STATUS, "Restaurant login failed").asBytes();
        }

        return jsonObject.asBytes();
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurantId Represents the id of the restaurant
     * @return The restaurant object
     */
    public byte[] getRestaurantById(final long restaurantId) {
        final Restaurant restaurantPojo = new Restaurant.RestaurantBuilder().setId(restaurantId).build();
        final JsonObject jsonObject = validatorFactory.validate(restaurantPojo, GetRestaurantValidator.class);

        if (jsonObject.isEmpty()) {
            final Optional<Restaurant> restaurant = restaurantProfileDAO.getRestaurantById(restaurantId);

            return restaurant.isPresent() ? jsonObject.build(restaurant.get()).asBytes() :
                    jsonObject.put(STATUS, "Restaurant not found").asBytes();
        }

        return jsonObject.asBytes();
    }

    /**
     * {@inheritDoc}
     *
     * @param restaurantProfileUpdateDetails Represents the instance of restaurant profile update details
     * @return The response for the restaurant profile updation
     */
    @Override
    public byte[] updateRestaurantData(final RestaurantProfileUpdateDetails restaurantProfileUpdateDetails) {
        final JsonObject jsonObject = validatorFactory.validate(restaurantProfileUpdateDetails, UpdateRestaurantValidator.class);

        if (jsonObject.isEmpty()) {
            final boolean updateStatus = switch (restaurantProfileUpdateDetails.updateDataType()) {
                case NAME -> restaurantProfileDAO.updateRestaurantProfile(restaurantProfileUpdateDetails.id(),
                        RestaurantProfileField.NAME.name(), restaurantProfileUpdateDetails.name());
                case PHONE_NUMBER -> restaurantProfileDAO.updateRestaurantProfile(restaurantProfileUpdateDetails.id(),
                        RestaurantProfileField.PHONE_NUMBER.name(), restaurantProfileUpdateDetails.phoneNumber());
                case EMAIL_ID -> restaurantProfileDAO.updateRestaurantProfile(restaurantProfileUpdateDetails.id(),
                        RestaurantProfileField.EMAIL_ID.name(), restaurantProfileUpdateDetails.emailId());
                case PASSWORD -> restaurantProfileDAO.updateRestaurantProfile(restaurantProfileUpdateDetails.id(),
                        RestaurantProfileField.PASSWORD.name(), restaurantProfileUpdateDetails.password());
            };

            return updateStatus ? jsonObject.put(STATUS, "Restaurant profile updated").asBytes() :
                    jsonObject.put(STATUS, "Restaurant profile updation failed").asBytes();
        }

        return jsonObject.asBytes();
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of all restaurants
     */
    @Override
    public byte[] getAllRestaurants() {
        final Optional<Collection<Restaurant>> restaurants = restaurantProfileDAO.getAllRestaurants();
        final JsonArray jsonArray = jsonFactory.createArrayNode();

        return restaurants.isPresent() ? jsonArray.build(restaurants.get()).asBytes() :
                jsonArray.add(jsonFactory.createObjectNode().put(STATUS, "Restaurants not found")).asBytes();
    }
}
