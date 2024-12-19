package fpoly.electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
