package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.repository.SupplierRepository;
import fpoly.electroland.util.CreateAction;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CreateAction createAction;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier createSupplier(Supplier supplier, int userId){
        Supplier savedSupplier = supplierRepository.save(supplier);
        Employee creator = employeeRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        createAction.createAction("Supplier", "CREATE", savedSupplier.getId(), null, 
        savedSupplier.toString(), creator);
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier updateSupplier, int userId) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier existingSupplier = optionalSupplier.get();
            
            existingSupplier.setLogo(updateSupplier.getLogo());
            existingSupplier.setName(updateSupplier.getName());

            Supplier savedSupplier = supplierRepository.save(existingSupplier);

            Employee creatorEmployee = employeeRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            createAction.createAction("Supplier", "UPDATE", savedSupplier.getId(), optionalSupplier.toString(), updateSupplier.toString(), creatorEmployee);
            return savedSupplier;
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    public Supplier findSupplierById(int id){
        return supplierRepository.findById(id).get();
    }
}
