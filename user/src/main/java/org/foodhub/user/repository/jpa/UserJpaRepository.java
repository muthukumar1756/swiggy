package org.foodhub.user.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.foodhub.user.entity.UserEntity;

/**
 * <p>
 * JPA Repository for UserEntity.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<UserEntity> findByEmailIdAndPassword(String emailId, String password);

    boolean existsByPhoneNumberOrEmailId(String phoneNumber, String emailId);
}
