package fpoly.electroland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "idSP", "idMau" }))
public class ProductColor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	Double colorPrice = 0.0;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;

	@ManyToOne
	@JoinColumn(name = "idColor", nullable = false)
	Color color;

	@Override
	public String toString() {
		return "ProductColor [id=" + id + ", colorPrice=" + colorPrice + ", product=" + product.id + ", color=" + color.id
				+ "]";
	}

	
}
