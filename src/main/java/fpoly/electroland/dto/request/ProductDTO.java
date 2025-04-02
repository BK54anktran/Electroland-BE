package fpoly.electroland.dto.request;

import java.util.List;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.ProductAttribute;
import fpoly.electroland.model.ProductImg;
import fpoly.electroland.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String avatar;
    private String description;
    private Double price;
    private Double priceDiscount;
    private Boolean status;
    private Integer category;
    private Integer supplier;
    private Integer weight;
    private Integer length;
    private Integer width;
    private Integer height;
    private List<ProductImg> productImgs;
    private List<ProductAttribute> productAttributes;
}
