package fpoly.electroland.restController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Address;
import fpoly.electroland.model.Customer;
import fpoly.electroland.service.AddressService;
import fpoly.electroland.service.CustomerService;
import fpoly.electroland.service.UserService;

@RestController
public class AddressController {
    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    AddressService addressService;

    @GetMapping("/getUserAdress")
    public List<Address> getAddresses() {
        System.out.println(userService.getUser());
        int id = userService.getUser().getId();
        Customer customer = customerService.getCustomer(id).get();
        return addressService.getAddresses(customer);
    }

    @PostMapping("/updateAddress")
    public void updateUserAddress(@RequestBody Address address) {
        int id = userService.getUser().getId();
        Customer customer = customerService.getCustomer(id).get();
        address.setCustomer(customer);
        System.out.println(address);
        if(address.isStatus()){
            List<Address> list = addressService.getAddresses(customer);
            list.forEach(ad ->{
                if(ad.isStatus() && (ad.getId() !=address.getId())){
                    ad.setStatus(false);
                };
            });
        };
        addressService.savAddress(address);
    }

    @PostMapping("/deleteAddress")
    public void deleteAddress(@RequestBody Address address){
        addressService.deletAddress(address);
    }
}
