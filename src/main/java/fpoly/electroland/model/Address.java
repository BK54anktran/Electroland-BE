package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column( columnDefinition = "NVARCHAR(255)")
	private String nameCity;
	
	@Column( columnDefinition = "NVARCHAR(255)")
	private String nameDistrict;

	@Column( columnDefinition = "NVARCHAR(255) ")
	private String nameWard;

	@Column(nullable = false, columnDefinition = "NVARCHAR(255) ")
	private String street;

	private boolean status;

	@Column(columnDefinition = "NVARCHAR(100)")
	private String addressType;

	@Column(columnDefinition = "NVARCHAR(100)")
	private String nameReciever;

	@Column(columnDefinition = "NVARCHAR(100)")
	private String phoneReciever;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	private Customer customer;

}
