package org.foodhub.user.mapper;

import org.foodhub.user.entity.AddressEntity;
import org.foodhub.user.model.address.Address;

/**
 * <p>
 * Mapper for Address and AddressEntity.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public class AddressMapper {

    public static AddressEntity toEntity(Address address) {
        AddressEntity entity = new AddressEntity();
        entity.setId(address.getId());
        entity.setUserId(address.getUserId());
        entity.setHouseNumber(address.getHouseNumber());
        entity.setStreetName(address.getStreetName());
        entity.setAreaName(address.getAreaName());
        entity.setCityName(address.getCityName());
        entity.setPincode(address.getPincode());
        entity.setAddressType(address.getAddressType());
        return entity;
    }

    public static Address toModel(AddressEntity entity) {
        return new Address.AddressBuilder()
                .setId(entity.getId())
                .setUserId(entity.getUserId())
                .setHouseNumber(entity.getHouseNumber())
                .setStreetName(entity.getStreetName())
                .setAreaName(entity.getAreaName())
                .setCityName(entity.getCityName())
                .setPincode(entity.getPincode())
                .setAddressType(entity.getAddressType())
                .build();
    }
}
