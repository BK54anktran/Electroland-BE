package fpoly.electroland.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.dto.response.AddressDto;
import fpoly.electroland.model.Address;
import fpoly.electroland.model.Customer;
import fpoly.electroland.model.User;
import fpoly.electroland.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserService userService;

    public Object getUserAddress() {
        List<Address> list = addressRepository.findByCustomerId(userService.getUser().getId());
        List<AddressDto> list2 = new ArrayList<>();
        for (Address address : list) {
            list2.add(AddressToDto(address));
        }
        return list2;
    }

    public AddressDto AddressToDto(Address address) {
        return new AddressDto(0,
                address.getStreet() + ", " + address.getNameWard() + ", " + address.getNameDistrict() + ", "
                        + address.getNameCity(),
                address.isStatus(), address.getNameReciever(), address.getPhoneReciever());
    }

    public List<Address> getAddresses(Customer customer) {
        return addressRepository.getAddressesByCustomer(customer);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deletAddress(Address address) {
        System.out.println(address);
        addressRepository.delete(address);
    }
}
