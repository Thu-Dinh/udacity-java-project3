package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Pet createPet(Pet pet) {
        Customer customer = customerRepository.findById(pet.getCustomer().getId()).get();
        pet.setCustomer(customer);
        Pet newPet = petRepository.saveAndFlush(pet);
        customer.addPet(newPet);
        return newPet;
    }

    @Transactional
    public Pet getById(Long petId) {
        return petRepository.findById(petId).get();
    }

    @Transactional
    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    @Transactional
    public List<Pet> getByOwner(Long ownerId) {
        return petRepository.findByOwner(ownerId);
    }
}
