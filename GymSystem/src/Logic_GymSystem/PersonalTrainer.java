/*
 * This program is used to manage the members and employees
 * of Bahrain Polytechnic Gym
 * Authors: 202102147 Sayed Hussain, 202101456 Ali Alfardan, 202000237 Omar Ali 
 */
package Logic_GymSystem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that contains all the required attributes for a Personal Trainer, also
 * extends Employee Class.
 */
public class PersonalTrainer extends Employee implements Serializable {

    private ArrayList<Member> members;

    public PersonalTrainer(int employee_ID, String firstName, String lastName, String gender, String phone, String address, String DOB, double salary) {
        super(employee_ID, firstName, lastName, gender, phone, address, DOB, salary);
        members = new ArrayList();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

}
