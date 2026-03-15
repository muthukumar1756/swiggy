package org.foodhub.user.repository.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.foodhub.user.entity.AddressEntity;
import org.foodhub.user.entity.UserEntity;
import org.foodhub.user.mapper.AddressMapper;
import org.foodhub.user.mapper.UserMapper;
import org.foodhub.user.model.address.Address;
import org.foodhub.user.model.user.User;
import org.foodhub.user.repository.UserRepository;
import org.foodhub.user.repository.jpa.AddressJpaRepository;
import org.foodhub.user.repository.jpa.UserJpaRepository;

/**
 * <p>
 * JPA implementation of UserRepository.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Repository
@ConditionalOnProperty(name = "orm.provider", havingValue = "hibernate")
public class JpaUserRepositoryImpl implements UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isUserExist(String phoneNumber, String emailId) {
        return userJpaRepository.existsByPhoneNumberOrEmailId(phoneNumber, emailId);
    }

    @Override
    @Transactional
    public boolean createUserProfile(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        userJpaRepository.save(entity);
        return true; // Assuming save always succeeds
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(String userDataType, String userData, String password) {
        Optional<UserEntity> entity;
        if ("PHONE_NUMBER".equals(userDataType)) {
            entity = userJpaRepository.findByPhoneNumberAndPassword(userData, password);
        } else if ("EMAIL_ID".equals(userDataType)) {
            entity = userJpaRepository.findByEmailIdAndPassword(userData, password);
        } else {
            entity = Optional.empty();
        }
        return entity.map(UserMapper::toModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(long userId) {
        return userJpaRepository.findById(userId).map(UserMapper::toModel);
    }

    @Override
    @Transactional
    public boolean addAddress(Address address) {
        AddressEntity entity = AddressMapper.toEntity(address);
        addressJpaRepository.save(entity);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Collection<Address>> getAddress(long userId) {
        List<AddressEntity> entities = addressJpaRepository.findByUserId(userId);
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        List<Address> addresses = entities.stream().map(AddressMapper::toModel).collect(Collectors.toList());
        return Optional.of(addresses);
    }

    @Override
    @Transactional
    public boolean updateUserProfile(long userId, String userDataType, String userData) {
        Optional<UserEntity> entityOpt = userJpaRepository.findById(userId);
        if (entityOpt.isPresent()) {
            UserEntity entity = entityOpt.get();
            switch (userDataType) {
                case "NAME" -> entity.setName(userData);
                case "PHONE_NUMBER" -> entity.setPhoneNumber(userData);
                case "EMAIL_ID" -> entity.setEmailId(userData);
                case "PASSWORD" -> entity.setPassword(userData);
            }
            userJpaRepository.save(entity);
            return true;
        }
        return false;
    }
}
