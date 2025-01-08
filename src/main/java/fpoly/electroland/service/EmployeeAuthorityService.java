package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.EmployeeAuthorityRepository;

@Service
public class EmployeeAuthorityService {

    @Autowired
    EmployeeAuthorityRepository employeeAuthorityRepository;

}
