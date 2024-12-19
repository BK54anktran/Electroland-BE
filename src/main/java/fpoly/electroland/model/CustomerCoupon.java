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
public class CustomerCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	Date expiredDate;

	Boolean status;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	Customer customer;

	@ManyToOne
	@JoinColumn(name = "idReceipsCoupon", nullable = true)
	ReceipsCoupon receipsCoupon;

	@ManyToOne
	@JoinColumn(name = "idProductCoupon", nullable = true)
	ProductCoupon productCoupon;

}
