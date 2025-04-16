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

	@Column(columnDefinition = "nvarchar(225)")
	String name;

	Double AttributePrice = 0.0;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idProductAtt", nullable = false)
	ProductAttribute productAttribute;

	@Override
	public String toString() {
		return "Attribute [name=" + name + ", AttributePrice=" + AttributePrice + ", productAttribute="
				+ productAttribute + "]";
	}

}
