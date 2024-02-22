/*
 * This program is used to manage the members and employees
 * of Bahrain Polytechnic Gym
 * Authors: 202102147 Sayed Hussain, 202101456 Ali Alfardan, 202000237 Omar Ali 
 */
package GUI_GymSystem;

import Logic_GymSystem.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Class that contains all Graphical Aspects of the program and event handling.
 */
public class GymSystemGui extends javax.swing.JFrame {

    GymSystem system = new GymSystem();

    /**
     * Creates new form MainMenu
     */
    public GymSystemGui() {
        system = system.deSerializeObject();
        if (system.getGymMembers().isEmpty() & system.getGymEmployees().isEmpty()) {
            system.loadStartUp();
        }
        initComponents();

    }

    /**
     * Name: updateMemberList Purpose/description: update the members list to
     * the latest version file.
     */
    private void updateMemberList() {
        // Create a list of Members
        ArrayList<Member> members = system.getGymMembers();

        // Create a DefaultListModel to store the strings
        DefaultListModel<String> listModelMembers = new DefaultListModel<>();
        for (Member item : members) {
            String role;
            if (item instanceof Staff) {
                role = "Staff";
            } else {
                role = "Student";
            }
            listModelMembers.addElement(item.getMember_ID() + "   " + role + "   " + item.getFirstName() + "  " + item.getLastName());
        }
        //set the model for listMembers to the latest version
        listMembers.setModel(listModelMembers);
    }

    /**
     * Name: updateEmployeeList Purpose/description: update the employees list
     * to the latest version file.
     */
    private void updateEmployeeList() {
        // Create a list of Employees
        ArrayList<Employee> employees = system.getGymEmployees();
        // Create a DefaultListModel to store the strings
        DefaultListModel<String> listModelEmployees = new DefaultListModel<>();
        for (Employee item : employees) {
            String role;
            if (item instanceof PersonalTrainer) {
                role = "Personal Trainer";
            } else {
                role = "Regular Employee";
            }
            listModelEmployees.addElement(item.getEmployee_ID() + "   " + role + "   " + item.getFirstName() + "  " + item.getLastName());
        }
        //set the model for listEmployees to the latest version
        listEmployees.setModel(listModelEmployees);
    }

    /**
     * Name: updateTrainerMembersList Purpose/description: update the trainer's
     * member list to the latest version file.
     */
    private void updateTrainerMembersList(int ID) {
        // get the trainer by using the ID passed in the parameter.
        PersonalTrainer trainer = (PersonalTrainer) system.findEmployee(ID);
        // Create a DefaultListModel to store the members of the list 
        DefaultListModel<String> model = new DefaultListModel<>();
        String role;
        for (Member item : trainer.getMembers()) {
            if (item instanceof Staff) {
                role = "Staff";
            } else {
                role = "Student";
            }
            model.addElement(item.getMember_ID() + "   " + role + "   " + item.getFirstName() + "  " + item.getLastName());
        }
        //set the model for listTrainerMembers to the latest version
        listTrainerMembers.setModel(model);
    }

    /**
     * Name: updatePersonalTrainerCombo Purpose/description: update the comboBox
     * of trainers to the latest version file.
     */
    private void updatePersonalTrainerCombo() {
        ArrayList<Employee> trainers = system.getGymEmployees();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("None");
        for (Employee item : trainers) {
            if (item instanceof PersonalTrainer) {
                String ID = Integer.toString(item.getEmployee_ID());
                model.addElement(ID + " " + item.getFirstName() + " " + item.getLastName());
            }
        }
        comboMemberPersonalTrainer.setModel(model);
        comboMemberStudentPersonalTrainer.setModel(model);
        comboMemberStaffPersonalTrainer.setModel(model);

    }

    /**
     * Name: showStudentDetails Purpose/description: Show student details after
     * clicking on edit member file.
     */
    private void showStudentDetails(Student student) {
        txtMemberStudentFirstName.setText(student.getFirstName());
        txtMemberStudentLastName.setText(student.getLastName());
        if (student.getGender().equals("Male")) {
            comboMemberStudentGender.setSelectedIndex(0);
        } else {
            comboMemberStudentGender.setSelectedIndex(1);
        }
        txtMemberStudentPhone.setText(student.getPhone());
        txtMemberStudentAddress.setText(student.getAddress());
        txtMemberStudentDOB.setText(student.getDOB());
        //loop for finding course
        int courseIndex = 0;
        int courseSize = comboMemberStudentCourse.getItemCount();
        boolean courseFound = false;
        while (!courseFound & courseIndex < courseSize) {
            String course = String.valueOf(comboMemberStudentCourse.getItemAt(courseIndex));
            if (student.getCourse().equals(course)) {
                comboMemberStudentCourse.setSelectedIndex(courseIndex);
                courseFound = true;
            } else {
                courseIndex++;
            }
        }
        // loop for finding team
        int teamIndex = 0;
        int teamSize = comboMemberStudentTeam.getItemCount();
        boolean teamFound = false;
        while (!teamFound & teamIndex < teamSize) {
            String team = String.valueOf(comboMemberStudentTeam.getItemAt(teamIndex));
            if (student.getTeam().equals(team)) {
                comboMemberStudentTeam.setSelectedIndex(teamIndex);
                teamFound = true;
            } else {
                teamIndex++;
            }
        }

        //finding personal trainer
        //setting the combo index to 0 if member has no personal trainer
        if (student.getTrainer() == null) {
            comboMemberStudentPersonalTrainer.setSelectedIndex(0);
        } else {
            int size = comboMemberStudentPersonalTrainer.getItemCount();
            boolean found = false;
            int i = 1;
            while (!found && i < size) {
                int ID = Integer.parseInt(String.valueOf(comboMemberStudentPersonalTrainer.getItemAt(i).substring(0, 3)));
                if (ID == student.getTrainer().getEmployee_ID()) {
                    comboMemberStudentPersonalTrainer.setSelectedIndex(i);
                    found = true;
                } else {
                    i++;
                }
            }
        }
    }

    /**
     * Name: showStaffDetails Purpose/description: Show staff details after
     * clicking on edit member file.
     */
    private void showStaffDetails(Staff staff) {
        txtMemberStaffFirstName.setText(staff.getFirstName());
        txtMemberStaffLastName.setText(staff.getLastName());
        if (staff.getGender().equals("Male")) {
            comboMemberStaffGender.setSelectedIndex(0);
        } else {
            comboMemberStaffGender.setSelectedIndex(1);
        }
        txtMemberStaffPhone.setText(staff.getPhone());
        txtMemberStaffAddress.setText(staff.getAddress());
        txtMemberStaffDOB.setText(staff.getDOB());

        //loop for finding position
        int positionIndex = 0;
        int positionSize = comboMemberStaffPosition.getItemCount();
        boolean positionFound = false;
        while (!positionFound & positionIndex < positionSize) {
            String position = String.valueOf(comboMemberStaffPosition.getItemAt(positionIndex));
            if (staff.getPosition().equals(position)) {
                comboMemberStaffPosition.setSelectedIndex(positionIndex);
                positionFound = true;
            } else {
                positionIndex++;
            }
        }

        // loop for finding department
        int departmentIndex = 0;
        int departmentSize = comboMemberStaffDepartment.getItemCount();
        boolean departmentFound = false;
        while (!departmentFound & departmentIndex < departmentSize) {
            String department = String.valueOf(comboMemberStaffDepartment.getItemAt(departmentIndex));
            if (staff.getDepartment().equals(department)) {
                comboMemberStaffDepartment.setSelectedIndex(departmentIndex);
                departmentFound = true;
            } else {
                departmentIndex++;
            }
        }

        //finding personal trainer
        //setting the combo index to 0 if member has no personal trainer
        if (staff.getTrainer() == null) {
            comboMemberStaffPersonalTrainer.setSelectedIndex(0);
        } else {
            // loop for finding PersonalTrainer
            int size = comboMemberStaffPersonalTrainer.getItemCount();
            boolean found = false;
            int i = 1;
            while (!found && i < size) {
                int ID = Integer.parseInt(String.valueOf(comboMemberStaffPersonalTrainer.getItemAt(i).substring(0, 3)));
                if (ID == staff.getTrainer().getEmployee_ID()) {
                    comboMemberStaffPersonalTrainer.setSelectedIndex(i);
                    found = true;
                } else {
                    i++;
                }
            }
        }
    }

    /**
     * Name: showEmployeeDetails Purpose/description: Show employee details
     * after clicking on edit employee file.
     */
    private void showEmployeeDetails(Employee emp) {
        txtEmployeeEditFirstName.setText(emp.getFirstName());
        txtEmployeeEditLastName.setText(emp.getLastName());
        if (emp.getGender().equals("Male")) {
            comboEmployeeEditGender.setSelectedIndex(0);
        } else {
            comboEmployeeEditGender.setSelectedIndex(1);
        }
        txtEmployeeEditPhone.setText(emp.getPhone());
        txtEmployeeEditAddress.setText(emp.getAddress());
        txtEmployeeEditDOB.setText(emp.getDOB());
        // changing the format of the salary to a more readable format.
        DecimalFormat salaryFormat = new DecimalFormat("####.00");
        String salary = salaryFormat.format(emp.getSalary());
        txtEmployeeEditSalary.setText(salary);
    }

    /**
     * Name: changePositionCombo Purpose/description: update the staff position
     * combo file.
     */
    private void changePositionCombo() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("IT Tutor");
        model.addElement("Logistics Tutor");
        model.addElement("Management Tutor");
        model.addElement("English Tutor");
        model.addElement("Marketing Tutor");
        model.addElement("Engineering Tutor");
        model.addElement("Web Media Tutor");
        model.addElement("Visual Design Tutor");
        comboMemberPosition_Course.setModel(model);
        comboMemberStaffPosition.setModel(model);
    }

    /**
     * Name: changeCourseCombo Purpose/description: update the student course
     * combo file.
     */
    private void changeCourseCombo() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Business");
        model.addElement("Logistics");
        model.addElement("ICT");
        model.addElement("Engineering");
        model.addElement("Creative Media");
        comboMemberPosition_Course.setModel(model);
        comboMemberStudentCourse.setModel(model);
    }

    /**
     * Name: changeDepartmentCombo Purpose/description: update the staff
     * department combo file.
     */
    private void changeDepartmentCombo() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("ICT dept");
        model.addElement("Business dept");
        model.addElement("English dept");
        model.addElement("Logistics dept");
        model.addElement("Engineering dept");
        model.addElement("Creative Media dept");
        comboMemberDepartment_Team.setModel(model);
        comboMemberStaffDepartment.setModel(model);
    }

    /**
     * Name: changeTeamCombo Purpose/description: update the student team combo
     * file.
     */
    private void changeTeamCombo() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("None");
        model.addElement("Badminton");
        model.addElement("Netball");
        model.addElement("Football");
        model.addElement("BasketBall");
        model.addElement("Tennis");
        model.addElement("Table Tennis");
        model.addElement("Hockey");
        comboMemberDepartment_Team.setModel(model);
        comboMemberStudentTeam.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupStaffStudent = new javax.swing.ButtonGroup();
        groupRegularPersonalTrainer = new javax.swing.ButtonGroup();
        tabbedPanel = new javax.swing.JTabbedPane();
        mainMenu = new javax.swing.JPanel();
        imgMainMenu = new javax.swing.JLabel();
        btnManageMembers = new javax.swing.JButton();
        btnManageEmployees = new javax.swing.JButton();
        btnMarketingReport = new javax.swing.JButton();
        exitSystem = new javax.swing.JButton();
        manageMembers = new javax.swing.JPanel();
        lblMembers = new javax.swing.JLabel();
        btnMembersBack = new javax.swing.JLabel();
        btnAddMember = new javax.swing.JButton();
        scrollMemberList = new javax.swing.JScrollPane();
        listMembers = new javax.swing.JList<>();
        btnEditMember = new javax.swing.JButton();
        btnDeleteMember = new javax.swing.JButton();
        ManageMembersLogo = new javax.swing.JLabel();
        btnSearchMemberList = new javax.swing.JButton();
        txtMemberSearchList = new javax.swing.JTextField();
        lblMemberSearchList = new javax.swing.JLabel();
        manageEmployees = new javax.swing.JPanel();
        lblEmployees = new javax.swing.JLabel();
        btnAddEmployee = new javax.swing.JButton();
        btnEmployeesBack = new javax.swing.JLabel();
        scrollEmployeeList = new javax.swing.JScrollPane();
        listEmployees = new javax.swing.JList<>();
        btnEditEmployee = new javax.swing.JButton();
        btnDeleteEmployee = new javax.swing.JButton();
        btnShowMemberList = new javax.swing.JButton();
        manageEmployeesLogo = new javax.swing.JLabel();
        txtEmployeeSearchList = new javax.swing.JTextField();
        lblEmployeeSearchList = new javax.swing.JLabel();
        btnEmployeeSearchList = new javax.swing.JButton();
        addMember = new javax.swing.JPanel();
        lblAddMember = new javax.swing.JLabel();
        lblMemberLastName = new javax.swing.JLabel();
        lblMemberFirstName = new javax.swing.JLabel();
        lblMemberGender = new javax.swing.JLabel();
        lblMemberPhone = new javax.swing.JLabel();
        lblMemberAddress = new javax.swing.JLabel();
        lblMemberDOB = new javax.swing.JLabel();
        lblMemberPosition_Course = new javax.swing.JLabel();
        lblMemberDepartment_Team = new javax.swing.JLabel();
        lblMemberPersonalTrainer = new javax.swing.JLabel();
        radioMemberStaff = new javax.swing.JRadioButton();
        radioMemberStudent = new javax.swing.JRadioButton();
        txtMemberDOB = new javax.swing.JTextField();
        txtMemberFirstName = new javax.swing.JTextField();
        txtMemberLastName = new javax.swing.JTextField();
        txtMemberPhone = new javax.swing.JTextField();
        comboMemberPersonalTrainer = new javax.swing.JComboBox<>();
        btnCreateMember = new javax.swing.JButton();
        txtMemberAddress = new javax.swing.JTextField();
        comboMemberPosition_Course = new javax.swing.JComboBox<>();
        btnAddMemberBack = new javax.swing.JLabel();
        comboMemberDepartment_Team = new javax.swing.JComboBox<>();
        comboMemberGender = new javax.swing.JComboBox<>();
        AddMemberLogo = new javax.swing.JLabel();
        addEmployee = new javax.swing.JPanel();
        lblAddEmployee = new javax.swing.JLabel();
        lblEmployeeFirstName = new javax.swing.JLabel();
        lblEmployeeLastName = new javax.swing.JLabel();
        lblEmployeeGender = new javax.swing.JLabel();
        lblEmployeePhone = new javax.swing.JLabel();
        lblEmployeeAddress = new javax.swing.JLabel();
        lblEmployeeSalary = new javax.swing.JLabel();
        lblEmployeeDOB = new javax.swing.JLabel();
        txtEmployeeLastName = new javax.swing.JTextField();
        txtEmployeePhone = new javax.swing.JTextField();
        txtEmployeeAddress = new javax.swing.JTextField();
        txtEmployeeDOB = new javax.swing.JTextField();
        txtEmployeeSalary = new javax.swing.JTextField();
        txtEmployeeFirstName = new javax.swing.JTextField();
        comboEmployeeGender = new javax.swing.JComboBox<>();
        radioEmployeeRegular = new javax.swing.JRadioButton();
        radioEmployeePersonalTrainer = new javax.swing.JRadioButton();
        btnCreateEmployee = new javax.swing.JButton();
        btnAddEmployeeBack = new javax.swing.JLabel();
        AddEmployeeLogo = new javax.swing.JLabel();
        memberStudent = new javax.swing.JPanel();
        lblMemberStudent = new javax.swing.JLabel();
        btnEditMemberStudentBack = new javax.swing.JLabel();
        lblMemberStudentFirstName = new javax.swing.JLabel();
        lblMemberStudentLastName = new javax.swing.JLabel();
        lblMemberStudentGender = new javax.swing.JLabel();
        lblMemberStudentPhone = new javax.swing.JLabel();
        lblMemberStudentAddress = new javax.swing.JLabel();
        lblMemberStudentDOB = new javax.swing.JLabel();
        lblMemberStudentTeam = new javax.swing.JLabel();
        lblMemberStudentCourse = new javax.swing.JLabel();
        lblMemberStudentPersonalTrainer = new javax.swing.JLabel();
        txtMemberStudentLastName = new javax.swing.JTextField();
        txtMemberStudentDOB = new javax.swing.JTextField();
        txtMemberStudentPhone = new javax.swing.JTextField();
        txtMemberStudentAddress = new javax.swing.JTextField();
        txtMemberStudentFirstName = new javax.swing.JTextField();
        comboMemberStudentGender = new javax.swing.JComboBox<>();
        comboMemberStudentPersonalTrainer = new javax.swing.JComboBox<>();
        btnSaveMemberStudent = new javax.swing.JButton();
        lblMemberStudentID = new javax.swing.JLabel();
        comboMemberStudentTeam = new javax.swing.JComboBox<>();
        comboMemberStudentCourse = new javax.swing.JComboBox<>();
        MemberStudentLogo = new javax.swing.JLabel();
        memberStaff = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnEditMemberStaffBack = new javax.swing.JLabel();
        lblMemberStaffFirstName = new javax.swing.JLabel();
        lblMemberStaffLastName = new javax.swing.JLabel();
        lblMemberStaffGender = new javax.swing.JLabel();
        lblMemberStaffPhone = new javax.swing.JLabel();
        lblMemberStaffAddress = new javax.swing.JLabel();
        lblMemberStaffDOB = new javax.swing.JLabel();
        lblMemberStaffCourse = new javax.swing.JLabel();
        lblMemberStaffTeam = new javax.swing.JLabel();
        lblMemberStaffPersonalTrainer = new javax.swing.JLabel();
        txtMemberStaffLastName = new javax.swing.JTextField();
        txtMemberStaffPhone = new javax.swing.JTextField();
        txtMemberStaffAddress = new javax.swing.JTextField();
        txtMemberStaffDOB = new javax.swing.JTextField();
        txtMemberStaffFirstName = new javax.swing.JTextField();
        comboMemberStaffGender = new javax.swing.JComboBox<>();
        comboMemberStaffPersonalTrainer = new javax.swing.JComboBox<>();
        btnSaveMemberStaff = new javax.swing.JButton();
        lblMemberStaffID = new javax.swing.JLabel();
        comboMemberStaffDepartment = new javax.swing.JComboBox<>();
        comboMemberStaffPosition = new javax.swing.JComboBox<>();
        editMemberLogo = new javax.swing.JLabel();
        employee = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnEditEmployeeBack = new javax.swing.JLabel();
        txtEmployeeEditFirstName = new javax.swing.JTextField();
        lblEmployeeEditFirstName = new javax.swing.JLabel();
        lblEmployeeEditLastName = new javax.swing.JLabel();
        txtEmployeeEditLastName = new javax.swing.JTextField();
        lblEmployeeEditGender = new javax.swing.JLabel();
        comboEmployeeEditGender = new javax.swing.JComboBox<>();
        lblEmployeeEditPhone = new javax.swing.JLabel();
        txtEmployeeEditPhone = new javax.swing.JTextField();
        lblEmployeeEditAddress = new javax.swing.JLabel();
        txtEmployeeEditAddress = new javax.swing.JTextField();
        txtEmployeeEditDOB = new javax.swing.JTextField();
        lblEmployeeEditDOB = new javax.swing.JLabel();
        lblEmployeeEditSalary = new javax.swing.JLabel();
        txtEmployeeEditSalary = new javax.swing.JTextField();
        btnSaveEmployee = new javax.swing.JButton();
        lblEmployeeID = new javax.swing.JLabel();
        employeeLogo = new javax.swing.JLabel();
        trainerList = new javax.swing.JPanel();
        lblPersonalTrainer = new javax.swing.JLabel();
        btnTrainerMembersListBack = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listTrainerMembers = new javax.swing.JList<>();
        lblTrainerEmployeeID = new javax.swing.JLabel();
        btnRemoveMemberFromTrainer = new javax.swing.JButton();
        trainerListLogo = new javax.swing.JLabel();
        txtTrainerMemberSearchList = new javax.swing.JTextField();
        btnSearchTrainerMemberList = new javax.swing.JButton();
        lblTrainerMemberSearchList = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GymSystem");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabbedPanel.setBackground(new java.awt.Color(255, 255, 255));
        tabbedPanel.setForeground(new java.awt.Color(255, 255, 255));
        tabbedPanel.setDoubleBuffered(true);
        tabbedPanel.setPreferredSize(new java.awt.Dimension(900, 675));

        mainMenu.setBackground(new java.awt.Color(255, 255, 255));

        imgMainMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/MainMenuImage.png"))); // NOI18N

        btnManageMembers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/manage_members.png"))); // NOI18N
        btnManageMembers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 50, 50)));
        btnManageMembers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManageMembers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageMembersActionPerformed(evt);
            }
        });

        btnManageEmployees.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/manage_employees.png"))); // NOI18N
        btnManageEmployees.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 50, 50)));
        btnManageEmployees.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManageEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageEmployeesActionPerformed(evt);
            }
        });

        btnMarketingReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/marketing_report.png"))); // NOI18N
        btnMarketingReport.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 50, 50)));
        btnMarketingReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMarketingReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarketingReportActionPerformed(evt);
            }
        });

        exitSystem.setBackground(new java.awt.Color(255, 50, 50));
        exitSystem.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        exitSystem.setForeground(new java.awt.Color(255, 255, 255));
        exitSystem.setText("Save & Exit");
        exitSystem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitSystem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitSystemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainMenuLayout = new javax.swing.GroupLayout(mainMenu);
        mainMenu.setLayout(mainMenuLayout);
        mainMenuLayout.setHorizontalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainMenuLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnManageMembers)
                        .addGap(18, 18, 18)
                        .addComponent(btnManageEmployees)
                        .addGap(18, 18, 18)
                        .addComponent(btnMarketingReport))
                    .addGroup(mainMenuLayout.createSequentialGroup()
                        .addGap(409, 409, 409)
                        .addComponent(exitSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainMenuLayout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(imgMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        mainMenuLayout.setVerticalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(imgMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMarketingReport)
                    .addComponent(btnManageEmployees)
                    .addComponent(btnManageMembers))
                .addGap(42, 42, 42)
                .addComponent(exitSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(236, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Main Menu", mainMenu);

        manageMembers.setBackground(new java.awt.Color(255, 255, 255));

        lblMembers.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        lblMembers.setForeground(new java.awt.Color(255, 50, 50));
        lblMembers.setText("Members");

        btnMembersBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnMembersBack.setAlignmentY(5000.0F);
        btnMembersBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMembersBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMembersBackMouseClicked(evt);
            }
        });

        btnAddMember.setBackground(new java.awt.Color(255, 50, 50));
        btnAddMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddMember.setForeground(new java.awt.Color(255, 255, 255));
        btnAddMember.setText("Add Member");
        btnAddMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddMember.setDoubleBuffered(true);
        btnAddMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMemberActionPerformed(evt);
            }
        });

        listMembers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        updateMemberList();
        scrollMemberList.setViewportView(listMembers);

        btnEditMember.setBackground(new java.awt.Color(255, 50, 50));
        btnEditMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditMember.setForeground(new java.awt.Color(255, 255, 255));
        btnEditMember.setText("Edit Member");
        btnEditMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditMember.setDoubleBuffered(true);
        btnEditMember.setPreferredSize(new java.awt.Dimension(120, 34));
        btnEditMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMemberActionPerformed(evt);
            }
        });

        btnDeleteMember.setBackground(new java.awt.Color(255, 50, 50));
        btnDeleteMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteMember.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteMember.setText("Delete Member");
        btnDeleteMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteMember.setDoubleBuffered(true);
        btnDeleteMember.setPreferredSize(new java.awt.Dimension(120, 34));
        btnDeleteMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMemberActionPerformed(evt);
            }
        });

        ManageMembersLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        ManageMembersLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ManageMembersLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ManageMembersLogoMouseClicked(evt);
            }
        });

        btnSearchMemberList.setBackground(new java.awt.Color(255, 50, 50));
        btnSearchMemberList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchMemberList.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchMemberList.setText("Search");
        btnSearchMemberList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchMemberList.setDoubleBuffered(true);
        btnSearchMemberList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchMemberListActionPerformed(evt);
            }
        });

        lblMemberSearchList.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 18)); // NOI18N
        lblMemberSearchList.setForeground(new java.awt.Color(255, 50, 50));
        lblMemberSearchList.setText("Member ID : ");

        javax.swing.GroupLayout manageMembersLayout = new javax.swing.GroupLayout(manageMembers);
        manageMembers.setLayout(manageMembersLayout);
        manageMembersLayout.setHorizontalGroup(
            manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageMembersLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnMembersBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMembers)
                .addGap(319, 319, 319)
                .addComponent(ManageMembersLogo)
                .addGap(155, 155, 155))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageMembersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMemberSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMemberSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearchMemberList)
                .addGap(488, 488, 488))
            .addGroup(manageMembersLayout.createSequentialGroup()
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGap(333, 333, 333)
                        .addComponent(btnAddMember, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditMember, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteMember, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(scrollMemberList, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        manageMembersLayout.setVerticalGroup(
            manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageMembersLayout.createSequentialGroup()
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblMembers))
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnMembersBack))
                    .addGroup(manageMembersLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(ManageMembersLogo)))
                .addGap(36, 36, 36)
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMemberSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMemberSearchList)
                    .addComponent(btnSearchMemberList))
                .addGap(18, 18, 18)
                .addComponent(scrollMemberList, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(manageMembersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnDeleteMember, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnAddMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditMember, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(500, 500, 500))
        );

        //btnAddMember.setFocusPainted(false);
        //btnAddMember.setFocusPainted(false);

        tabbedPanel.addTab("Manage Members", manageMembers);

        manageEmployees.setBackground(new java.awt.Color(255, 255, 255));

        lblEmployees.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        lblEmployees.setForeground(new java.awt.Color(255, 50, 50));
        lblEmployees.setText("Employees");

        btnAddEmployee.setBackground(new java.awt.Color(255, 50, 50));
        btnAddEmployee.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEmployee.setText("Add Employee");
        btnAddEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });

        btnEmployeesBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnEmployeesBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmployeesBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeesBackMouseClicked(evt);
            }
        });

        listEmployees.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        updateEmployeeList();
        scrollEmployeeList.setViewportView(listEmployees);

        btnEditEmployee.setBackground(new java.awt.Color(255, 50, 50));
        btnEditEmployee.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnEditEmployee.setText("Edit Employee");
        btnEditEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditEmployeeActionPerformed(evt);
            }
        });

        btnDeleteEmployee.setBackground(new java.awt.Color(255, 50, 50));
        btnDeleteEmployee.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteEmployee.setText("Delete Employee");
        btnDeleteEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteEmployeeActionPerformed(evt);
            }
        });

        btnShowMemberList.setBackground(new java.awt.Color(255, 50, 50));
        btnShowMemberList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnShowMemberList.setForeground(new java.awt.Color(255, 255, 255));
        btnShowMemberList.setText("Show Member List");
        btnShowMemberList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnShowMemberList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowMemberListActionPerformed(evt);
            }
        });

        manageEmployeesLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        manageEmployeesLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageEmployeesLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageEmployeesLogoMouseClicked(evt);
            }
        });

        lblEmployeeSearchList.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 18)); // NOI18N
        lblEmployeeSearchList.setForeground(new java.awt.Color(255, 50, 50));
        lblEmployeeSearchList.setText("Employee ID :");

        btnEmployeeSearchList.setBackground(new java.awt.Color(255, 50, 50));
        btnEmployeeSearchList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEmployeeSearchList.setForeground(new java.awt.Color(255, 255, 255));
        btnEmployeeSearchList.setText("Search");
        btnEmployeeSearchList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmployeeSearchList.setDoubleBuffered(true);
        btnEmployeeSearchList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeSearchListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout manageEmployeesLayout = new javax.swing.GroupLayout(manageEmployees);
        manageEmployees.setLayout(manageEmployeesLayout);
        manageEmployeesLayout.setHorizontalGroup(
            manageEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageEmployeesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnEmployeesBack)
                .addGap(366, 366, 366)
                .addComponent(lblEmployees)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(manageEmployeesLogo)
                .addGap(155, 155, 155))
            .addGroup(manageEmployeesLayout.createSequentialGroup()
                .addGroup(manageEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageEmployeesLayout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(scrollEmployeeList, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(manageEmployeesLayout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addComponent(lblEmployeeSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmployeeSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEmployeeSearchList))
                    .addGroup(manageEmployeesLayout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnShowMemberList, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(284, Short.MAX_VALUE))
        );
        manageEmployeesLayout.setVerticalGroup(
            manageEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageEmployeesLayout.createSequentialGroup()
                .addGroup(manageEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(manageEmployeesLayout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(btnEmployeesBack))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageEmployeesLayout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(lblEmployees)))
                    .addGroup(manageEmployeesLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(manageEmployeesLogo)))
                .addGap(33, 33, 33)
                .addGroup(manageEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeSearchList)
                    .addComponent(btnEmployeeSearchList))
                .addGap(18, 18, 18)
                .addComponent(scrollEmployeeList, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(manageEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowMemberList, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(214, Short.MAX_VALUE))
        );

        //btnAddMember.setFocusPainted(false);

        tabbedPanel.addTab("Manage Employees", manageEmployees);

        addMember.setBackground(new java.awt.Color(255, 255, 255));

        lblAddMember.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        lblAddMember.setForeground(new java.awt.Color(255, 50, 50));
        lblAddMember.setText("Add Member");

        lblMemberLastName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberLastName.setText("Last Name");

        lblMemberFirstName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberFirstName.setText("First Name");

        lblMemberGender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberGender.setText("Gender");

        lblMemberPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberPhone.setText("Phone");

        lblMemberAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberAddress.setText("Address");

        lblMemberDOB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberDOB.setText("D.O.B");

        lblMemberPosition_Course.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberPosition_Course.setText("Position");

        lblMemberDepartment_Team.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberDepartment_Team.setText("Department");

        lblMemberPersonalTrainer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberPersonalTrainer.setText("Personal Trainer");

        groupStaffStudent.add(radioMemberStaff);
        radioMemberStaff.setSelected(true);
        radioMemberStaff.setText("Staff");
        radioMemberStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMemberStaffActionPerformed(evt);
            }
        });

        groupStaffStudent.add(radioMemberStudent);
        radioMemberStudent.setText("Student");
        radioMemberStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMemberStudentActionPerformed(evt);
            }
        });

        comboMemberPersonalTrainer.setBackground(new java.awt.Color(255, 50, 50));
        updatePersonalTrainerCombo();

        btnCreateMember.setBackground(new java.awt.Color(255, 50, 50));
        btnCreateMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCreateMember.setForeground(new java.awt.Color(255, 255, 255));
        btnCreateMember.setText("Add Member");
        btnCreateMember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateMemberActionPerformed(evt);
            }
        });

        comboMemberPosition_Course.setBackground(new java.awt.Color(255, 50, 50));
        changePositionCombo();

        btnAddMemberBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnAddMemberBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddMemberBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMemberBackMouseClicked(evt);
            }
        });

        comboMemberDepartment_Team.setBackground(new java.awt.Color(255, 50, 50));
        changeDepartmentCombo();

        comboMemberGender.setBackground(new java.awt.Color(255, 50, 50));
        comboMemberGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        AddMemberLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        AddMemberLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddMemberLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddMemberLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout addMemberLayout = new javax.swing.GroupLayout(addMember);
        addMember.setLayout(addMemberLayout);
        addMemberLayout.setHorizontalGroup(
            addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addMemberLayout.createSequentialGroup()
                .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addMemberLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(addMemberLayout.createSequentialGroup()
                                    .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMemberFirstName)
                                        .addComponent(lblMemberGender)
                                        .addComponent(lblMemberAddress))
                                    .addGap(15, 15, 15))
                                .addComponent(lblMemberPosition_Course, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblMemberPersonalTrainer))
                        .addGap(34, 34, 34)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMemberFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMemberAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboMemberGender, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboMemberPosition_Course, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboMemberPersonalTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMemberDOB)
                            .addComponent(lblMemberDepartment_Team, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMemberPhone)
                            .addComponent(lblMemberLastName)))
                    .addGroup(addMemberLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnAddMemberBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAddMember)))
                .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addMemberLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboMemberDepartment_Team, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMemberDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMemberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMemberLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(addMemberLayout.createSequentialGroup()
                                .addComponent(radioMemberStaff)
                                .addGap(64, 64, 64)
                                .addComponent(radioMemberStudent))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addMemberLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                        .addComponent(AddMemberLogo)
                        .addGap(155, 155, 155))))
            .addGroup(addMemberLayout.createSequentialGroup()
                .addGap(476, 476, 476)
                .addComponent(btnCreateMember, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addMemberLayout.setVerticalGroup(
            addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addMemberLayout.createSequentialGroup()
                .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addMemberLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(AddMemberLogo))
                    .addGroup(addMemberLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddMemberBack)
                            .addComponent(lblAddMember))))
                .addGap(63, 63, 63)
                .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addMemberLayout.createSequentialGroup()
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMemberLastName)
                            .addComponent(txtMemberFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMemberPhone)
                            .addComponent(comboMemberGender, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMemberAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMemberDOB))
                        .addGap(30, 30, 30)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboMemberPosition_Course, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMemberDepartment_Team)))
                    .addGroup(addMemberLayout.createSequentialGroup()
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addMemberLayout.createSequentialGroup()
                                .addComponent(lblMemberFirstName)
                                .addGap(38, 38, 38)
                                .addComponent(lblMemberGender)
                                .addGap(33, 33, 33)
                                .addComponent(lblMemberAddress)
                                .addGap(39, 39, 39)
                                .addComponent(lblMemberPosition_Course))
                            .addGroup(addMemberLayout.createSequentialGroup()
                                .addComponent(txtMemberLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(txtMemberPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(txtMemberDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(comboMemberDepartment_Team, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(addMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMemberPersonalTrainer)
                            .addComponent(comboMemberPersonalTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioMemberStaff)
                            .addComponent(radioMemberStudent))))
                .addGap(117, 117, 117)
                .addComponent(btnCreateMember, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Add Member", addMember);

        addEmployee.setBackground(new java.awt.Color(255, 255, 255));

        lblAddEmployee.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        lblAddEmployee.setForeground(new java.awt.Color(255, 50, 50));
        lblAddEmployee.setText("Add Employee");

        lblEmployeeFirstName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeFirstName.setText("First Name");

        lblEmployeeLastName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeLastName.setText("Last Name");

        lblEmployeeGender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeGender.setText("Gender");

        lblEmployeePhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeePhone.setText("Phone");

        lblEmployeeAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeAddress.setText("Address");

        lblEmployeeSalary.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeSalary.setText("Salary");

        lblEmployeeDOB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeDOB.setText("D.O.B");

        comboEmployeeGender.setBackground(new java.awt.Color(255, 50, 50));
        comboEmployeeGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        groupRegularPersonalTrainer.add(radioEmployeeRegular);
        radioEmployeeRegular.setSelected(true);
        radioEmployeeRegular.setText("Regular Employee");

        groupRegularPersonalTrainer.add(radioEmployeePersonalTrainer);
        radioEmployeePersonalTrainer.setText("Personal Trainer");

        btnCreateEmployee.setBackground(new java.awt.Color(255, 50, 50));
        btnCreateEmployee.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCreateEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnCreateEmployee.setText("Add Employee");
        btnCreateEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateEmployeeActionPerformed(evt);
            }
        });

        btnAddEmployeeBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnAddEmployeeBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddEmployeeBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddEmployeeBackMouseClicked(evt);
            }
        });

        AddEmployeeLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        AddEmployeeLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddEmployeeLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddEmployeeLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout addEmployeeLayout = new javax.swing.GroupLayout(addEmployee);
        addEmployee.setLayout(addEmployeeLayout);
        addEmployeeLayout.setHorizontalGroup(
            addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEmployeeLayout.createSequentialGroup()
                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addEmployeeLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmployeeFirstName)
                            .addComponent(lblEmployeeGender)
                            .addComponent(lblEmployeeAddress)
                            .addComponent(lblEmployeeSalary))
                        .addGap(52, 52, 52)
                        .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmployeeAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboEmployeeGender, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmployeeFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmployeeSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addEmployeeLayout.createSequentialGroup()
                                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmployeeLastName)
                                    .addComponent(lblEmployeePhone)
                                    .addComponent(lblEmployeeDOB))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmployeePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmployeeLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmployeeDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(addEmployeeLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(radioEmployeeRegular)
                                .addGap(63, 63, 63)
                                .addComponent(radioEmployeePersonalTrainer))))
                    .addGroup(addEmployeeLayout.createSequentialGroup()
                        .addGap(487, 487, 487)
                        .addComponent(btnCreateEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(266, Short.MAX_VALUE))
            .addGroup(addEmployeeLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnAddEmployeeBack)
                .addGap(348, 348, 348)
                .addComponent(lblAddEmployee)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddEmployeeLogo)
                .addGap(155, 155, 155))
        );
        addEmployeeLayout.setVerticalGroup(
            addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEmployeeLayout.createSequentialGroup()
                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addEmployeeLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(lblAddEmployee))
                    .addGroup(addEmployeeLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnAddEmployeeBack))
                    .addGroup(addEmployeeLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(AddEmployeeLogo)))
                .addGap(107, 107, 107)
                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addEmployeeLayout.createSequentialGroup()
                        .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(addEmployeeLayout.createSequentialGroup()
                                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEmployeeLastName)
                                    .addComponent(txtEmployeeLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmployeePhone, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtEmployeePhone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(addEmployeeLayout.createSequentialGroup()
                                .addComponent(txtEmployeeFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(comboEmployeeGender, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39)
                        .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(addEmployeeLayout.createSequentialGroup()
                                .addComponent(txtEmployeeAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(txtEmployeeSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(addEmployeeLayout.createSequentialGroup()
                                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEmployeeDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEmployeeDOB))
                                .addGap(50, 50, 50)
                                .addGroup(addEmployeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(radioEmployeeRegular)
                                    .addComponent(radioEmployeePersonalTrainer)))))
                    .addGroup(addEmployeeLayout.createSequentialGroup()
                        .addComponent(lblEmployeeFirstName)
                        .addGap(41, 41, 41)
                        .addComponent(lblEmployeeGender)
                        .addGap(49, 49, 49)
                        .addComponent(lblEmployeeAddress)
                        .addGap(40, 40, 40)
                        .addComponent(lblEmployeeSalary)))
                .addGap(107, 107, 107)
                .addComponent(btnCreateEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Add Employee", addEmployee);

        memberStudent.setBackground(new java.awt.Color(255, 255, 255));

        lblMemberStudent.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        lblMemberStudent.setForeground(new java.awt.Color(255, 50, 50));
        lblMemberStudent.setText("Edit Member(Student)");

        btnEditMemberStudentBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnEditMemberStudentBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditMemberStudentBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMemberStudentBackMouseClicked(evt);
            }
        });

        lblMemberStudentFirstName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentFirstName.setText("First Name");

        lblMemberStudentLastName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentLastName.setText("Last Name");

        lblMemberStudentGender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentGender.setText("Gender");

        lblMemberStudentPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentPhone.setText("Phone");

        lblMemberStudentAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentAddress.setText("Address");

        lblMemberStudentDOB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentDOB.setText("D.O.B");

        lblMemberStudentTeam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentTeam.setText("Team");

        lblMemberStudentCourse.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentCourse.setText("Course");

        lblMemberStudentPersonalTrainer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStudentPersonalTrainer.setText("Personal Trainer");

        comboMemberStudentGender.setBackground(new java.awt.Color(255, 50, 50));
        comboMemberStudentGender.setForeground(new java.awt.Color(153, 153, 153));
        comboMemberStudentGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        comboMemberStudentGender.setBorder(null);

        comboMemberStudentPersonalTrainer.setBackground(new java.awt.Color(255, 50, 50));
        updatePersonalTrainerCombo();

        btnSaveMemberStudent.setBackground(new java.awt.Color(255, 50, 50));
        btnSaveMemberStudent.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSaveMemberStudent.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveMemberStudent.setText("Save Changes");
        btnSaveMemberStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveMemberStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveMemberStudentActionPerformed(evt);
            }
        });

        lblMemberStudentID.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 18)); // NOI18N
        lblMemberStudentID.setForeground(new java.awt.Color(255, 50, 50));
        lblMemberStudentID.setText("Member ID : ");

        comboMemberStudentTeam.setBackground(new java.awt.Color(255, 50, 50));
        changeTeamCombo();

        comboMemberStudentCourse.setBackground(new java.awt.Color(255, 50, 50));
        comboMemberStudentCourse.setBorder(null);
        changeCourseCombo();

        MemberStudentLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        MemberStudentLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MemberStudentLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MemberStudentLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout memberStudentLayout = new javax.swing.GroupLayout(memberStudent);
        memberStudent.setLayout(memberStudentLayout);
        memberStudentLayout.setHorizontalGroup(
            memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, memberStudentLayout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblMemberStudentFirstName)
                        .addComponent(lblMemberStudentGender)
                        .addComponent(lblMemberStudentAddress))
                    .addComponent(lblMemberStudentCourse, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(60, 60, 60)
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboMemberStudentGender, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMemberStudentFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMemberStudentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMemberStudentCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91)
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMemberStudentLastName)
                    .addComponent(lblMemberStudentPhone)
                    .addComponent(lblMemberStudentDOB)
                    .addComponent(lblMemberStudentTeam))
                .addGap(89, 89, 89)
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMemberStudentDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMemberStudentPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMemberStudentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMemberStudentTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(174, 174, 174))
            .addGroup(memberStudentLayout.createSequentialGroup()
                .addGap(395, 395, 395)
                .addComponent(lblMemberStudentPersonalTrainer)
                .addGap(32, 32, 32)
                .addComponent(comboMemberStudentPersonalTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(memberStudentLayout.createSequentialGroup()
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(memberStudentLayout.createSequentialGroup()
                        .addGap(481, 481, 481)
                        .addComponent(btnSaveMemberStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(memberStudentLayout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(lblMemberStudentID)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(memberStudentLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnEditMemberStudentBack)
                .addGap(293, 293, 293)
                .addComponent(lblMemberStudent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(MemberStudentLogo)
                .addGap(155, 155, 155))
        );
        memberStudentLayout.setVerticalGroup(
            memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memberStudentLayout.createSequentialGroup()
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(memberStudentLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMemberStudent)
                            .addComponent(btnEditMemberStudentBack)))
                    .addGroup(memberStudentLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(MemberStudentLogo)))
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(memberStudentLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(txtMemberStudentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(txtMemberStudentDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(memberStudentLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblMemberStudentID)
                        .addGap(80, 80, 80)
                        .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(memberStudentLayout.createSequentialGroup()
                                .addComponent(lblMemberStudentLastName)
                                .addGap(43, 43, 43)
                                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStudentPhone)
                                    .addComponent(txtMemberStudentPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addComponent(lblMemberStudentDOB))
                            .addGroup(memberStudentLayout.createSequentialGroup()
                                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStudentFirstName)
                                    .addComponent(txtMemberStudentFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStudentGender)
                                    .addComponent(comboMemberStudentGender, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStudentAddress)
                                    .addComponent(txtMemberStudentAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStudentCourse)
                                    .addComponent(comboMemberStudentCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMemberStudentTeam)
                                    .addComponent(comboMemberStudentTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(51, 51, 51)
                .addGroup(memberStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboMemberStudentPersonalTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMemberStudentPersonalTrainer))
                .addGap(65, 65, 65)
                .addComponent(btnSaveMemberStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Member(Student)", memberStudent);

        memberStaff.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 50, 50));
        jLabel6.setText("Edit Member(Staff)");

        btnEditMemberStaffBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnEditMemberStaffBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditMemberStaffBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMemberStaffBackMouseClicked(evt);
            }
        });

        lblMemberStaffFirstName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffFirstName.setText("First Name");

        lblMemberStaffLastName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffLastName.setText("Last Name");

        lblMemberStaffGender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffGender.setText("Gender");

        lblMemberStaffPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffPhone.setText("Phone");

        lblMemberStaffAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffAddress.setText("Address");

        lblMemberStaffDOB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffDOB.setText("D.O.B");

        lblMemberStaffCourse.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffCourse.setText("Position");

        lblMemberStaffTeam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffTeam.setText("Department");

        lblMemberStaffPersonalTrainer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMemberStaffPersonalTrainer.setText("Personal Trainer");

        comboMemberStaffGender.setBackground(new java.awt.Color(255, 50, 50));
        comboMemberStaffGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        comboMemberStaffPersonalTrainer.setBackground(new java.awt.Color(255, 50, 50));
        updatePersonalTrainerCombo();

        btnSaveMemberStaff.setBackground(new java.awt.Color(255, 50, 50));
        btnSaveMemberStaff.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSaveMemberStaff.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveMemberStaff.setText("Save Changes");
        btnSaveMemberStaff.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveMemberStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveMemberStaffActionPerformed(evt);
            }
        });

        lblMemberStaffID.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 18)); // NOI18N
        lblMemberStaffID.setForeground(new java.awt.Color(255, 50, 50));
        lblMemberStaffID.setText("Member ID : ");

        comboMemberStaffDepartment.setBackground(new java.awt.Color(255, 50, 50));
        changeDepartmentCombo();

        comboMemberStaffPosition.setBackground(new java.awt.Color(255, 50, 50));
        changePositionCombo();

        editMemberLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        editMemberLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editMemberLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMemberLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout memberStaffLayout = new javax.swing.GroupLayout(memberStaff);
        memberStaff.setLayout(memberStaffLayout);
        memberStaffLayout.setHorizontalGroup(
            memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memberStaffLayout.createSequentialGroup()
                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(memberStaffLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnEditMemberStaffBack)
                        .addGap(54, 54, 54)
                        .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, memberStaffLayout.createSequentialGroup()
                                    .addGap(231, 231, 231)
                                    .addComponent(lblMemberStaffPersonalTrainer)
                                    .addGap(43, 43, 43)
                                    .addComponent(comboMemberStaffPersonalTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(196, 196, 196))
                                .addGroup(memberStaffLayout.createSequentialGroup()
                                    .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(memberStaffLayout.createSequentialGroup()
                                            .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, memberStaffLayout.createSequentialGroup()
                                                    .addComponent(lblMemberStaffGender)
                                                    .addGap(28, 28, 28))
                                                .addComponent(lblMemberStaffFirstName, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(memberStaffLayout.createSequentialGroup()
                                                    .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblMemberStaffCourse)
                                                        .addComponent(lblMemberStaffAddress))
                                                    .addGap(20, 20, 20)))
                                            .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(memberStaffLayout.createSequentialGroup()
                                                    .addGap(70, 70, 70)
                                                    .addComponent(comboMemberStaffPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, memberStaffLayout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(comboMemberStaffGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtMemberStaffAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addComponent(txtMemberStaffFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(77, 77, 77)
                                    .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMemberStaffPhone)
                                        .addComponent(lblMemberStaffLastName)
                                        .addComponent(lblMemberStaffDOB)
                                        .addComponent(lblMemberStaffTeam))
                                    .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(memberStaffLayout.createSequentialGroup()
                                            .addGap(38, 38, 38)
                                            .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtMemberStaffPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtMemberStaffLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtMemberStaffDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(memberStaffLayout.createSequentialGroup()
                                            .addGap(37, 37, 37)
                                            .addComponent(comboMemberStaffDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(memberStaffLayout.createSequentialGroup()
                                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(memberStaffLayout.createSequentialGroup()
                                        .addGap(249, 249, 249)
                                        .addComponent(jLabel6))
                                    .addGroup(memberStaffLayout.createSequentialGroup()
                                        .addGap(343, 343, 343)
                                        .addComponent(lblMemberStaffID)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                                .addComponent(editMemberLogo))))
                    .addGroup(memberStaffLayout.createSequentialGroup()
                        .addGap(485, 485, 485)
                        .addComponent(btnSaveMemberStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(155, 155, 155))
        );
        memberStaffLayout.setVerticalGroup(
            memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memberStaffLayout.createSequentialGroup()
                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(memberStaffLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnEditMemberStaffBack))
                    .addGroup(memberStaffLayout.createSequentialGroup()
                        .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(memberStaffLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMemberStaffID))
                            .addGroup(memberStaffLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(editMemberLogo)))
                        .addGap(92, 92, 92)
                        .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(memberStaffLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(memberStaffLayout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addComponent(lblMemberStaffPhone)
                                        .addGap(32, 32, 32)
                                        .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblMemberStaffDOB)
                                            .addComponent(txtMemberStaffAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(24, 24, 24)
                                        .addComponent(lblMemberStaffTeam))
                                    .addComponent(lblMemberStaffLastName)
                                    .addComponent(txtMemberStaffLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(memberStaffLayout.createSequentialGroup()
                                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMemberStaffFirstName)
                                    .addComponent(txtMemberStaffFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStaffGender)
                                    .addComponent(txtMemberStaffPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboMemberStaffGender, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStaffAddress)
                                    .addComponent(txtMemberStaffDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMemberStaffCourse)
                                    .addComponent(comboMemberStaffDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboMemberStaffPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(48, 48, 48)
                        .addGroup(memberStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMemberStaffPersonalTrainer)
                            .addComponent(comboMemberStaffPersonalTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(82, 82, 82)
                .addComponent(btnSaveMemberStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Member(Staff)", memberStaff);

        employee.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 50, 50));
        jLabel7.setText("Employee");

        btnEditEmployeeBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnEditEmployeeBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditEmployeeBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditEmployeeBackMouseClicked(evt);
            }
        });

        lblEmployeeEditFirstName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeEditFirstName.setText("First Name");

        lblEmployeeEditLastName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeEditLastName.setText("Last Name");

        lblEmployeeEditGender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeEditGender.setText("Gender");

        comboEmployeeEditGender.setBackground(new java.awt.Color(255, 50, 50));
        comboEmployeeEditGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        lblEmployeeEditPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeEditPhone.setText("Phone");

        lblEmployeeEditAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeEditAddress.setText("Address");

        lblEmployeeEditDOB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeEditDOB.setText("D.O.B");

        lblEmployeeEditSalary.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeEditSalary.setText("Salary");

        btnSaveEmployee.setBackground(new java.awt.Color(255, 50, 50));
        btnSaveEmployee.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSaveEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveEmployee.setText("Save Changes");
        btnSaveEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveEmployeeActionPerformed(evt);
            }
        });

        lblEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 50, 50));
        lblEmployeeID.setText("Employee ID : ");

        employeeLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        employeeLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout employeeLayout = new javax.swing.GroupLayout(employee);
        employee.setLayout(employeeLayout);
        employeeLayout.setHorizontalGroup(
            employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeLayout.createSequentialGroup()
                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeLayout.createSequentialGroup()
                                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmployeeEditGender)
                                    .addComponent(lblEmployeeEditFirstName)
                                    .addComponent(lblEmployeeEditAddress))
                                .addGap(51, 51, 51)
                                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmployeeEditAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmployeeEditFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboEmployeeEditGender, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)
                                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmployeeEditLastName)
                                    .addComponent(lblEmployeeEditPhone)
                                    .addComponent(lblEmployeeEditDOB))
                                .addGap(41, 41, 41)
                                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmployeeEditPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmployeeEditLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmployeeEditDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(employeeLayout.createSequentialGroup()
                                .addGap(255, 255, 255)
                                .addComponent(lblEmployeeEditSalary)
                                .addGap(47, 47, 47)
                                .addComponent(txtEmployeeEditSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(employeeLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnEditEmployeeBack)
                        .addGap(383, 383, 383)
                        .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblEmployeeID))
                            .addGroup(employeeLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                                .addComponent(employeeLogo)))))
                .addGap(155, 155, 155))
            .addGroup(employeeLayout.createSequentialGroup()
                .addGap(480, 480, 480)
                .addComponent(btnSaveEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        employeeLayout.setVerticalGroup(
            employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeLayout.createSequentialGroup()
                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(employeeLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnEditEmployeeBack)
                        .addGap(153, 153, 153))
                    .addGroup(employeeLayout.createSequentialGroup()
                        .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(employeeLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, employeeLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(employeeLogo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(lblEmployeeID)
                        .addGap(106, 106, 106)))
                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeEditFirstName)
                    .addComponent(txtEmployeeEditFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeEditLastName)
                    .addComponent(txtEmployeeEditLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeEditGender)
                    .addComponent(comboEmployeeEditGender, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeEditPhone)
                    .addComponent(txtEmployeeEditPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeEditAddress)
                    .addComponent(txtEmployeeEditAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeEditDOB)
                    .addComponent(txtEmployeeEditDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(employeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeEditSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeEditSalary))
                .addGap(113, 113, 113)
                .addComponent(btnSaveEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Employee(Regular)", employee);

        trainerList.setBackground(new java.awt.Color(255, 255, 255));

        lblPersonalTrainer.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 36)); // NOI18N
        lblPersonalTrainer.setForeground(new java.awt.Color(255, 50, 50));
        lblPersonalTrainer.setText("Personal Trainer");

        btnTrainerMembersListBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/back.png"))); // NOI18N
        btnTrainerMembersListBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTrainerMembersListBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrainerMembersListBackMouseClicked(evt);
            }
        });

        listTrainerMembers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listTrainerMembers);

        lblTrainerEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTrainerEmployeeID.setForeground(new java.awt.Color(255, 50, 50));
        lblTrainerEmployeeID.setText("Employee ID : ");

        btnRemoveMemberFromTrainer.setBackground(new java.awt.Color(255, 50, 50));
        btnRemoveMemberFromTrainer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRemoveMemberFromTrainer.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveMemberFromTrainer.setText("Remove Member");
        btnRemoveMemberFromTrainer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoveMemberFromTrainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMemberFromTrainerActionPerformed(evt);
            }
        });

        trainerListLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI_GymSystem/small_icon.png"))); // NOI18N
        trainerListLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        trainerListLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trainerListLogoMouseClicked(evt);
            }
        });

        btnSearchTrainerMemberList.setBackground(new java.awt.Color(255, 50, 50));
        btnSearchTrainerMemberList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchTrainerMemberList.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchTrainerMemberList.setText("Search");
        btnSearchTrainerMemberList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchTrainerMemberList.setDoubleBuffered(true);
        btnSearchTrainerMemberList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchTrainerMemberListActionPerformed(evt);
            }
        });

        lblTrainerMemberSearchList.setFont(new java.awt.Font("Microsoft New Tai Lue", 0, 18)); // NOI18N
        lblTrainerMemberSearchList.setForeground(new java.awt.Color(255, 50, 50));
        lblTrainerMemberSearchList.setText("Member ID : ");

        javax.swing.GroupLayout trainerListLayout = new javax.swing.GroupLayout(trainerList);
        trainerList.setLayout(trainerListLayout);
        trainerListLayout.setHorizontalGroup(
            trainerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerListLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnTrainerMembersListBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPersonalTrainer)
                .addGap(265, 265, 265)
                .addComponent(trainerListLogo)
                .addGap(155, 155, 155))
            .addGroup(trainerListLayout.createSequentialGroup()
                .addGroup(trainerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(trainerListLayout.createSequentialGroup()
                        .addGap(474, 474, 474)
                        .addComponent(lblTrainerEmployeeID))
                    .addGroup(trainerListLayout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(trainerListLayout.createSequentialGroup()
                        .addGap(391, 391, 391)
                        .addComponent(lblTrainerMemberSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTrainerMemberSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchTrainerMemberList))
                    .addGroup(trainerListLayout.createSequentialGroup()
                        .addGap(474, 474, 474)
                        .addComponent(btnRemoveMemberFromTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(277, Short.MAX_VALUE))
        );
        trainerListLayout.setVerticalGroup(
            trainerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerListLayout.createSequentialGroup()
                .addGroup(trainerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(trainerListLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btnTrainerMembersListBack))
                    .addGroup(trainerListLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(trainerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPersonalTrainer)
                            .addComponent(trainerListLogo))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTrainerEmployeeID)
                .addGap(38, 38, 38)
                .addGroup(trainerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTrainerMemberSearchList)
                    .addComponent(txtTrainerMemberSearchList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchTrainerMemberList))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRemoveMemberFromTrainer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        //btnAddMember.setFocusPainted(false);

        tabbedPanel.addTab("TrainerMembersList", trainerList);

        getContentPane().add(tabbedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -32, 1180, 870));

        setSize(new java.awt.Dimension(1073, 695));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //All methoeds involving tabbedPannel are used to go from one page to another

    private void btnManageMembersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageMembersActionPerformed
        tabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_btnManageMembersActionPerformed

    private void btnManageEmployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageEmployeesActionPerformed
        tabbedPanel.setSelectedIndex(2);
    }//GEN-LAST:event_btnManageEmployeesActionPerformed

    private void btnAddMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMemberActionPerformed
        comboMemberPersonalTrainer.setSelectedIndex(0);
        tabbedPanel.setSelectedIndex(3);
    }//GEN-LAST:event_btnAddMemberActionPerformed

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed
        tabbedPanel.setSelectedIndex(4);
    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void radioMemberStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMemberStudentActionPerformed
        lblMemberPosition_Course.setText("Course");
        lblMemberDepartment_Team.setText("Team");
        changeCourseCombo();
        changeTeamCombo();
    }//GEN-LAST:event_radioMemberStudentActionPerformed

    private void radioMemberStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMemberStaffActionPerformed
        lblMemberPosition_Course.setText("Position");
        lblMemberDepartment_Team.setText("Department");
        changePositionCombo();
        changeDepartmentCombo();
    }//GEN-LAST:event_radioMemberStaffActionPerformed

    /**
     * Name: btnCreateMemberActionPerformed Purpose/description: A button that
     * handles the event of Adding a new member to the system
     */
    private void btnCreateMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateMemberActionPerformed
        //get all the necessary information to create a member object
        String fname = txtMemberFirstName.getText();
        String fname_error;
        if (fname.matches("[a-zA-Z\\s]+")) {
            fname_error = "";
        } else {
            fname_error = "\nNote (First Name Field Must Contain Letters Only!)";
        }
        String lname = txtMemberLastName.getText();
        String lname_error;
        if (lname.matches("[a-zA-Z\\s]+")) {
            lname_error = "";
        } else {
            lname_error = "\nNote (Last Name Field Must Contain Letters Only!)";
        }
        String gender = String.valueOf(comboMemberGender.getSelectedItem());
        String phone = txtMemberPhone.getText();
        //create an error if phone string contains anything other than digits
        String phone_error;
        if (phone.matches("[0-9]+")) {
            phone_error = "";
        } else {
            phone_error = "\nNote (Phone Field Must Contain Digits Only!)";
        }
        String address = txtMemberAddress.getText();
        String DOB = txtMemberDOB.getText();
        String position_course = String.valueOf(comboMemberPosition_Course.getSelectedItem());
        String department_team = String.valueOf(comboMemberDepartment_Team.getSelectedItem());
        int spaceIndex = String.valueOf(comboMemberPersonalTrainer.getSelectedItem()).indexOf(" ");
        String trainer_ID_txt;
        try {
            trainer_ID_txt = String.valueOf(comboMemberPersonalTrainer.getSelectedItem()).substring(0, spaceIndex);
        } catch (StringIndexOutOfBoundsException ex) {
            trainer_ID_txt = "";
        }

        boolean isStaff = false;
        if (radioMemberStaff.isSelected()) {
            isStaff = true;
        } else if (radioMemberStudent.isSelected()) {
            isStaff = false;
        }

        if (!fname.isEmpty() & !lname.isEmpty() & !gender.isEmpty() & !phone.isEmpty() & !address.isEmpty() & !DOB.isEmpty() & phone_error.isEmpty() & fname_error.isEmpty() & lname_error.isEmpty()) {
            //Create a new member object 
            int member_ID = system.addMember(fname, lname, gender, phone, address, DOB, position_course, department_team, isStaff);
            //Asign A Member to a PersonalTrainer if selected.
            if (!trainer_ID_txt.isEmpty()) {
                int trainer_ID = Integer.parseInt(trainer_ID_txt);
                system.assignMemberToTrainer(member_ID, trainer_ID);
            }
            //update the members list in members page
            updateMemberList();
            //Set all input fields to empty
            txtMemberFirstName.setText("");
            txtMemberLastName.setText("");
            txtMemberPhone.setText("");
            txtMemberAddress.setText("");
            txtMemberDOB.setText("");
            comboMemberPosition_Course.setSelectedIndex(0);
            comboMemberDepartment_Team.setSelectedIndex(0);
            comboMemberPersonalTrainer.setSelectedIndex(0);
            comboMemberGender.setSelectedIndex(0);
            //show message to user
            JOptionPane.showMessageDialog(rootPane, "A New Member Has been Successfully Added To The System!\nMember ID : " + member_ID, "New Member Added", 1);
        } else {
            JOptionPane.showMessageDialog(rootPane, "You have to fill in all the fields" + fname_error + lname_error + phone_error, "Error", 0);
        }

    }//GEN-LAST:event_btnCreateMemberActionPerformed

    /**
     * Name: btnCreateEmployeeActionPerformed Purpose/description: A button that
     * handles the event of Adding a new employee to the system
     */
    private void btnCreateEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateEmployeeActionPerformed
        String fname = txtEmployeeFirstName.getText();
        String fname_error;
        if (fname.matches("[a-zA-Z\\s]+")) {
            fname_error = "";
        } else {
            fname_error = "\nNote (First Name Field Must Contain Letters Only!)";
        }
        String lname = txtEmployeeLastName.getText();
        String lname_error;
        if (lname.matches("[a-zA-Z\\s]+")) {
            lname_error = "";
        } else {
            lname_error = "\nNote (Last Name Field Must Contain Letters Only!)";
        }
        String gender = String.valueOf(comboEmployeeGender.getSelectedItem());
        String phone = txtEmployeePhone.getText();
        //create an error if phone string contains anything other than digits
        String phone_error;
        if (phone.matches("[0-9]+")) {
            phone_error = "";
        } else {
            phone_error = "\nNote (Phone Field Must Contain Digits Only!)";
        }
        String address = txtEmployeeAddress.getText();
        String DOB = txtEmployeeDOB.getText();
        String salary_txt = txtEmployeeSalary.getText();
        //create an error if salary is not a number;
        double salary = 0.0;
        String salary_error = "";
        try {
            salary = Double.parseDouble(salary_txt);
        } catch (NumberFormatException exp) {
            salary_error = "\nNote (Salary Field Must Contain a Number Only!)";
        }

        boolean isPT = false;
        if (radioEmployeePersonalTrainer.isSelected()) {
            isPT = true;
        } else if (radioEmployeeRegular.isSelected()) {
            isPT = false;
        }

        if (!fname.isEmpty() && !lname.isEmpty() && !gender.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !DOB.isEmpty() & salary_error.isEmpty() & phone_error.isEmpty() & fname_error.isEmpty() & lname_error.isEmpty()) {
            //Create a new employee object
            int id = system.addEmployee(fname, lname, gender, phone, address, DOB, salary, isPT);
            //update the employees list in members page
            updateEmployeeList();
            updatePersonalTrainerCombo();
            //Set all input fields to empty
            txtEmployeeFirstName.setText("");
            txtEmployeeLastName.setText("");
            txtEmployeePhone.setText("");
            txtEmployeeAddress.setText("");
            txtEmployeeDOB.setText("");
            txtEmployeeSalary.setText("");
            comboEmployeeGender.setSelectedIndex(0);
            //show message to user
            JOptionPane.showMessageDialog(rootPane, "A New Employee Has been Successfully Added To The System!\nEmployee ID : " + id, "New Employee Added", 1);
        } else {
            JOptionPane.showMessageDialog(rootPane, "You have to fill in all the fields" + fname_error + lname_error + phone_error + salary_error, "Error", 0);
        }
    }//GEN-LAST:event_btnCreateEmployeeActionPerformed

    private void btnMembersBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMembersBackMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_btnMembersBackMouseClicked

    private void btnEmployeesBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeesBackMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_btnEmployeesBackMouseClicked

    private void btnAddMemberBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMemberBackMouseClicked
        tabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_btnAddMemberBackMouseClicked

    private void btnAddEmployeeBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddEmployeeBackMouseClicked
        tabbedPanel.setSelectedIndex(2);
    }//GEN-LAST:event_btnAddEmployeeBackMouseClicked

    private void btnEditMemberStudentBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMemberStudentBackMouseClicked
        tabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_btnEditMemberStudentBackMouseClicked

    private void btnEditMemberStaffBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMemberStaffBackMouseClicked
        tabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_btnEditMemberStaffBackMouseClicked

    private void btnEditEmployeeBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditEmployeeBackMouseClicked
        tabbedPanel.setSelectedIndex(2);
    }//GEN-LAST:event_btnEditEmployeeBackMouseClicked

    private void btnTrainerMembersListBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrainerMembersListBackMouseClicked
        tabbedPanel.setSelectedIndex(2);
    }//GEN-LAST:event_btnTrainerMembersListBackMouseClicked

    /**
     * Name: exitSystemActionPerformed Purpose/description: A button that
     * handles the event of serializing the system object before exiting the
     * program
     */
    private void exitSystemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitSystemActionPerformed
        system.serializeObject();
        System.exit(0);
    }//GEN-LAST:event_exitSystemActionPerformed

    /**
     * Name: btnEditMemberActionPerformed Purpose/description: A button that
     * handles the event of going to the edit page for Members and calling the
     * ShowDetails method.
     */
    private void btnEditMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMemberActionPerformed
        String member_info = listMembers.getSelectedValue();
        // check if user selected a member or not
        if (member_info == null) {
            JOptionPane.showMessageDialog(rootPane, "Please Select A Member!", "No Member Selected", 0);
        } else {
            int spaceIndex = member_info.indexOf(" ");
            int ID = Integer.parseInt(member_info.substring(0, spaceIndex));
            Member member = system.findMember(ID);
            // if member is a student go to student page else go to staff page
            if (member instanceof Student) {
                tabbedPanel.setSelectedIndex(5);
                showStudentDetails((Student) member);
                lblMemberStudentID.setText("Member ID : " + ID);
            } else {
                tabbedPanel.setSelectedIndex(6);
                showStaffDetails((Staff) member);
                lblMemberStaffID.setText("Member ID : " + ID);
            }
        }
    }//GEN-LAST:event_btnEditMemberActionPerformed

    /**
     * Name: btnSaveMemberStudentActionPerformed Purpose/description: A button
     * that handles the event of saving New information to the specified student
     * object.
     */
    private void btnSaveMemberStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveMemberStudentActionPerformed
        //Smillair to the create Method but instead of creating a new object we edit an existing object
        int choice = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You Want To Save These Changes?", "Save", 0);
        if (choice == 0) {
            String fname = txtMemberStudentFirstName.getText();
            String fname_error;
            if (fname.matches("[a-zA-Z\\s]+")) {
                fname_error = "";
            } else {
                fname_error = "\nNote (First Name Field Must Contain Letters Only!)";
            }
            String lname = txtMemberStudentLastName.getText();
            String lname_error;
            if (lname.matches("[a-zA-Z\\s]+")) {
                lname_error = "";
            } else {
                lname_error = "\nNote (Last Name Field Must Contain Letters Only!)";
            }
            String gender = String.valueOf(comboMemberStudentGender.getSelectedItem());
            String phone = txtMemberStudentPhone.getText();
            String phone_error;
            if (phone.matches("[0-9]+")) {
                phone_error = "";
            } else {
                phone_error = "\nNote (Phone Field Must Contain Digits Only!)";
            }
            String address = txtMemberStudentAddress.getText();
            String DOB = txtMemberStudentDOB.getText();
            String course = String.valueOf(comboMemberStudentCourse.getSelectedItem());
            String team = String.valueOf(comboMemberStudentTeam.getSelectedItem());
            int ID = Integer.parseInt(lblMemberStudentID.getText().substring(12));

            if (!fname.isEmpty() & !lname.isEmpty() & !phone.isEmpty() & !address.isEmpty() & !DOB.isEmpty() & phone_error.isEmpty() & fname_error.isEmpty() & lname_error.isEmpty()) {
                system.editMemberDetails(ID, fname, lname, gender, phone, address, DOB, course, team);
                int spaceIndex = String.valueOf(comboMemberStudentPersonalTrainer.getSelectedItem()).indexOf(" ");
                String trainer_ID_txt;
                try {
                    trainer_ID_txt = String.valueOf(comboMemberStudentPersonalTrainer.getSelectedItem()).substring(0, spaceIndex);
                } catch (StringIndexOutOfBoundsException ex) {
                    trainer_ID_txt = "";
                }
                PersonalTrainer trainer = system.findMember(ID).getTrainer();
                int new_Trainer_ID;
                if (trainer_ID_txt.isEmpty() & trainer != null) { //from trainer to null
                    system.removeMemberFromTrainer(ID, trainer.getEmployee_ID());
                } else if (!trainer_ID_txt.isEmpty() & trainer == null) { //from null to trainer
                    new_Trainer_ID = Integer.parseInt(trainer_ID_txt);
                    system.assignMemberToTrainer(ID, new_Trainer_ID);
                } else if (!trainer_ID_txt.isEmpty() & trainer != null) { //from trainer to trainer
                    new_Trainer_ID = Integer.parseInt(trainer_ID_txt);
                    system.removeMemberFromTrainer(ID, trainer.getEmployee_ID());
                    system.assignMemberToTrainer(ID, new_Trainer_ID);
                }
                updateMemberList();
                tabbedPanel.setSelectedIndex(1);
            } else {
                JOptionPane.showMessageDialog(rootPane, "You have to fill in all the fields" + fname_error + lname_error + phone_error, "Error", 0);
            }

        }

    }//GEN-LAST:event_btnSaveMemberStudentActionPerformed

    /**
     * Name: btnSaveMemberStaffActionPerformed Purpose/description: A button
     * that handles the event of saving New information to the specified staff
     * object.
     */
    private void btnSaveMemberStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveMemberStaffActionPerformed
        //Smillair to the create Method but instead of creating a new object we edit an existing object
        int choice = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You Want To Save These Changes?", "Save", 0);
        if (choice == 0) {
            String fname = txtMemberStaffFirstName.getText();
            String fname_error;
            if (fname.matches("[a-zA-Z\\s]+")) {
                fname_error = "";
            } else {
                fname_error = "\nNote (First Name Field Must Contain Letters Only!)";
            }
            String lname = txtMemberStaffLastName.getText();
            String lname_error;
            if (lname.matches("[a-zA-Z\\s]+")) {
                lname_error = "";
            } else {
                lname_error = "\nNote (Last Name Field Must Contain Letters Only!)";
            }
            String gender = String.valueOf(comboMemberStaffGender.getSelectedItem());
            String phone = txtMemberStaffPhone.getText();
            String phone_error;
            if (phone.matches("[0-9]+")) {
                phone_error = "";
            } else {
                phone_error = "\nNote (Phone Field Must Contain Digits Only!)";
            }
            String address = txtMemberStaffAddress.getText();
            String DOB = txtMemberStaffDOB.getText();
            String position = String.valueOf(comboMemberStaffPosition.getSelectedItem());
            String department = String.valueOf(comboMemberStaffDepartment.getSelectedItem());
            int ID = Integer.parseInt(lblMemberStaffID.getText().substring(12));

            if (!fname.isEmpty() & !lname.isEmpty() & !phone.isEmpty() & !address.isEmpty() & !DOB.isEmpty() & phone_error.isEmpty() & fname_error.isEmpty() & lname_error.isEmpty()) {
                system.editMemberDetails(ID, fname, lname, gender, phone, address, DOB, position, department);

                int spaceIndex = String.valueOf(comboMemberStaffPersonalTrainer.getSelectedItem()).indexOf(" ");
                String trainer_ID_txt;
                try {
                    trainer_ID_txt = String.valueOf(comboMemberStaffPersonalTrainer.getSelectedItem()).substring(0, spaceIndex);
                } catch (StringIndexOutOfBoundsException ex) {
                    trainer_ID_txt = "";
                }
                PersonalTrainer trainer = system.findMember(ID).getTrainer();
                int new_Trainer_ID;
                if (trainer_ID_txt.isEmpty() & trainer != null) { //from trainer to null
                    system.removeMemberFromTrainer(ID, trainer.getEmployee_ID());
                } else if (!trainer_ID_txt.isEmpty() & trainer == null) { //from null to trainer
                    new_Trainer_ID = Integer.parseInt(trainer_ID_txt);
                    system.assignMemberToTrainer(ID, new_Trainer_ID);
                } else if (!trainer_ID_txt.isEmpty() & trainer != null) { //from trainer to trainer
                    new_Trainer_ID = Integer.parseInt(trainer_ID_txt);
                    system.removeMemberFromTrainer(ID, trainer.getEmployee_ID());
                    system.assignMemberToTrainer(ID, new_Trainer_ID);
                }
                updateMemberList();
                tabbedPanel.setSelectedIndex(1);
            } else {
                JOptionPane.showMessageDialog(rootPane, "You have to fill in all the fields" + fname_error + lname_error + phone_error, "Error", 0);
            }
        }
    }//GEN-LAST:event_btnSaveMemberStaffActionPerformed

    /**
     * Name: btnSaveEmployeeActionPerformed Purpose/description: A button that
     * handles the event of saving New information to the specified employee
     * object.
     */
    private void btnSaveEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveEmployeeActionPerformed
        //Smillair to the create Method but instead of creating a new object we edit an existing object
        int choice = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You Want To Save These Changes?", "Save", 0);
        if (choice == 0) {
            String fname = txtEmployeeEditFirstName.getText();
            String fname_error;
            if (fname.matches("[a-zA-Z\\s]+")) {
                fname_error = "";
            } else {
                fname_error = "\nNote (First Name Field Must Contain Letters Only!)";
            }
            String lname = txtEmployeeEditLastName.getText();
            String lname_error;
            if (lname.matches("[a-zA-Z\\s]+")) {
                lname_error = "";
            } else {
                lname_error = "\nNote (Last Name Field Must Contain Letters Only!)";
            }
            String gender = String.valueOf(comboEmployeeEditGender.getSelectedItem());
            String phone = txtEmployeeEditPhone.getText();
            String phone_error;
            if (phone.matches("[0-9]+")) {
                phone_error = "";
            } else {
                phone_error = "\nNote (Phone Field Must Contain Digits Only!)";
            }
            String address = txtEmployeeEditAddress.getText();
            String DOB = txtEmployeeEditDOB.getText();
            String salary_txt = txtEmployeeEditSalary.getText();
            double salary = 0.0;
            String salary_error = "";
            try {
                salary = Double.parseDouble(salary_txt);
            } catch (NumberFormatException exp) {
                salary_error = "\nNote (Salary Field Must Contain a Number Only!)";
            }
            if (!fname.isEmpty() && !lname.isEmpty() && !gender.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !DOB.isEmpty() & salary_error.isEmpty() & phone_error.isEmpty() & fname_error.isEmpty() & lname_error.isEmpty()) {
                int ID = Integer.parseInt(lblEmployeeID.getText().substring(14));
                system.editEmployeeDetails(ID, fname, lname, gender, phone, address, DOB, salary);
                updateEmployeeList();
                updatePersonalTrainerCombo();
                tabbedPanel.setSelectedIndex(2);
            } else {
                JOptionPane.showMessageDialog(rootPane, "You have to fill in all the fields" + fname_error + lname_error + phone_error + salary_error, "Error", 0);
            }
        }
    }//GEN-LAST:event_btnSaveEmployeeActionPerformed

    /**
     * Name: btnSaveEmployeeActionPerformed Purpose/description: A button that
     * handles the event of going to the edit page for Employees and calling the
     * ShowDetails method.
     */
    private void btnEditEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditEmployeeActionPerformed
        String employee_info = listEmployees.getSelectedValue();
        // check if a user selected an employee
        if (employee_info == null) {
            JOptionPane.showMessageDialog(rootPane, "Please Select An Employee!", "No Employee Selected", 0);
        } else {
            int spaceIndex = employee_info.indexOf(" ");
            int ID = Integer.parseInt(employee_info.substring(0, spaceIndex));
            Employee emp = system.findEmployee(ID);
            tabbedPanel.setSelectedIndex(7);
            showEmployeeDetails(emp);
            lblEmployeeID.setText("Employee ID : " + ID);
        }
    }//GEN-LAST:event_btnEditEmployeeActionPerformed

    /**
     * Name: btnDeleteMemberActionPerformed Purpose/description: A button that
     * handles the event of deleting a member object from the system
     */
    private void btnDeleteMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMemberActionPerformed
        String member_info = listMembers.getSelectedValue();
        // check if a user selected a member and ask for confirmation before deleting the member from the system
        if (member_info == null) {
            JOptionPane.showMessageDialog(rootPane, "Please Select A Member!", "No Member Selected", 0);
        } else {
            int spaceIndex = member_info.indexOf(" ");
            int ID = Integer.parseInt(member_info.substring(0, spaceIndex));
            int choice = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You Want To Delete This Member?", "Warnning!", 0);
            if (choice == 0) {
                system.deleteMember(ID);
                updateMemberList();
            }
        }
    }//GEN-LAST:event_btnDeleteMemberActionPerformed

    /**
     * Name: btnDeleteEmployeeActionPerformed Purpose/description: A button that
     * handles the event of deleting an employee object from the system
     */
    private void btnDeleteEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteEmployeeActionPerformed
        String employee_info = listEmployees.getSelectedValue();
        // check if a user selected an employee and ask for confirmation before deleting the employee from the system
        if (employee_info == null) {
            JOptionPane.showMessageDialog(rootPane, "Please Select An Employee!", "No Employee Selected", 0);
        } else {
            int spaceIndex = employee_info.indexOf(" ");
            int ID = Integer.parseInt(employee_info.substring(0, spaceIndex));
            int choice = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You Want To Delete This Employee?", "Warnning!", 0);
            if (choice == 0) {
                boolean isDeleted = system.deleteEmployee(ID);
                if (!isDeleted) {
                    JOptionPane.showMessageDialog(rootPane, "You Cant Delete A Personal Trainer With Members Assigned To Him/Her", "Error!", 0);
                }
                updateEmployeeList();
                updatePersonalTrainerCombo();
            }
        }
    }//GEN-LAST:event_btnDeleteEmployeeActionPerformed

    /**
     * Name: btnShowMemberListActionPerformed Purpose/description: A button that
     * handles the event of going to the TrainerList Page.
     */
    private void btnShowMemberListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowMemberListActionPerformed
        // check if a user selected a personal trainer. if user selected a regular employee display a special message.
        String employee_info = listEmployees.getSelectedValue();
        if (employee_info == null) {
            JOptionPane.showMessageDialog(rootPane, "Please Select A Personal Trainer!", "No Personal Trainer Selected", 0);
        } else {
            int spaceIndex = employee_info.indexOf(" ");
            int ID = Integer.parseInt(employee_info.substring(0, spaceIndex));
            Employee emp = system.findEmployee(ID);
            if (emp instanceof PersonalTrainer) {
                updateTrainerMembersList(ID);
                lblTrainerEmployeeID.setText("Employee ID : " + ID);
                tabbedPanel.setSelectedIndex(8);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Select A Personal Trainer!\nYou Cant Select a Regular Employee For This Purpose", "No Personal Trainer Selected", 0);
            }
        }
    }//GEN-LAST:event_btnShowMemberListActionPerformed

    /**
     * Name: btnRemoveMemberFromTrainerActionPerformed Purpose/description: A
     * button that handles the event of removing a specific member from a
     * trainer.
     */
    private void btnRemoveMemberFromTrainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMemberFromTrainerActionPerformed
        //check if user selected a member if so ask for confirmation before deleting member.
        String member_info = listTrainerMembers.getSelectedValue();
        if (member_info == null) {
            JOptionPane.showMessageDialog(rootPane, "Please Select A Member!", "No Member Selected", 0);
        } else {
            int spaceIndex = member_info.indexOf(" ");
            int member_ID = Integer.parseInt(member_info.substring(0, spaceIndex));
            int trainer_ID = Integer.parseInt(lblTrainerEmployeeID.getText().substring(14));
            int choice = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You Want To Remove This Member From This Personal Trainer?", "Warnning!", 0);
            if (choice == 0) {
                system.removeMemberFromTrainer(member_ID, trainer_ID);
                updateTrainerMembersList(trainer_ID);
            }
        }
    }//GEN-LAST:event_btnRemoveMemberFromTrainerActionPerformed

    /**
     * Name: formWindowActivated Purpose/description: As soon as the GUI loads
     * up change the icon.
     */
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        ImageIcon icon = new ImageIcon("src/GUI_GymSystem/favicon.png");
        setIconImage(icon.getImage());

    }//GEN-LAST:event_formWindowActivated

    private void ManageMembersLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManageMembersLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_ManageMembersLogoMouseClicked

    private void manageEmployeesLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageEmployeesLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_manageEmployeesLogoMouseClicked

    private void AddMemberLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddMemberLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_AddMemberLogoMouseClicked

    private void AddEmployeeLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEmployeeLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_AddEmployeeLogoMouseClicked

    private void MemberStudentLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MemberStudentLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_MemberStudentLogoMouseClicked

    private void editMemberLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMemberLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_editMemberLogoMouseClicked

    private void employeeLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_employeeLogoMouseClicked

    private void trainerListLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trainerListLogoMouseClicked
        tabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_trainerListLogoMouseClicked

    /**
     * Name: btnMarketingReportActionPerformed Purpose/description: A button
     * that handles the event of Creating a Marketing report file and returns
     * the Absolute Path for the file.
     */
    private void btnMarketingReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarketingReportActionPerformed
        String filePath = system.produceMarketingReport();
        JOptionPane.showMessageDialog(rootPane, "File Path: " + filePath, "Marketing Report Created", 1);
    }//GEN-LAST:event_btnMarketingReportActionPerformed

    /**
     * Name: btnSearchMemberListActionPerformed Purpose/description: A button
     * that handles the event of searching for a member in the MemberList.
     */
    private void btnSearchMemberListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchMemberListActionPerformed
        String ID = txtMemberSearchList.getText().strip();
        if (!ID.isEmpty()) {
            boolean found = false;
            int i = 0;
            int size = listMembers.getModel().getSize();
            while (!found & i < size) {
                int spaceIndex = listMembers.getModel().getElementAt(i).indexOf(" ");
                String listID = listMembers.getModel().getElementAt(i).substring(0, spaceIndex);
                if (listID.equals(ID)) {
                    listMembers.setSelectedIndex(i);
                    found = true;
                } else {
                    i++;
                }
            }
            txtMemberSearchList.setText("");
            if (!found) {
                JOptionPane.showMessageDialog(rootPane, "Invalid ID or Member Does not exist", "Error", 0);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Enter an ID!", "Error", 0);
        }
    }//GEN-LAST:event_btnSearchMemberListActionPerformed

    /**
     * Name: btnEmployeeSearchListActionPerformed Purpose/description: A button
     * that handles the event of searching for an employee in the EmployeeList.
     */
    private void btnEmployeeSearchListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeSearchListActionPerformed
        String ID = txtEmployeeSearchList.getText().strip();
        if (!ID.isEmpty()) {
            boolean found = false;
            int i = 0;
            int size = listEmployees.getModel().getSize();
            while (!found & i < size) {
                int spaceIndex = listEmployees.getModel().getElementAt(i).indexOf(" ");
                String listID = listEmployees.getModel().getElementAt(i).substring(0, spaceIndex);
                if (listID.equals(ID)) {
                    listEmployees.setSelectedIndex(i);
                    found = true;
                } else {
                    i++;
                }
            }
            txtEmployeeSearchList.setText("");
            if (!found) {
                JOptionPane.showMessageDialog(rootPane, "Invalid ID or Employee Does not exist", "Error", 0);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Enter an ID!", "Error", 0);
        }
    }//GEN-LAST:event_btnEmployeeSearchListActionPerformed

    /**
     * Name: btnSearchTrainerMemberListActionPerformed Purpose/description: A
     * button that handles the event of searching for a member in the
     * TrainerList.
     */
    private void btnSearchTrainerMemberListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchTrainerMemberListActionPerformed
        String ID = txtTrainerMemberSearchList.getText().strip();
        if (!ID.isEmpty()) {
            boolean found = false;
            int i = 0;
            int size = listTrainerMembers.getModel().getSize();
            while (!found & i < size) {
                int spaceIndex = listTrainerMembers.getModel().getElementAt(i).indexOf(" ");
                String listID = listTrainerMembers.getModel().getElementAt(i).substring(0, spaceIndex);
                if (listID.equals(ID)) {
                    listTrainerMembers.setSelectedIndex(i);
                    found = true;
                } else {
                    i++;
                }
            }
            txtTrainerMemberSearchList.setText("");
            if (!found) {
                JOptionPane.showMessageDialog(rootPane, "Invalid ID or Member Does not exist", "Error", 0);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Enter an ID!", "Error", 0);
        }
    }//GEN-LAST:event_btnSearchTrainerMemberListActionPerformed

    /**
     * Name: formWindowClosing Purpose/description: prompting the user to save
     * the progress or not after clicking on the x button in the window.
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int choice = JOptionPane.showConfirmDialog(rootPane, "Do you want to save your progress ?", "Warnning!", 0);
        if (choice == 0) {
            system.serializeObject();
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GymSystemGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GymSystemGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GymSystemGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GymSystemGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GymSystemGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddEmployeeLogo;
    private javax.swing.JLabel AddMemberLogo;
    private javax.swing.JLabel ManageMembersLogo;
    private javax.swing.JLabel MemberStudentLogo;
    private javax.swing.JPanel addEmployee;
    private javax.swing.JPanel addMember;
    private javax.swing.JButton btnAddEmployee;
    private javax.swing.JLabel btnAddEmployeeBack;
    private javax.swing.JButton btnAddMember;
    private javax.swing.JLabel btnAddMemberBack;
    private javax.swing.JButton btnCreateEmployee;
    private javax.swing.JButton btnCreateMember;
    private javax.swing.JButton btnDeleteEmployee;
    private javax.swing.JButton btnDeleteMember;
    private javax.swing.JButton btnEditEmployee;
    private javax.swing.JLabel btnEditEmployeeBack;
    private javax.swing.JButton btnEditMember;
    private javax.swing.JLabel btnEditMemberStaffBack;
    private javax.swing.JLabel btnEditMemberStudentBack;
    private javax.swing.JButton btnEmployeeSearchList;
    private javax.swing.JLabel btnEmployeesBack;
    private javax.swing.JButton btnManageEmployees;
    private javax.swing.JButton btnManageMembers;
    private javax.swing.JButton btnMarketingReport;
    private javax.swing.JLabel btnMembersBack;
    private javax.swing.JButton btnRemoveMemberFromTrainer;
    private javax.swing.JButton btnSaveEmployee;
    private javax.swing.JButton btnSaveMemberStaff;
    private javax.swing.JButton btnSaveMemberStudent;
    private javax.swing.JButton btnSearchMemberList;
    private javax.swing.JButton btnSearchTrainerMemberList;
    private javax.swing.JButton btnShowMemberList;
    private javax.swing.JLabel btnTrainerMembersListBack;
    private javax.swing.JComboBox<String> comboEmployeeEditGender;
    private javax.swing.JComboBox<String> comboEmployeeGender;
    private javax.swing.JComboBox<String> comboMemberDepartment_Team;
    private javax.swing.JComboBox<String> comboMemberGender;
    private javax.swing.JComboBox<String> comboMemberPersonalTrainer;
    private javax.swing.JComboBox<String> comboMemberPosition_Course;
    private javax.swing.JComboBox<String> comboMemberStaffDepartment;
    private javax.swing.JComboBox<String> comboMemberStaffGender;
    private javax.swing.JComboBox<String> comboMemberStaffPersonalTrainer;
    private javax.swing.JComboBox<String> comboMemberStaffPosition;
    private javax.swing.JComboBox<String> comboMemberStudentCourse;
    private javax.swing.JComboBox<String> comboMemberStudentGender;
    private javax.swing.JComboBox<String> comboMemberStudentPersonalTrainer;
    private javax.swing.JComboBox<String> comboMemberStudentTeam;
    private javax.swing.JLabel editMemberLogo;
    private javax.swing.JPanel employee;
    private javax.swing.JLabel employeeLogo;
    private javax.swing.JButton exitSystem;
    private javax.swing.ButtonGroup groupRegularPersonalTrainer;
    private javax.swing.ButtonGroup groupStaffStudent;
    private javax.swing.JLabel imgMainMenu;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddEmployee;
    private javax.swing.JLabel lblAddMember;
    private javax.swing.JLabel lblEmployeeAddress;
    private javax.swing.JLabel lblEmployeeDOB;
    private javax.swing.JLabel lblEmployeeEditAddress;
    private javax.swing.JLabel lblEmployeeEditDOB;
    private javax.swing.JLabel lblEmployeeEditFirstName;
    private javax.swing.JLabel lblEmployeeEditGender;
    private javax.swing.JLabel lblEmployeeEditLastName;
    private javax.swing.JLabel lblEmployeeEditPhone;
    private javax.swing.JLabel lblEmployeeEditSalary;
    private javax.swing.JLabel lblEmployeeFirstName;
    private javax.swing.JLabel lblEmployeeGender;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblEmployeeLastName;
    private javax.swing.JLabel lblEmployeePhone;
    private javax.swing.JLabel lblEmployeeSalary;
    private javax.swing.JLabel lblEmployeeSearchList;
    private javax.swing.JLabel lblEmployees;
    private javax.swing.JLabel lblMemberAddress;
    private javax.swing.JLabel lblMemberDOB;
    private javax.swing.JLabel lblMemberDepartment_Team;
    private javax.swing.JLabel lblMemberFirstName;
    private javax.swing.JLabel lblMemberGender;
    private javax.swing.JLabel lblMemberLastName;
    private javax.swing.JLabel lblMemberPersonalTrainer;
    private javax.swing.JLabel lblMemberPhone;
    private javax.swing.JLabel lblMemberPosition_Course;
    private javax.swing.JLabel lblMemberSearchList;
    private javax.swing.JLabel lblMemberStaffAddress;
    private javax.swing.JLabel lblMemberStaffCourse;
    private javax.swing.JLabel lblMemberStaffDOB;
    private javax.swing.JLabel lblMemberStaffFirstName;
    private javax.swing.JLabel lblMemberStaffGender;
    private javax.swing.JLabel lblMemberStaffID;
    private javax.swing.JLabel lblMemberStaffLastName;
    private javax.swing.JLabel lblMemberStaffPersonalTrainer;
    private javax.swing.JLabel lblMemberStaffPhone;
    private javax.swing.JLabel lblMemberStaffTeam;
    private javax.swing.JLabel lblMemberStudent;
    private javax.swing.JLabel lblMemberStudentAddress;
    private javax.swing.JLabel lblMemberStudentCourse;
    private javax.swing.JLabel lblMemberStudentDOB;
    private javax.swing.JLabel lblMemberStudentFirstName;
    private javax.swing.JLabel lblMemberStudentGender;
    private javax.swing.JLabel lblMemberStudentID;
    private javax.swing.JLabel lblMemberStudentLastName;
    private javax.swing.JLabel lblMemberStudentPersonalTrainer;
    private javax.swing.JLabel lblMemberStudentPhone;
    private javax.swing.JLabel lblMemberStudentTeam;
    private javax.swing.JLabel lblMembers;
    private javax.swing.JLabel lblPersonalTrainer;
    private javax.swing.JLabel lblTrainerEmployeeID;
    private javax.swing.JLabel lblTrainerMemberSearchList;
    private javax.swing.JList<String> listEmployees;
    private javax.swing.JList<String> listMembers;
    private javax.swing.JList<String> listTrainerMembers;
    private javax.swing.JPanel mainMenu;
    private javax.swing.JPanel manageEmployees;
    private javax.swing.JLabel manageEmployeesLogo;
    private javax.swing.JPanel manageMembers;
    private javax.swing.JPanel memberStaff;
    private javax.swing.JPanel memberStudent;
    private javax.swing.JRadioButton radioEmployeePersonalTrainer;
    private javax.swing.JRadioButton radioEmployeeRegular;
    private javax.swing.JRadioButton radioMemberStaff;
    private javax.swing.JRadioButton radioMemberStudent;
    private javax.swing.JScrollPane scrollEmployeeList;
    private javax.swing.JScrollPane scrollMemberList;
    private javax.swing.JTabbedPane tabbedPanel;
    private javax.swing.JPanel trainerList;
    private javax.swing.JLabel trainerListLogo;
    private javax.swing.JTextField txtEmployeeAddress;
    private javax.swing.JTextField txtEmployeeDOB;
    private javax.swing.JTextField txtEmployeeEditAddress;
    private javax.swing.JTextField txtEmployeeEditDOB;
    private javax.swing.JTextField txtEmployeeEditFirstName;
    private javax.swing.JTextField txtEmployeeEditLastName;
    private javax.swing.JTextField txtEmployeeEditPhone;
    private javax.swing.JTextField txtEmployeeEditSalary;
    private javax.swing.JTextField txtEmployeeFirstName;
    private javax.swing.JTextField txtEmployeeLastName;
    private javax.swing.JTextField txtEmployeePhone;
    private javax.swing.JTextField txtEmployeeSalary;
    private javax.swing.JTextField txtEmployeeSearchList;
    private javax.swing.JTextField txtMemberAddress;
    private javax.swing.JTextField txtMemberDOB;
    private javax.swing.JTextField txtMemberFirstName;
    private javax.swing.JTextField txtMemberLastName;
    private javax.swing.JTextField txtMemberPhone;
    private javax.swing.JTextField txtMemberSearchList;
    private javax.swing.JTextField txtMemberStaffAddress;
    private javax.swing.JTextField txtMemberStaffDOB;
    private javax.swing.JTextField txtMemberStaffFirstName;
    private javax.swing.JTextField txtMemberStaffLastName;
    private javax.swing.JTextField txtMemberStaffPhone;
    private javax.swing.JTextField txtMemberStudentAddress;
    private javax.swing.JTextField txtMemberStudentDOB;
    private javax.swing.JTextField txtMemberStudentFirstName;
    private javax.swing.JTextField txtMemberStudentLastName;
    private javax.swing.JTextField txtMemberStudentPhone;
    private javax.swing.JTextField txtTrainerMemberSearchList;
    // End of variables declaration//GEN-END:variables
}
