package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.SupplierReponsitory;

@Service
public class SupplierService {

    @Autowired
    SupplierReponsitory supplierReponsitory;

    public List<Supplier> getAllSuppliers(){
        return supplierReponsitory.findAll();
    }

}
