package com.paulo.ecommerce.services;

import com.paulo.ecommerce.dto.AddressUpdateRequestDTO;
import com.paulo.ecommerce.entities.Address;
import com.paulo.ecommerce.entities.User;
import com.paulo.ecommerce.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address syncUserAddress(User user, AddressUpdateRequestDTO request) {
        if (request == null) {
            return user.getAddress();
        }

        Address address = user.getAddress();

        if (address == null) {
            address = new Address();
            address.setUser(user);
        }

        address.setCep(request.cep());
        address.setStreet(request.street());
        address.setNumber(request.number());
        address.setCity(request.city());
        address.setUf(request.uf());

        return addressRepository.save(address);
    }
}
