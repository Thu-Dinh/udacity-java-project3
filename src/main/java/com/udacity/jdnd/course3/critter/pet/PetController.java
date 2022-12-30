package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.createPet(convertPetDTO2Pet(petDTO));
        return convertPet2PetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getById(petId);
        return convertPet2PetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> petList = petService.getAll();
        return petList.stream()
                .map(this::convertPet2PetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.getByOwner(ownerId);
        return petList.stream()
                .map(this::convertPet2PetDTO)
                .collect(Collectors.toList());
    }

    private PetDTO convertPet2PetDTO(Pet pet) {
        if (Objects.isNull(pet)) return null;
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    private Pet convertPetDTO2Pet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        pet.setCustomer(new Customer(petDTO.getOwnerId()));

        return pet;
    }


}
