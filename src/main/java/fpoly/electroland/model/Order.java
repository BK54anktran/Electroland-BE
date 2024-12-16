package fpoly.electroland.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)")
	private String address;

	@NotEmpty(message = "Vui lòng nhập thông tin người nhận")
	@Column(nullable = false, columnDefinition = "NVARCHAR(225)")
	private String nameReciver;

	@NotEmpty(message = "Vui lòng nhập số điện thoại người nhận")
	@Column(nullable = false, columnDefinition = "VARCHAR(15)")
	private String phoneReciver;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(nullable = false)
	private Date orderDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date deliveryDate;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)")
	private String note;

	@ManyToOne
	@JoinColumn(name = "idStatus", nullable = true)
	private OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name = "idPayment", nullable = false)
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "idOrderCoupon", nullable = true)
	private OrderCoupon orderCoupon;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	private Customer customer;
}
