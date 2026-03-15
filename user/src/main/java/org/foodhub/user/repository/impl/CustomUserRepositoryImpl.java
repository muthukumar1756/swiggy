package org.foodhub.user.repository.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.foodhub.user.database.dao.UserDAO;
import org.foodhub.user.model.address.Address;
import org.foodhub.user.model.user.User;
import org.foodhub.user.repository.UserRepository;

/**
 * <p>
 * Custom ORM implementation of UserRepository.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Repository
@ConditionalOnProperty(name = "orm.provider", havingValue = "custom", matchIfMissing = true)
public class CustomUserRepositoryImpl implements UserRepository {

    private final UserDAO userDAO;

    public CustomUserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserExist(String phoneNumber, String emailId) {
        return userDAO.isUserExist(phoneNumber, emailId);
    }

    @Override
    @Transactional
    public boolean createUserProfile(User user) {
        return userDAO.createUserProfile(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(String userDataType, String userData, String password) {
        return userDAO.getUser(userDataType, userData, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(long userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    @Transactional
    public boolean addAddress(Address address) {
        return userDAO.addAddress(address);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Collection<Address>> getAddress(long userId) {
        return userDAO.getAddress(userId);
    }

    @Override
    @Transactional
    public boolean updateUserProfile(long userId, String userDataType, String userData) {
        return userDAO.updateUserProfile(userId, userDataType, userData);
    }
}
