package fpoly.electroland.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String tableName;

	String action;

	int idRecord;

	Date time;

	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String oldValue;

	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String newValue;

	@ManyToOne
	@JoinColumn(name = "idEmployee", nullable = false)
	Employee employee;
}
