package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Category;
import fpoly.electroland.service.CategoryService;

@RestController
public class CategoryRest {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }
}
