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

	private Integer provinceID;

	private Integer districtID;

	@Column(columnDefinition = "NVARCHAR(10)")
	private String wardCode;

	@Column(columnDefinition = "NVARCHAR(255) ")
	private String street;

	@Column(columnDefinition = "NVARCHAR(255) ")
	private String stringAddress;

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
