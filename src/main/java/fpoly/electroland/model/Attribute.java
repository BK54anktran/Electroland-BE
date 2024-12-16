package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;

	Double AttributePrice = 0.0;

	@Column(nullable = false, columnDefinition = "nvarchar(225)")

	@ManyToOne
	@JoinColumn(name = "idProductAtt", nullable = false)
	ProductAttribute productAttribute;
}
