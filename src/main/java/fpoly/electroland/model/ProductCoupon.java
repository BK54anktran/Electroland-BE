package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	Double value;

	@Column
	Integer redemptionCost;

	@Column(columnDefinition = "nvarchar(225)", nullable = true)
	String description;

	@Column(nullable = false)
	Boolean status = true;

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;
}
