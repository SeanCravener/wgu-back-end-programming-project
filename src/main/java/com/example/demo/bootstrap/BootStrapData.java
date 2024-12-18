package com.example.demo.bootstrap;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // If statement needed to make sure sample customers to do get overwritten.
        // Database is created with one initial customer, so 1 is needed in If Statement instead of 0 (empty).
        if(customerRepository.count() == 1) {
            Division div_one = divisionRepository.findAll().get(1);
            Division div_two = divisionRepository.findAll().get(2);

            Customer chewy = new Customer("Chewy", "Cravener", "123 Treats Ave", "112233", "7242238273", div_one);
            Customer laney = new Customer("Laney", "Kohler", "777 Barks Blvd", "384732", "237-384-3829", div_two);
            Customer kirsten = new Customer("Kirsten", "Bones", "483 Fine Street", "849202", "837-398-3889", div_one);
            Customer bella = new Customer("Bella", "Toy", "999 Loud Rd.", "394820", "090-884-9090", div_two);
            Customer sonic = new Customer("Sonic", "Cricket", "323 Lizard Lane", "876590", "444-888-6969", div_one);

            customerRepository.save(chewy);
            customerRepository.save(laney);
            customerRepository.save(kirsten);
            customerRepository.save(bella);
            customerRepository.save(sonic);
        }

            System.out.println("BootStrapData.java");
            System.out.println("Number of Customers" + customerRepository.count());
            System.out.println(customerRepository.findAll());

    }
}