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

	@Column(nullable = true)
	Double discountMoney;

	@Column(nullable = true)
	Double discountPercent;

	@Column(nullable = true)
	Double maxDiscount;

	@Column(nullable = true)
	Double minReceiptPrice;

	@Column(columnDefinition = "nvarchar(225)", nullable = true)
	String description;

	@Column(nullable = true)
	Boolean status = true;

	@Column(nullable = true)
	Integer point;

	@Column
	Integer redemptionCost;
}
