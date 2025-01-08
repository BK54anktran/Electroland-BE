package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

}
