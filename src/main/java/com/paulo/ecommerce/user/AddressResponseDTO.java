package com.paulo.ecommerce.user;

public record AddressResponseDTO(
        Long id,
        String cep,
        String street,
        String number,
        String city,
        String uf
) {
    public static AddressResponseDTO fromEntity(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressResponseDTO(
                address.getId(),
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getUf()
        );
    }
}
