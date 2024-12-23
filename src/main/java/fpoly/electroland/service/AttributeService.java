package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import fpoly.electroland.model.Attribute;
import fpoly.electroland.repository.AttributeReponsitory;

@Service
public class AttributeService {

    @Autowired
    AttributeReponsitory attributeReponsitory;

    public Attribute getAttributeById(int id){
        return attributeReponsitory.findById(id).get();
    }
}
