package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Product;
import fpoly.electroland.repository.CategoryReponsitory;

@Service
public class CategoryService {

    @Autowired
    CategoryReponsitory categoryReponsitory;

    public List<Category> getCategories() {
        return categoryReponsitory.findAll();
    }
}
