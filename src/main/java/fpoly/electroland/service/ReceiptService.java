package fpoly.electroland.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Receipt;
import fpoly.electroland.model.ReceiptDetail;
import fpoly.electroland.repository.ReceiptDetailRepository;
import fpoly.electroland.repository.ReceiptRepository;

@Service
public class ReceiptService {

    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    private ReceiptDetailRepository receiptDetailRepository;
     public List<Receipt> getAll() {
        return receiptRepository.findAll();
    }
  

   // Sửa phương thức getReceiptById để nhận tham số kiểu Long
   public Optional<Receipt> getReceiptById(Long receiptId) {
    return receiptRepository.findById(receiptId);  // Phương thức này đã trả về Optional<Receipt>
}

// Sửa phương thức getReceiptDetailsByReceiptId để nhận tham số kiểu Long
public List<ReceiptDetail> getReceiptDetailsByReceiptId(Long receiptId) {
    return receiptDetailRepository.findByReceiptId(receiptId);
}


 public List<Receipt> getReceiptsByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        return receiptRepository.findByDateRange(startDateTime, endDateTime);
    }

public List<Receipt> searchReceipts(String searchKey) {
    return receiptRepository.searchReceipts(searchKey);
}


}



