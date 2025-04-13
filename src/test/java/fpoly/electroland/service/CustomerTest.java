package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Customer;
import fpoly.electroland.model.TypeCustomer;
import fpoly.electroland.repository.CustomerRepository;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.repository.TypeCustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {
    
    @Mock
    CustomerRepository customerRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    TypeCustomerRepository typeCustomerRepository;

    @Mock
    TypeCustomerService typeCustomerService;

    @InjectMocks
    CustomerService customerService; 

    private Customer existingCustomer;

    @BeforeEach
    void setUp() {
        try {
            existingCustomer = new Customer();
            existingCustomer.setId(1);
            existingCustomer.setAvatar(null);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf.parse("2024-10-13"); 
            existingCustomer.setDateOfBirth(dateOfBirth);
            existingCustomer.setEmail("daotankiet@gmail.com");
            existingCustomer.setFullName("Đào Tấn Kiệt");
            existingCustomer.setGender(true);
            existingCustomer.setPassword("123");
            existingCustomer.setPhoneNumber("0987654321");
            existingCustomer.setStatus(true); 
        } catch (ParseException e) {
            e.printStackTrace(); 
        }
    }

    @Test
    void findCustomerByIdTest() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(existingCustomer));
        Optional<Customer> result = customerService.findCustomerById(1);
        assertAll("Customer checks", 
            () -> assertTrue(result.isPresent(), "Customer should be present"),
            () -> assertEquals(existingCustomer, result.get(), "The customer returned should match the expected customer")
        );
    }

    @Test
    void findCustomerByIdNotFound() {
        when(customerRepository.findById(999)).thenReturn(Optional.empty());
        Optional<Customer> result = customerService.findCustomerById(999);
        assertFalse(result.isPresent(), "Customer should not be present");
        System.out.println("Test completed: Customer is not found");
    }

    @Test
    void getCustomerByEmail(){
        when(customerRepository.findByEmail("daotankiet@gmail.com")).thenReturn(Optional.of(existingCustomer));
        Optional<Customer> result = customerService.getCustomer("daotankiet@gmail.com");
        assertTrue(result.isPresent(), "Customer should be present");
        assertEquals(existingCustomer, result.get(), "The customer returned should match the expected customer");
    }

    @Test
    void getCustomerByEmailNotFound() {
        when(customerRepository.findByEmail("nonexistentemail@gmail.com")).thenReturn(Optional.empty());
        Optional<Customer> result = customerService.getCustomer("nonexistentemail@gmail.com");
        assertFalse(result.isPresent(), "Customer should not be present");
        System.out.println("Test completed: Customer is not found");
    }

    @Test
    void getCustomerById(){
        when(customerRepository.findById(1)).thenReturn(Optional.of(existingCustomer));
        Optional<Customer> result = customerService.getCustomer(1);
        assertTrue(result.isPresent(), "Customer should be present");
        assertEquals(existingCustomer, result.get(), "The customer returned should match the expected customer");
    }

    @Test
    void getCustomerByIdNotFound(){
        when(customerRepository.findById(0)).thenReturn(Optional.empty());
        Optional<Customer> result = customerService.getCustomer(0);
        assertFalse(result.isPresent(),"Customer should not be present");
        System.out.println("Test completed: Customer is not found");
    }

    @Test
    void createCustomerSuccess() {
        Customer customer = new Customer();
        when(customerRepository.save(any())).thenReturn(customer);
        when(typeCustomerService.getTypeCustomer(anyInt())).thenReturn(new TypeCustomer()); 
        Customer result = customerService.createCustomer(customer);
        assertNotNull(result, "The product coupon should not be null");
    }

    @Test
    void createCustomerFail() {
        Customer customer = new Customer();
        when(customerRepository.save(any())).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> customerService.createCustomer(customer), "Creating customer should throw an exception");
    }

    @Test
    void getListCustomerNull() {
        when(customerService.getAll()).thenReturn(null);
        List<Customer> result = customerService.getAll();
        assertNull(result, "The list of customer should be null");
    }

    @Test
    void getListCustomerEmpty() {
        List<Customer> list = new ArrayList<>();
        when(customerService.getAll()).thenReturn(list);
        List<Customer> result = customerService.getAll();
        assertTrue(result.isEmpty(), "The list of customer should be empty");
    }

    @Test
    void searchCustomersSuccess(){
        when(customerRepository.searchByKeyword("daotankiet@gmail.com")).thenReturn(List.of(existingCustomer));
        List<Customer> result = customerService.searchCustomers("daotankiet@gmail.com");
        assertNotNull(result, "The list of customer should not be null");
        assertEquals(1, result.size(), "The list of customer should have 1 element");
    }

    @Test
    void searchCustomersNotFound() {
        when(customerRepository.searchByKeyword("nonexistent")).thenReturn(new ArrayList<>());
        List<Customer> result = customerService.searchCustomers("nonexistent");
        assertTrue(result.isEmpty(), "The list of customers should be empty");
    }

    @Test
    void filterCustomersByStatusSuccess() {
        when(customerRepository.findByStatus(true)).thenReturn(List.of(existingCustomer));
        List<Customer> result = customerService.filterCustomersByStatus(true);
        assertNotNull(result, "The list of customer should not be null");
        assertEquals(1, result.size(), "The list of customer should have 1 element");
    }

    @Test
    void filterCustomersByStatusNotFound() {
        when(customerRepository.findByStatus(false)).thenReturn(new ArrayList<>());
        List<Customer> result = customerService.filterCustomersByStatus(false);
        assertTrue(result.isEmpty(), "The list of customers should be empty");
    }

    @Test
    void searchAndFilterCustomers(){
        when(customerRepository.searchAndFilter("daotankiet@gmail.com", true)).thenReturn(List.of(existingCustomer));
        List<Customer> result = customerService.searchAndFilterCustomers("daotankiet@gmail.com", true);
        assertNotNull(result, "The list of customer should not be null");
        assertEquals(1, result.size(), "The list of customer should have 1 element");
    }

    @Test
    void searchAndFilterCustomersNotFound() {
        when(customerRepository.searchAndFilter("nonexistent", false)).thenReturn(new ArrayList<>());
        List<Customer> result = customerService.searchAndFilterCustomers("nonexistent", false);
        assertTrue(result.isEmpty(), "The list of customers should be empty");
    }

    @Test
    void updateCustomer(){
        Customer customer = new Customer();
        when(customerRepository.save(any())).thenReturn(customer);
        Customer result = customerService.updateCustomer(customer);
        assertNotNull(result, "The customer should not be null");
    }

    @Test
    void updateCustomerFail() {
        Customer customer = new Customer();
        when(customerRepository.save(any())).thenThrow(new RuntimeException("Update failed"));
        assertThrows(RuntimeException.class, () -> customerService.updateCustomer(customer), "Updating customer should throw an exception");
    }
}
