package com.example.webfluxcrud.annotated_controller.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Employee {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private Double bonus;

    public Employee() {
    }

    public Employee(String id, String firstName, String lastName, Double bonus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bonus = bonus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) && Objects.equals(bonus, employee.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, bonus);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bonus=" + bonus +
                '}';
    }
}
