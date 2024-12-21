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
public class Attribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String name;

	Double AttributePrice = 0.0;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idProductAtt", nullable = false)
	ProductAttribute productAttribute;
}
