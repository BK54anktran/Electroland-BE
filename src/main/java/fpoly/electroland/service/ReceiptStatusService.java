package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReceiptStatusReponsitory;

@Service
public class ReceiptStatusService {

    @Autowired
    ReceiptStatusReponsitory receiptStatusReponsitory;

}
