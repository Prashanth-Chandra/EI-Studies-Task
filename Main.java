import java.util.*;
import java.lang.Thread;
import java.io.*;

// map<student id, student object>
// map<classroom name, classroom object>

// TODO: After the start of the cli, the program must retieve the data from the databases and store it in RAM

class globalVariables{
    static ArrayList<
}

class User{
    private String username;
    private String password;
    private int type;
    private static String path = "./Data/user.csv";

    public User(String username, String password, int type){
        this.username = username;
        this.password = password;
        this.type = type;
        add();
    }

    private void add(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(username + "," + password + "," + String.valueOf(type));
            bw.newLine();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static int authenticate(String username, String password){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            while(line != null){
                String[] data = line.split(",");
                if(data[0].equals(username) && data[1].equals(password)){
                    return Integer.parseInt(data[2]);
                }
                line = br.readLine();
            }
            
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }
}

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
        Util.clear();
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

        int n = User.authenticate(username, password); // 1 => Teacher, 2 => Student

        // if(username.equals("teach") && password.equals("teach")){
        //     n = 1;
        // }
        // if(username.equals("stud") && password.equals("stud")){
        //     n = 2;
        // }

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
            System.out.println("Taking you back to main menu.");
            Util.slp();
            Util.clear();
            run();
        }
    }

    private void register(){
        System.out.println("\nRegistration Page");
        System.out.println("\nPlease note that this is only for teachers.\n");
        
        System.out.print("Please enter a new username\n>");
        String username = sc.nextLine();

        System.out.print("\nEnter the teacher passcode\n>");
        String passcode = sc.nextLine();

        System.out.print("\nPlease enter a new password\n>");
        String password = sc.nextLine();

        //TODO: update the txt ot json file to authenticate the user
        if(!passcode.equals("BoyaRakesh")){
            System.out.println("Wrong passcode. Taking you back to main menu.");
            Util.slp();
            Util.clear();
            run();
            return;
        }

        User user = new User(username, password, 1);
        
        System.out.println("\nYou have successfully registered, please sign now");

        Util.slp();

        Util.clear();

        signin();

    }
}

public class Main {
    public static void main(String[] args) {
        new Auth();    
    }
}
