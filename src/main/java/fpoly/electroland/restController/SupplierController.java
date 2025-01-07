package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Supplier;
import fpoly.electroland.service.SupplierService;

@RestController
public class SupplierController {

        @Autowired
        SupplierService supplierService;

        @GetMapping("/supplier")
        public List<Supplier> getSuppliers(){
            return supplierService.getAllSuppliers();
        }
}
