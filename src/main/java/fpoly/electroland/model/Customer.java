package fpoly.electroland.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
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

	// Constructor mặc định (chỉ với email và password)
	public Customer(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(columnDefinition = "nvarchar(225)")
	String fullName;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date dateOfBirth;

	@Column(columnDefinition = "varchar(15)")
	String phoneNumber;

	Boolean gender;

	@Column(columnDefinition = "varchar(225)")
	String email;

	@Column(columnDefinition = "varchar(225)")
	String password;

	@Column(columnDefinition = "varchar(225)")
	String avatar;

	@Column()
	Boolean status;

	@ManyToOne
	@JoinColumn(name = "idTypeCustomer")
	private TypeCustomer typeCustomer;

	@Column
	Integer userPoint;
}