package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Color;
import fpoly.electroland.repository.ColorRepository;

@Service
public class ColorService {

    @Autowired
    ColorRepository colorRepository;

    public Optional<Color> getColorById(Integer id){
        return colorRepository.findById(id);
    }
}
