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
        return list;
    }

    public List<Address> getAddresses(Customer customer) {
        // System.out.println(customer);
        return addressRepository.getAddressesByCustomer(customer);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deletAddress(Address address) {
        // System.out.println(address);
        addressRepository.delete(address);
    }
}
