package fpoly.electroland.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * Chuyển đổi định dạng ngày tháng từ chuỗi ISO 8601 sang đối tượng java.sql.Date theo định dạng yyyy-MM-dd
     *
     * @param dateOfBirth chuỗi ngày tháng ở định dạng ISO 8601
     * @return đối tượng java.sql.Date ở định dạng yyyy-MM-dd
     */
    public static Date formatDate(String dateOfBirth) {
         // Loại bỏ chữ "Z" khỏi chuỗi
         if (dateOfBirth.endsWith("Z")) {
            dateOfBirth = dateOfBirth.substring(0, dateOfBirth.length() - 1);
        }
         // Định dạng chuỗi ngày tháng theo định dạng yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);

        // Chuyển đổi LocalDate sang java.sql.Date
        return Date.valueOf(localDate.plusDays(1));

        
    }

}

