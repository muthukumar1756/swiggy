package org.foodhub.user.mapper;

import org.foodhub.user.entity.UserEntity;
import org.foodhub.user.model.user.User;

/**
 * <p>
 * Mapper for User and UserEntity.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public class UserMapper {

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setPassword(user.getPassword());
        entity.setEmailId(user.getEmailId());
        return entity;
    }

    public static User toModel(UserEntity entity) {
        return new User.UserBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setPhoneNumber(entity.getPhoneNumber())
                .setPassword(entity.getPassword())
                .setEmailId(entity.getEmailId())
                .build();
    }
}
