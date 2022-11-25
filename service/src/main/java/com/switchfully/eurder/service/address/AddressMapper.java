package com.switchfully.eurder.service.address;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.AddressBuilder;

public class AddressMapper {
    public Address mapDTOToAddress(AddressDTO addressDTO) {
        return new AddressBuilder()
                .setStreetName(addressDTO.getStreetName())
                .setStreetNumber(addressDTO.getStreetNumber())
                .setPostalCode(addressDTO.getPostalCode())
                .setCityName(addressDTO.getCity())
                .build();
    }

    public AddressDTO mapAddressToDTO(Address address) {
        return new AddressDTO()
                .setStreetName(address.getStreetName())
                .setStreetNumber(address.getStreetNumber())
                .setPostalCode(address.getPostalCodeAsString())
                .setCity(address.getCity());
    }
}
