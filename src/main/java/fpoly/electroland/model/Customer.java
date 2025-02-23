package fpoly.electroland.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	public Customer(String fullName,
			Date dateOfBirth,
			String phoneNumber,
			Boolean gender,
			String email,
			String password) {
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.email = email;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(columnDefinition = "nvarchar(225)", nullable = false)
	String fullName;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date dateOfBirth;

	@Column(columnDefinition = "varchar(15)", nullable = true)
	String phoneNumber;

	Boolean gender;

	@Column(columnDefinition = "varchar(225)", nullable = true)
	String email;

	@Column(columnDefinition = "varchar(225)", nullable = false)
	String password;

	@Column(columnDefinition = "varchar(225)", nullable = true)
	String avatar;

	@Column(nullable = true)
	Boolean status;

	@ManyToOne
	@JoinColumn(name = "idTypeCustomer", nullable = true)
	private TypeCustomer typeCustomer;

	@Column
	Integer userPoint;

}
