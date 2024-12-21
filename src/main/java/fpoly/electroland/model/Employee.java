package fpoly.electroland.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String fullName;

	@Column(nullable = false, columnDefinition = "varchar(15)")
	String phoneNumber;

	@Column(nullable = true, columnDefinition = "nvarchar(225)")
	String role;

	@Column(nullable = false, columnDefinition = "varchar(225)")
	String email;

	@Column(nullable = false, columnDefinition = "varchar(225)")
	String password;

	@Column(nullable = true)
	Boolean status = true;

	@OneToMany(mappedBy = "employee")
	private List<EmployeeAuthority> employeeAuthority;

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", fullName='" + fullName + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", role='" + role + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", status=" + status +
				'}';
	}

}
