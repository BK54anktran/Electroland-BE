package fpoly.electroland.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String name;

	@Column(nullable = false, columnDefinition = "varchar(225)")
	String avatar;

	@Column(nullable = true, columnDefinition = "nvarchar(225)")
	String description;

	@Column(nullable = false)
	Double price;

	@Column(nullable = true)
	Double priceDiscount;

	@Column(nullable = false)
	Boolean status = true;

	@ManyToOne
	@JoinColumn(name = "idCategory", nullable = false)
	Category category;

	@ManyToOne
	@JoinColumn(name = "idSupplier", nullable = false)
	Supplier supplier;


	@OneToMany(mappedBy = "product")
	List<ProductImg> productImgs;

	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
	List<ProductAttribute> productAttributes;
}
