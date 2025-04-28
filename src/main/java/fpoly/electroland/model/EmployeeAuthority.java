package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	@JoinColumn(name = "idEmployee", nullable = false)
	Employee employee;

	@ManyToOne
	@JoinColumn(name = "idAuthority", nullable = false)
	Authority authority;
}