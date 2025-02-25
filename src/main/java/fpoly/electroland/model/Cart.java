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
@Table
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	int quantity;

	@Column(nullable = true, columnDefinition = "nvarchar(225)")
	String description;

	@Column(nullable = true)
	Boolean status;

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	Customer customer;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
	List<CartProductAttribute> cartProductAttributes;

	@Override
	public String toString() {
		return "Cart [id=" + id + ", quantity=" + quantity + ", description=" + description + ", status=" + status
				+ ", product=" + product.getId() + ", customer=" + customer.getId() + "]";
	}

}
