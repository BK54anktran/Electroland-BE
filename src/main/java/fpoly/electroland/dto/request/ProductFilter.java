package fpoly.electroland.dto.request;

import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class ProductFilter {
    private String key;
    private int category;
    private int minPrice;
    private int maxPrice;
    private List<Integer> supplier;
    private Sort sort;
}
