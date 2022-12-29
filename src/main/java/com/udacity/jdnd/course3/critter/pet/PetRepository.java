package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p where p.customer.id =:ownerId")
    List<Pet> findByOwner(@Param("ownerId") long ownerId);

    @Query(value = "SELECT * FROM Pet p " +
            "WHERE p.pet_id in (:ids) ", nativeQuery = true)
    List<Pet> findByIds(@Param("ids") List<Long> ids);
}
