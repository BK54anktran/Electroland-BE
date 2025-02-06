package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Receipt;
import fpoly.electroland.repository.ReceiptRepository;

@Service
public class ReceiptService {

    @Autowired
        ReceiptRepository receiptRepository;
     public List<Receipt> getAll() {
        return receiptRepository.findAll();
    }
   
}
