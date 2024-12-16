package fpoly.electroland.model;

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

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	Product product;
}
