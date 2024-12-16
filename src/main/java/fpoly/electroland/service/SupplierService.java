package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.SupplierReponsitory;

@Service
public class SupplierService {

    @Autowired
    SupplierReponsitory supplierReponsitory;

}
