package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptDetail;
import fpoly.electroland.repository.ReceiptDetailRepository;

@Service
public class ReceiptDetailService {

    @Autowired
    ReceiptDetailRepository receiptDetailRepository;
    
     public List<ReceiptDetail> getAll() {
            return receiptDetailRepository.findAll();
    }
}
