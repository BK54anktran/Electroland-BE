package fpoly.electroland.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Category;
import fpoly.electroland.repository.CategoryReponsitory;

@Service
public class CategoryService {
    @Autowired
    CategoryReponsitory categoryReponsitory;

    public List<Category> getAll() {
        return categoryReponsitory.findAll();
    }
}
