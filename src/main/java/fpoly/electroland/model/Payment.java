package fpoly.electroland.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date successTime;

    Double amount;

    String content;

    @ManyToOne
    @JoinColumn(name = "idPaymentType", nullable = false)
    PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "idPaymentStatus", nullable = false)
    PaymentStatus paymentStatus;
}
