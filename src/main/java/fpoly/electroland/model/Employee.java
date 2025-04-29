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

	@Column(columnDefinition = "nvarchar(225)")
	String fullName;

	@Column(columnDefinition = "varchar(15)")
	String phoneNumber;

	@Column(columnDefinition = "nvarchar(225)")
	String role;

	@Column(columnDefinition = "varchar(225)")
	String email;

	@Column(columnDefinition = "varchar(225)")
	String password;

	@Column()
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