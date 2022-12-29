package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee creatEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Transactional
    public void updateEmployee(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        if (employee == null) {
            // not found employee
            throw new IllegalArgumentException();
        }
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.saveAndFlush(employee);
    }

    public Employee getById(long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public List<Employee> getAvailableEmployeeBySkillAndDate(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees.stream().filter(e -> e.getSkills().containsAll(skills))
                .filter(e -> e.getDaysAvailable().contains(date.getDayOfWeek()))
                .collect(Collectors.toList());
    }
}
