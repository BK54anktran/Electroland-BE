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

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)")
	private String nameReciver;

	@Column(nullable = false, columnDefinition = "VARCHAR(15)")
	private String phoneReciver;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")

	private Date receiptDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date deliveryDate;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)")
	private String note;

	@ManyToOne
	@JoinColumn(name = "idReceiptStatus", nullable = true)
	private ReceiptStatus receiptStatus;

	@ManyToOne
	@JoinColumn(name = "idPayment", nullable = true)
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "idReceiptCoupon", nullable = true)
	private ReceiptCoupon ReceiptCoupon;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	private Customer customer;
	@Column(nullable = true)
	private Boolean isRead = false;
}