import java.util.*;
import java.lang.Thread;
import java.io.*;

// map<student id, student object>
// map<classroom name, classroom object>

// TODO: After the start of the cli, the program must retieve the data from the databases and store it in RAM

class Util {
    public static void clear(){
        try{
            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else{
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch(Exception e){
            System.out.println("Error clearing terminal");
        }
    }

    public static void slp(){
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

class Student{
    private String name;
    private int id;
    private static int count = 1;
    private String path = "./Data/students.csv";

    Student(String name){
        this.name = name;
        this.id = count;
        count += 1;
        System.out.println(this.name + " " + this.id);
        add();
    }

    private void add(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(id + "," + name);
            bw.newLine();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return "Student Name : " + this.name + ", ID : " + this.id;
    }
}

class Classroom{
    private static int classroom_count = 0;
    private String name;
    private List<Integer> studIds;
    private List<Assignment> assignments = new ArrayList<>();
    private String path = "./Data/classroom.csv";

    Classroom(String name){
        this.name = name;
        classroom_count += 1;
        studIds = new ArrayList<>();
        add();
    }

    private void add(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(name + "," + classroom_count + "");
            bw.newLine();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    
    public void addStudent(Student stud){
        System.out.println(stud);
        studIds.add(stud.getId());
        updateDb();
    }

    private void updateDb(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
            bw.write(getDetails());
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private String getDetails(){
        StringBuilder details = new StringBuilder();
        details.append(name).append(",").append(classroom_count).append(",").append(getStudDetails()).append("\n");
        return details.toString();
    }

    private String getStudDetails(){
        StringBuilder details = new StringBuilder();

        if(studIds.isEmpty()){
            return "";
        }

        for(Integer id: studIds){
            details.append(id).append(",");
        }
        
        return details.substring(0, details.length() - 1);
    }

    public void scheduleAssignment(String detials){
        Assignment as = new Assignment(name, detials);
        assignments.add(as);

        System.out.println("Assignment created for the class " + name + " with details : " + detials);
    }

    @Override
    public String toString(){
        return getDetails();
    }

}

class Assignment{
    String className;
    String details;
    int id;
    static int count = 1;

    Assignment(String className, String details){
        this.className = className;
        this.details = details;
        this.id = count++;
        add();
    }

    private void add(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("./Data/assignments.csv", true));
            bw.write(id + "," + className + "," + details);
            bw.newLine();
        } 
        catch (IOException e){
            System.out.println(e);
        }
    }
}








// CLI

class Cli{
    private String session;

    Cli(String session){
        this.session = session;
        run();
    }
    void run() {
        Util.clear();

        switch(session){

        }
    }

}


class Auth{
    Scanner sc;
    String session;
    Cli cli;

    Auth(){
        sc = new Scanner(System.in);
        session = "guest";
        run();
    }

    private void modify_session(String session){
        this.session = session;
    }

    private void run_cli(){
        cli = new Cli(session);
    }

    void run(){
        System.out.print("Hello user, do you want to sign/register or continue as guest (Valid responses: 'guest', 'signin', 'register')\n>");
        String response = sc.nextLine();
        
        switch(response.toLowerCase()){
            case "guest":
                run_cli();
                break;

            case "signin":
                Util.clear();
                signin();
                break;

            case "register":
                Util.clear();
                register();
                break;

            default:
                Util.clear();
                System.out.println("Invalid response. Try again.");
                run();
                break;
        }
    }

    private void signin(){
        System.out.println("Signin Page\n");

        System.out.print("Please enter your username\n>");
        String username = sc.nextLine();
        
        System.out.print("\nPlease enter your password\n>");
        String password = sc.nextLine();

        //TODO: use some txt or json file to authenticate the user

        int n = 0; // 1 => Teacher, 2 => Student

        if(username.equals("teach") && password.equals("teach")){
            n = 1;
        }
        if(username.equals("stud") && password.equals("stud")){
            n = 2;
        }

        if(n == 1){
            modify_session("teacher_auth");
            System.out.println("Successfully authenticated, Welcome teacher");

            Util.slp();

            run_cli();
        }
        else if(n == 2){
            modify_session("stud_auth");
            System.out.println("Successfully authenticated, Welcome student");
            
            Util.slp();

            run_cli();
        }
        else{
            Util.clear();
            System.out.println("Invalid credentials");
            System.out.println("Do you want to continue as guest / register or try again. (Valid responses '1' for continue as guest, '2' for register, any other for try again)");
            int choice = sc.nextInt();

            switch(choice){
                case 1:
                    System.out.println("Continuing as guest.");

                    Util.slp();
                    Util.clear();        
                    run_cli();

                    break;

                case 2:
                    Util.clear();
                    register();
                    break;

                default:
                    Util.clear();
                    sc.nextLine();
                    signin();
                    break;
            }
        }
    }

    private void register(){
        System.out.println("\nRegistration Page");
        System.out.println("\nPlease note that this is only for teachers.\n");
        
        System.out.print("Please enter a new username\n>");
        String newUsername = sc.nextLine();

        System.out.print("\nEnter the teacher passcode\n>");
        String passcode = sc.nextLine();

        System.out.print("\nPlease enter a new password\n>");
        String newPassword = sc.nextLine();

        //TODO: update the txt ot json file to authenticate the user
        
        System.out.println("\nYou have successfully registered, please sign now");

        Util.slp();

        Util.clear();

        signin();

    }
}

public class Main {
    public static void main(String[] args) {
        // 1. Test adding classrooms
        Classroom mathClass = new Classroom("Math 101");
        Classroom scienceClass = new Classroom("Science 101");

        // 2. Test adding students to classrooms
        Student student1 = new Student("John Doe");
        Student student2 = new Student("Jane Smith");
        Student student3 = new Student("Alice Johnson");

        mathClass.addStudent(student1); // Add John Doe to Math 101
        mathClass.addStudent(student2); // Add Jane Smith to Math 101
        scienceClass.addStudent(student3); // Add Alice Johnson to Science 101

        // Print classroom details
        System.out.println(mathClass);
        System.out.println(scienceClass);

        // 3. Test scheduling assignments
        mathClass.scheduleAssignment("Homework 1: Solve problems 1-10");
        scienceClass.scheduleAssignment("Lab Report: Chemistry Experiment 1");

    }
}
