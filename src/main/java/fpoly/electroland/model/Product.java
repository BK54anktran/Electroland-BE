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

	@Column(columnDefinition = "nvarchar(225)")
	String name;

	@Column(columnDefinition = "varchar(225)")
	String avatar;

	@Column(columnDefinition = "nvarchar(MAX)")
	String description;

	Double price;

	@Column()
	Double priceDiscount;

	Boolean status = true;

	@Column()
	Integer weight;

	@Column()
	Integer length;

	@Column()
	Integer width;

	@Column()
	Integer height;

	@ManyToOne
	@JoinColumn(name = "idCategory", nullable = false)
	Category category;

	@ManyToOne
	@JoinColumn(name = "idSupplier", nullable = false)
	Supplier supplier;

	@OneToMany(mappedBy = "product")
	List<ProductImg> productImgs;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	List<ProductAttribute> productAttributes;
}
