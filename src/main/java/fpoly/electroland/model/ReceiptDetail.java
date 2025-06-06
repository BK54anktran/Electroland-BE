package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ReceiptDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int quantity;
	private Double price;

	@Column(columnDefinition = "NVARCHAR(225)")
	String description;

	@ManyToOne
	@JoinColumn(name = "idProductCoupon")
	private ProductCoupon productCoupon;

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "idReceipt", nullable = false)
	private Receipt receipt;
}