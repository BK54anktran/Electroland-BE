package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ProductImgReponsitory;

@Service
public class ProductImgService {

    @Autowired
    ProductImgReponsitory productImgReponsitory;

}
