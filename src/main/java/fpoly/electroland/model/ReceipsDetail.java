package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceipsDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = true, columnDefinition = "NVARCHAR(225)")
	String description;

	@ManyToOne
	@JoinColumn(name = "idProductCoupon")
	private ProductCoupon productCoupon;

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "idOder", nullable = false)
	private Receips receips;
}
