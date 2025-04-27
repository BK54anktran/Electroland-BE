package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReceiptCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column()
	Double discountMoney;

	@Column()
	Double discountPercent;

	@Column()
	Double maxDiscount;

	@Column()
	Double minReceiptPrice;

	@Column(columnDefinition = "nvarchar(225)")
	String description;

	@Column()
	Boolean status = true;

	@Column()
	Integer point;
}
