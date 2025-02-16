package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Address;
import fpoly.electroland.model.Customer;
import fpoly.electroland.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAddresses(Customer customer){
        return addressRepository.getAddressesByCustomer(customer);
    }

    public Address savAddress(Address address){
        return addressRepository.save(address);
    }

    public void deletAddress(Address address){
        System.out.println(address);
        addressRepository.delete(address);
    }
}
