/*
 * This program is used to manage the members and employees
 * of Bahrain Polytechnic Gym
 * Authors: 202102147 Sayed Hussain, 202101456 Ali Alfardan, 202000237 Omar Ali 
 */
package Logic_GymSystem;

import java.io.Serializable;

/**
 * Class that contains all the required attributes for a Staff, also extends
 * Member Class.
 */
public class Staff extends Member implements Serializable {

    private String position;
    private String department;

    public Staff(String position, String department, int member_ID, String firstName, String lastName, String gender, String phone, String address, String DOB) {
        super(member_ID, firstName, lastName, gender, phone, address, DOB);
        this.position = position;
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
