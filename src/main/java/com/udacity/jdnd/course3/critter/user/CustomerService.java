package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Transactional
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer getCustomerByPet(Long petId) {
        Pet pet = petRepository.findById(petId).get();
        if (pet == null) return null;
        return pet.getCustomer();
    }
}
