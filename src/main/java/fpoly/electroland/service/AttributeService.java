package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import fpoly.electroland.model.Attribute;

import fpoly.electroland.repository.AttributeRepository;

@Service
public class AttributeService {

    @Autowired
    AttributeRepository attributeRepository;

    public Attribute getAttributeById(int id){
        return attributeRepository.findById(id).get();
    }
}
