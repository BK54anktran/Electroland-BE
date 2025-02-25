package fpoly.electroland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(columnDefinition = "varchar(225)", nullable = false)
	String link;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;

	@Override
	public String toString() {
		return "ProductImg [id=" + id + ", link=" + link + ", product=" + product.id + "]";
	}

	
}
