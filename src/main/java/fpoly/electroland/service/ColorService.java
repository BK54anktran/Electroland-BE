package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ColorRepository;

@Service
public class ColorService {

    @Autowired
    ColorRepository colorRepository;

}
