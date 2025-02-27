package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.ReceiptStatus;
import fpoly.electroland.repository.ReceiptStatusRepository;

@Service
public class ReceiptStatusService {

    @Autowired
    ReceiptStatusRepository receiptStatusRepository;

    public List<ReceiptStatus> getAll(){
        return receiptStatusRepository.findAll();
    }
}
