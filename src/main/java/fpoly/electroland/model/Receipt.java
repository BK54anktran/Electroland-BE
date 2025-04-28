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

public class Receipt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "NVARCHAR(225)")
	private String address;

	@Column(columnDefinition = "NVARCHAR(225)")
	private String nameReciver;

	@Column(columnDefinition = "VARCHAR(15)")
	private String phoneReciver;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")

	private Date receiptDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date deliveryDate;

	@Column(columnDefinition = "NVARCHAR(225)")
	private String note;

	@ManyToOne
	@JoinColumn(name = "idReceiptStatus")
	private ReceiptStatus receiptStatus;

	@ManyToOne
	@JoinColumn(name = "idPayment")
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "idReceiptCoupon")
	private ReceiptCoupon ReceiptCoupon;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	private Customer customer;
	
	@Column()
	private Boolean isRead = false;
}