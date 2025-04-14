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

	Date time = new Date();

	@Column(nullable = false, columnDefinition = "nvarchar(max)")
	String oldValue;

	@Column(nullable = false, columnDefinition = "nvarchar(max)")
	String newValue;

	@ManyToOne
	@JoinColumn(name = "idEmployee", nullable = false)
	Employee employee;
}
