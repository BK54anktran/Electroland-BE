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

	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String name;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;

	@OneToMany(mappedBy = "productAttribute")
	List<Attribute> attributes;
}
