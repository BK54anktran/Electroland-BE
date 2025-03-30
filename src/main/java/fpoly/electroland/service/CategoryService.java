package fpoly.electroland.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.CategoryRepository;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.util.CreateAction;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CreateAction createAction;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category, int userId){
        Category save = categoryRepository.save(category);
        Employee creator = employeeRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        createAction.createAction("Category", "CREATE", save.getId(), null, 
        save.toString(), creator);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updateCategory, int userId) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            
            existingCategory.setImg(updateCategory.getImg());
            existingCategory.setName(updateCategory.getName());

            Category saved = categoryRepository.save(existingCategory);

            Employee creatorEmployee = employeeRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            createAction.createAction("Supplier", "UPDATE", saved.getId(), optionalCategory.toString(), updateCategory.toString(), creatorEmployee);
            return saved;
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    public Category findBCategoryId(int id){
        return categoryRepository.findById(id).get();
    }
}
