package org.foodhub.user.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.foodhub.user.entity.AddressEntity;

/**
 * <p>
 * JPA Repository for AddressEntity.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findByUserId(Long userId);
}
