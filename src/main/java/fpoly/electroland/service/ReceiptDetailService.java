package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReceiptDetailReponsitory;

@Service
public class ReceiptDetailService {

    @Autowired
    ReceiptDetailReponsitory receiptDetailReponsitory;

}
