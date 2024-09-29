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
            return;
        }
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
            System.out.println("5. Logout");
            System.out.print("\nChoose an option: ");
            int choice = sc.nextInt();

            switch(choice){
                case 1:
                    viewStudents();
                    break;

                case 2:
                    addStudent();
                    break;

                case 3:
                    scheduleAssignment();
                    break;

                case 4:
                    addClassroom(sc);
                    break;

                case 5:
                    GlobalVariables.saveData();
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
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

    private void addClassroom(Scanner sc){
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
    public static void main(String[] args) {
        new Authentication();    
    }
}
