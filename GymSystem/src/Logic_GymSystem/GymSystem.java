/*
 * This program is used to manage the members and employees
 * of Bahrain Polytechnic Gym
 * Authors: 202102147 Sayed Hussain, 202101456 Ali Alfardan, 202000237 Omar Ali 
 */
package Logic_GymSystem;

import java.util.*;
import java.io.*;

/**
 * Class that contains all logical attributes and methods to run the system and
 * to be serialized.
 */
public class GymSystem implements Serializable {

    private ArrayList<Employee> gymEmployees;
    private ArrayList<Member> gymMembers;
    private int employeeID;
    private int memberID;

    public GymSystem() {
        gymEmployees = new ArrayList();
        gymMembers = new ArrayList();
        employeeID = 101;
        memberID = 1001;
    }

    public ArrayList<Employee> getGymEmployees() {
        return gymEmployees;
    }

    public ArrayList<Member> getGymMembers() {
        return gymMembers;
    }

    /**
     * Name: addEmployee
     *
     * Purpose/description: Add a new employee to the system.
     *
     * @param fname @param lname @param gender @param phone @param addreess
     * @param DOB @param salary @param isPT
     * @return return the ID of the Employee.
     */
    public int addEmployee(String fname, String lname, String gender, String phone, String addreess, String DOB, double salary, boolean isPT) {
        if (isPT) {
            PersonalTrainer pt = new PersonalTrainer(employeeID, fname, lname, gender, phone, addreess, DOB, salary);
            gymEmployees.add(pt);
        } else {
            Employee emp = new Employee(employeeID, fname, lname, gender, phone, addreess, DOB, salary);
            gymEmployees.add(emp);
        }
        employeeID++;
        return employeeID - 1;
    }

    /**
     * Name: addMember
     *
     * Purpose/description: Add a new member to the system.
     *
     * @param fname @param lname @param gender @param phone @param addreess
     * @param DOB @param position_course @param isStaff @param department_team
     * @return return the ID of the Member.
     */
    public int addMember(String fname, String lname, String gender, String phone, String addreess, String DOB, String position_course, String department_team, boolean isStaff) {
        if (isStaff) {
            Staff staff = new Staff(position_course, department_team, memberID, fname, lname, gender, phone, addreess, DOB);
            gymMembers.add(staff);
        } else {
            Student student = new Student(position_course, department_team, memberID, fname, lname, gender, phone, addreess, DOB);
            gymMembers.add(student);
        }
        memberID++;
        return memberID - 1;
    }

    /**
     * Name: findMember
     *
     * Purpose/description: try to find the Member with the passed in ID
     *
     * @param ID Get the Member ID
     * @return return the member if found null if not.
     */
    public Member findMember(int ID) {
        boolean found = false;
        int count = 0;
        Member member = null;
        while (!found & count < gymMembers.size()) {
            if (gymMembers.get(count).getMember_ID() == ID) {
                found = true;
            } else {
                count++;
            }
        }
        if (found) {
            member = gymMembers.get(count);
            return member;
        } else {
            return member;
        }
    }

    /**
     * Name: findEmployee
     *
     * Purpose/description: try to find the Employee with the passed in ID
     *
     * @param ID Get the Employee ID
     * @return return the Employee if found null if not.
     */
    public Employee findEmployee(int ID) {
        boolean found = false;
        int count = 0;
        Employee employee = null;
        while (!found & count < gymEmployees.size()) {
            if (gymEmployees.get(count).getEmployee_ID() == ID) {
                found = true;
            } else {
                count++;
            }
        }
        if (found) {
            employee = gymEmployees.get(count);
            return employee;
        } else {
            return employee;
        }
    }

    /**
     * Name: editEmployeeDetails
     *
     * Purpose/description: edit Employee Details
     *
     * @param ID @param fname @param lname @param salary @param gender @param
     * phone @param DOB @param addreess
     *
     */
    public void editEmployeeDetails(int ID, String fname, String lname, String gender, String phone, String addreess, String DOB, double salary) {
        Employee emp = findEmployee(ID);
        emp.setFirstName(fname);
        emp.setLastName(lname);
        emp.setGender(gender);
        emp.setPhone(phone);
        emp.setAddress(addreess);
        emp.setDOB(DOB);
        emp.setSalary(salary);
    }

    /**
     * Name: editMemberDetails
     *
     * Purpose/description: edit Member Details
     *
     * @param ID @param fname @param lname @param gender @param phone @param
     * address @param DOB @param position_course @param department_team
     */
    public void editMemberDetails(int ID, String fname, String lname, String gender, String phone, String address, String DOB, String position_course, String department_team) {
        boolean found = false;
        int count = 0;
        while (!found & count < gymMembers.size()) {
            if (gymMembers.get(count).getMember_ID() == ID) {
                found = true;
            } else {
                count++;
            }
        }
        if (found) {
            gymMembers.get(count).setFirstName(fname);
            gymMembers.get(count).setLastName(lname);
            gymMembers.get(count).setGender(gender);
            gymMembers.get(count).setPhone(phone);
            gymMembers.get(count).setAddress(address);
            gymMembers.get(count).setDOB(DOB);
            if (gymMembers.get(count) instanceof Staff) {
                Staff staff = (Staff) gymMembers.get(count);
                staff.setPosition(position_course);
                staff.setDepartment(department_team);
            } else {
                Student student = (Student) gymMembers.get(count);
                student.setCourse(position_course);
                student.setTeam(department_team);
            }
        }
    }

    /**
     * Name: assignMemberToTrainer Purpose/description: add a member to a
     * trainers list of members
     *
     * @param member_ID
     * @param trainer_ID
     */
    public void assignMemberToTrainer(int member_ID, int trainer_ID) {
        PersonalTrainer trainer = (PersonalTrainer) findEmployee(trainer_ID);
        Member member = findMember(member_ID);
        trainer.getMembers().add(member);
        member.setTrainer(trainer);
    }

    /**
     * Name: removeMemberFromTrainer Purpose/description: remove a member from a
     * trainers list of members
     *
     * @param member_ID
     * @param trainer_ID
     */
    public void removeMemberFromTrainer(int member_ID, int trainer_ID) {
        PersonalTrainer trainer = (PersonalTrainer) findEmployee(trainer_ID);
        Member member = findMember(member_ID);
        boolean found = false;
        int i = 0;
        while (!found & i < trainer.getMembers().size()) {
            if (member_ID == trainer.getMembers().get(i).getMember_ID()) {
                trainer.getMembers().remove(i);
                found = true;
            } else {
                i++;
            }
        }
        member.setTrainer(null);
    }

    /**
     * Name: deleteEmployee Purpose/description: Delete an Employee from the
     * system.
     *
     * @param ID
     * @return
     */
    public boolean deleteEmployee(int ID) {
        boolean found = false;
        boolean deleted = false;
        int count = 0;
        while (!found & count < gymEmployees.size()) {
            if (gymEmployees.get(count).getEmployee_ID() == ID) {
                found = true;
            } else {
                count++;
            }
        }
        if (found & gymEmployees.get(count) instanceof PersonalTrainer) {
            PersonalTrainer trainer = (PersonalTrainer) gymEmployees.get(count);
            if (trainer.getMembers().isEmpty()) {
                gymEmployees.remove(count);
                deleted = true;
                return deleted;
            } else {
                return deleted;
            }
        } else {
            gymEmployees.remove(count);
            deleted = true;
            return deleted;
        }
    }

    /**
     * Name: deleteMember Purpose/description: Delete a Member from the system.
     *
     * @param ID
     * @return
     */
    public boolean deleteMember(int ID) {
        boolean found = false;
        int count = 0;
        while (!found & count < gymMembers.size()) {
            if (gymMembers.get(count).getMember_ID() == ID) {
                found = true;
            } else {
                count++;
            }
        }
        if (found) {
            if (gymMembers.get(count).getTrainer() != null) {
                removeMemberFromTrainer(ID, gymMembers.get(count).getTrainer().getEmployee_ID());
            }
            gymMembers.remove(count);
            return found;
        } else {
            return found;
        }
    }

    /**
     * Name: produceMarketingReport Purpose/description: Create a new marketing
     * file of the latest changes.
     *
     * @return
     */
    public String produceMarketingReport() {

        //Creeating student and staff list
        ArrayList<Staff> staffList = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();

        //Looping though member list to add students to student list and staff to staff list
        for (Member member : gymMembers) {
            if (member instanceof Staff) {
                staffList.add((Staff) member);
            } else {
                studentList.add((Student) member);
            }
        }

        //Creating file and checking for exceptions
        File marketingReport = null;
        try {
            marketingReport = new File("MarketingReport.txt");
            if (marketingReport.createNewFile()) {
            } else {
                //
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writeFile = new FileWriter("MarketingReport.txt");

            writeFile.write("Staff: \n");

            for (Staff staff : staffList) {
                writeFile.write("Member ID : " + staff.getMember_ID() + "\n"
                        + "\t Name : " + staff.getFirstName() + " " + staff.getLastName() + "\n"
                        + "\t Address : " + staff.getAddress() + "\n"
                        + "\t Phone : " + staff.getPhone() + "\n"
                        + "\t Position : " + staff.getPosition() + "\n"
                        + "\t Department : " + staff.getDepartment() + "\n");
            }
            writeFile.write("Total Staff : " + staffList.size());

            writeFile.write("\n__________________________________________\n");

            writeFile.write("Students: \n");

            for (Student student : studentList) {
                writeFile.write("Member ID : " + student.getMember_ID() + "\n"
                        + "\t Name : " + student.getFirstName() + " " + student.getLastName() + "\n"
                        + "\t Address : " + student.getAddress() + "\n"
                        + "\t Phone : " + student.getPhone() + "\n"
                        + "\t Course : " + student.getCourse() + "\n"
                        + "\t Team : " + student.getTeam() + "\n");
            }
            writeFile.write("Total Students : " + studentList.size());

            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marketingReport.getAbsolutePath();
    }

    /**
     * Name: loadStartUp Purpose/description: Load in the data from the startup
     * file.
     */
    public void loadStartUp() {
        BufferedReader reader = null;
        try {
            // Open the file using FileReader
            reader = new BufferedReader(new FileReader("startup.txt"));

            // Read the file line by line
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("E")) {
                    String fname = reader.readLine();
                    String lname = reader.readLine();
                    String gender;
                    String DOB;
                    // set default values for gender and DOB as it its not included in start up.
                    if (fname.equalsIgnoreCase("Hasan")) {
                        gender = "Male";
                        DOB = "19/9/1999";
                    } else {
                        gender = "Female";
                        DOB = "10/8/1995";
                    }
                    String address = reader.readLine();
                    String phone = reader.readLine();
                    double salary = Double.parseDouble(reader.readLine());
                    addEmployee(fname, lname, gender, phone, address, DOB, salary, false);
                } else if (line.equals("PT")) {
                    String fname = reader.readLine();
                    String lname = reader.readLine();
                    String gender;
                    String DOB;
                    // set default values for gender and DOB as it its not included in start up.
                    if (fname.equalsIgnoreCase("Maryam")) {
                        gender = "Female";
                        DOB = "9/3/1994";
                    } else {
                        gender = "Male";
                        DOB = "15/10/1988";
                    }
                    String address = reader.readLine();
                    String phone = reader.readLine();
                    double salary = Double.parseDouble(reader.readLine());
                    int PT_ID = addEmployee(fname, lname, gender, phone, address, DOB, salary, true);
                    int totalMembers = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < totalMembers; i++) {
                        String role = reader.readLine();
                        String member_fname = reader.readLine();
                        String member_lname = reader.readLine();
                        String member_address = reader.readLine();
                        String member_DOB = reader.readLine();
                        String member_phone = reader.readLine();
                        String member_gender = reader.readLine();
                        // setting the first letter to Capital to match the one used in the system.
                        member_gender = Character.toUpperCase(member_gender.charAt(0)) + member_gender.substring(1);
                        String position_course = reader.readLine();
                        String department_team = reader.readLine();
                        int member_ID = addMember(member_fname, member_lname, member_gender, member_phone, member_address, member_DOB, position_course, department_team, role.equals("staff"));
                        assignMemberToTrainer(member_ID, PT_ID);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Name: serializeObject Purpose/description: serialize the object that
     * calls the method and output it to a file.
     */
    public void serializeObject() {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("SystemData.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
        } catch (FileNotFoundException ex) {
            //
        } catch (IOException ex) {
            //
        } finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                //
            }
        }
    }

    /**
     * Name: deSerializeObject Purpose/description: deSerialize the object that
     * calls the method and get the information from the file.
     *
     * @return
     */
    public GymSystem deSerializeObject() {
        GymSystem self = this;
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream("SystemData.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            self = (GymSystem) in.readObject();
        } catch (FileNotFoundException ex) {
            //
        } catch (IOException ex) {
            //
        } catch (ClassNotFoundException ex) {
            //
        } finally {
            try {
                fileIn.close();
            } catch (NullPointerException | IOException ex) {
                //
            }
        }
        return self;
    }

}
