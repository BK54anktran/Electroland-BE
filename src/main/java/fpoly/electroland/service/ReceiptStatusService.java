package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReceiptStatusRepository;

@Service
public class ReceiptStatusService {

    @Autowired
    ReceiptStatusRepository receiptStatusRepository;

}
