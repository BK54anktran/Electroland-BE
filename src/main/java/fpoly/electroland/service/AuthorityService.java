package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.AuthorityRepository;

@Service
public class AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

}
