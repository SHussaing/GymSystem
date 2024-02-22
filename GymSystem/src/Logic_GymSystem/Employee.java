/*
 * This program is used to manage the members and employees
 * of Bahrain Polytechnic Gym
 * Authors: 202102147 Sayed Hussain, 202101456 Ali Alfardan, 202000237 Omar Ali 
 */
package Logic_GymSystem;

import java.io.Serializable;

/**
 * Class that contains all the required attributes for an employee.
 */
public class Employee implements Serializable {

    private final int employee_ID;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String address;
    private String DOB;
    private double salary;

    public Employee(int employee_ID, String firstName, String lastName, String gender, String phone, String address, String DOB, double salary) {
        this.employee_ID = employee_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.DOB = DOB;
        this.salary = salary;
    }

    public int getEmployee_ID() {
        return employee_ID;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
