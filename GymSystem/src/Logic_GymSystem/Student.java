/*
 * This program is used to manage the members and employees
 * of Bahrain Polytechnic Gym
 * Authors: 202102147 Sayed Hussain, 202101456 Ali Alfardan, 202000237 Omar Ali 
 */
package Logic_GymSystem;

import java.io.Serializable;

/**
 * Class that contains all the required attributes for a Student, also extends
 * Member Class.
 */
public class Student extends Member implements Serializable {

    private String course;
    private String team;

    public Student(String course, String team, int member_ID, String firstName, String lastName, String gender, String phone, String address, String DOB) {
        super(member_ID, firstName, lastName, gender, phone, address, DOB);
        this.course = course;
        this.team = team;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}
