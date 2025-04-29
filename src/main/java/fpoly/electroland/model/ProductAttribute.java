package fpoly.electroland.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(columnDefinition = "nvarchar(225)")
	String name;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;

	@OneToMany(mappedBy = "productAttribute")
	List<Attribute> attributes;

	@Override
	public String toString() {
		return "ProductAttribute [id=" + id + ", name=" + name + ", product=" + product.id
				+ "]";
	}
}
