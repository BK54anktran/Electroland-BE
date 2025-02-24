package fpoly.electroland.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
   

    public List<Object[]> getSalesDataByMonthRange(LocalDateTime compareDate) {
        // Check if compareDate is null
        if (compareDate == null) {
            throw new IllegalArgumentException("The compareDate parameter cannot be null");
        }
        
        // Convert LocalDateTime to LocalDate (remove time portion)
        int month = compareDate.getMonthValue();
        int year = compareDate.getYear();
        LocalDateTime end = compareDate;
        
        // Calculate the start of the month (01/MM/YYYY)
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);  // Get the first day of the month
        // Call the Repository to get data
        System.out.println("start: " + start);
        System.out.println("end: " + end);
        return receiptDetailRepository.findSalesDataByDateRange(start, end);
    }
    
}
