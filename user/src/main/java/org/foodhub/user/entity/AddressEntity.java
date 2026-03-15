package org.foodhub.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.foodhub.user.model.address.AddressType;

/**
 * <p>
 * JPA Entity for Address.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Entity
@Table(name = "address_table")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String houseNumber;
    private String streetName;
    private String areaName;
    private String cityName;
    private String pincode;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    public AddressEntity() {
    }

    public AddressEntity(Long userId, String houseNumber, String streetName, String areaName, String cityName, String pincode, AddressType addressType) {
        this.userId = userId;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.areaName = areaName;
        this.cityName = cityName;
        this.pincode = pincode;
        this.addressType = addressType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
}
