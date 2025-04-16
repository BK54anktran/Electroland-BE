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

	@Column(columnDefinition = "nvarchar(225)")
	String description;

	@Column()
	Boolean status = true;

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;

	@Column()
	Integer point;
}
