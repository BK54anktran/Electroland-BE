package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.CategoryReponsitory;

@Service
public class CategoryService {

    @Autowired
    CategoryReponsitory categoryReponsitory;

}
