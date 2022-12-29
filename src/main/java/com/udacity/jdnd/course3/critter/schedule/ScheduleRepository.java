package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "SELECT s.* FROM Schedule s " +
            "JOIN schedule_pet sp ON s.schedule_id = sp.schedule_id " +
            "WHERE sp.pet_id =:petId ", nativeQuery = true)
    List<Schedule> findByPet(@Param("petId") long petId);

    @Query(value = "SELECT s.* FROM Schedule s " +
            "JOIN schedule_employee se ON s.schedule_id = se.schedule_id " +
            "WHERE se.employee_id =:employeeId ", nativeQuery = true)
    List<Schedule> findByEmployee(@Param("employeeId") long employeeId);

}
