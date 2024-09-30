import java.util.*;

// CLI

class Cli{
    private String session;
    Scanner sc = new Scanner(System.in);

    Cli(String session){
        this.session = session;
        run();
    }

    void run(){
        Util.clear();
        if(session.equals("teacher_auth")){
            teacherMenu();
        }
        else if(session.equals("stud_auth")){
            studentMenu();
            return;
        }
    }


    private void studentMenu(){
        boolean running = true;

        while (running){
            Util.clear();
            System.out.println("Student Menu:\n");
            System.out.println("1. List Classrooms");
            System.out.println("2. Submit Assignment");
            System.out.println("3. Logout");
            System.out.print("\nChoose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice){
                case 1:
                    Util.clear();
                    listClassrooms();
                    break;

                case 2:
                    Util.clear();
                    submitAssignment();
                    break;

                case 3:
                    GlobalVariables.saveData();
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void listClassrooms(){
        System.out.println("Classrooms:\n");
        for (String className : GlobalVariables.classrooms.keySet()){
            System.out.println(className);
        }
        sc.nextLine();
    }

    private void submitAssignment(){
        System.out.print("Enter classroom name: ");
        String className = sc.nextLine();
        if (!GlobalVariables.classrooms.containsKey(className)){
            System.out.println("Classroom not found.");
            Util.slp();
            return;
        }

        GlobalVariables.classrooms.get(className);
        System.out.print("Enter assignment ID to submit: ");
        int assignmentId = sc.nextInt();
        sc.nextLine();

        System.out.println("Submitting assignment " + assignmentId + "...");
        Util.slp();
        System.out.println("Assignment submitted for Classroom " + className);
    }



    private void teacherMenu(){
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while(running){
            Util.clear();
            System.out.println("Teacher Menu:\n");
            System.out.println("1. View Students");
            System.out.println("2. Add Student");
            System.out.println("3. Schedule Assignment");
            System.out.println("4. Add Classroom");
            System.out.println("5. Remove Classroom");
            System.out.println("6. Logout");
            System.out.print("\nChoose an option: ");
            String choice = sc.nextLine();

            switch (choice){
                case "1":
                    viewStudents();
                    break;
                case "2":
                    addStudent();
                    break;
                case "3":
                    scheduleAssignment();
                    break;
                case "4":
                    addClassroom();
                    break;
                case "5":
                    removeClassroom();
                    break;
                case "6":
                    GlobalVariables.saveData();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        sc.close();
    }

    private void removeClassroom(){
        System.out.print("Enter classroom name to remove: ");
        String name = sc.nextLine();

        if (GlobalVariables.classrooms.remove(name) != null){
            System.out.println("Classroom \"" + name + "\" removed successfully.");
        } else {
            System.out.println("Classroom not found.");
        }
        Util.slp();
    }

    private void viewStudents(){
        System.out.println("Fetching student details.");
        Util.slp();
        Util.clear();

        System.out.println("Students List:\n");

        for(Student student: GlobalVariables.students.values()){
            System.out.println(student);
        }
        
        sc.nextLine();
    }

    private void addStudent(){
        Util.clear();

        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        new Student(name);

        System.out.println("Student added successfully.");

        Util.slp();
    }

    private void scheduleAssignment(){
        System.out.print("Enter classroom name: ");
        String className = sc.nextLine();
        System.out.print("Enter assignment details: ");
        String details = sc.nextLine();
        
        if(GlobalVariables.classrooms.containsKey(className)){
            Classroom classroom = GlobalVariables.classrooms.get(className);
            classroom.scheduleAssignment(details);
        }
        else{
            System.out.println("Classroom not found.");
        }
        Util.slp();
    }

    private void addClassroom(){
        System.out.print("Enter classroom name: ");
        String name = sc.nextLine();
        
        if(GlobalVariables.classrooms.containsKey(name)){
            System.out.println("Classroom already exists.");
            return;
        }

        new Classroom(name);
        System.out.println("Classroom \"" + name + "\" created successfully.");
    }
}


public class Main {
    public static void main(String[] args){
        new Authentication();    
    }
}
