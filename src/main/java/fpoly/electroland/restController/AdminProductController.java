package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Product;
import fpoly.electroland.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getListProduct() {
        return productService.getProduct();
    }
    
}
