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

	@Column(nullable = false, columnDefinition = "NVARCHAR(5)")
	private String idCity;
	
	@Column(nullable = false, columnDefinition = "NVARCHAR(255)")
	private String nameCity;

	@Column(nullable = false, columnDefinition = "NVARCHAR(5)")
	private String idDistrict;
	
	@Column(nullable = false, columnDefinition = "NVARCHAR(255)")
	private String nameDistrict;

	@Column(nullable = false, columnDefinition = "NVARCHAR(10)")
	private String idWard;

	@Column(nullable = false, columnDefinition = "NVARCHAR(255) ")
	private String nameWard;

	@Column(nullable = false, columnDefinition = "NVARCHAR(255) ")
	private String street;

	@Column(nullable = true)
	private boolean status;

	@Column(nullable = true, columnDefinition = "NVARCHAR(100)")
	private String addressType;
	@Column(nullable = true, columnDefinition = "NVARCHAR(100)")
	private String nameReciever;

	@Column(nullable = true, columnDefinition = "NVARCHAR(100)")
	private String phoneReciever;

	@ManyToOne
	@JoinColumn(name = "idCustomer", nullable = false)
	private Customer customer;

}
