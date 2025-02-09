package fpoly.electroland.model;

import java.util.Date;
import java.util.List;

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
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = true)
	private int point = 5;

	@Column(nullable = true, columnDefinition = "NVARCHAR(225)")
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date = new Date();

	private Boolean status = true;

	@OneToMany(mappedBy = "review")
    private List<ReviewImg> imgs;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "idProduct", nullable = false)
	private Product product;
}
