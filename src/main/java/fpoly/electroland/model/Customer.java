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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(columnDefinition = "nvarchar(225)", nullable = false)
	@NotBlank(message = "Họ tên không được để trống")
	String fullName;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Ngày sinh không được để trống")
	Date dateOfBirth;

	@Column(columnDefinition = "varchar(15)", nullable = true)
	@NotBlank(message = "Số điện thoại không được để trống")
	@Pattern(regexp = "\\d{10,15}", message = "Số điện thoại không đúng định dạng")
	String phoneNumber;

	Boolean gender;

	@Column(columnDefinition = "varchar(225)", nullable = true)
	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không đúng định dạng")
	String email;

	@Column(columnDefinition = "varchar(225)", nullable = false)
	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Mật khẩu phải chứa ít nhất một chữ cái, một số và một ký tự đặc biệt")
	String password;

	@Column(columnDefinition = "varchar(225)", nullable = true)
	String avatar;

	@Column(nullable = true)
	Boolean status;

	@ManyToOne
	@JoinColumn(name = "idTypeCustomer", nullable = true)
	private TypeCustomer typeCustomer;

}
