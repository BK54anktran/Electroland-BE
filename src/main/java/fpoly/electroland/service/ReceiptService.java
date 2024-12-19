package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReceiptReponsitory;

@Service
public class ReceiptService {

    @Autowired
    ReceiptReponsitory receiptReponsitory;

}
