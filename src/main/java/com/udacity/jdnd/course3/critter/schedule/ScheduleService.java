package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;


    @Transactional
    public Schedule create(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        if (!CollectionUtils.isEmpty(employeeIds)) {
            List<Employee> employeeList = employeeRepository.findByIds(employeeIds);
            schedule.setEmployees(employeeList);
        }

        if (!CollectionUtils.isEmpty(petIds)) {
            List<Pet> petList = petRepository.findByIds(petIds);
            schedule.setPets(petList);
        }

        return scheduleRepository.saveAndFlush(schedule);
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPetId(long petId) {
        return scheduleRepository.findByPet(petId);
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId) {
        return scheduleRepository.findByEmployee(employeeId);
    }

    public List<Schedule> getScheduleByCustomerId(long customerId) {

        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Schedule> scheduleList = allSchedules.stream().filter(s -> containCustomer(s.getPets(), customerId)).collect(Collectors.toList());

        return scheduleList;
    }

    private boolean containCustomer(List<Pet> petList, long customerId) {
        return petList.stream().anyMatch(pet -> pet.getCustomer().getId() == customerId);
    }
}
