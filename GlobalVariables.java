import java.util.*;

public class GlobalVariables{
    static Map<String, User> users = new HashMap<>();
    static Map<Integer, Student> students = new HashMap<>();
    static Map<String, Classroom> classrooms = new HashMap<>();

    public static void loadData(){
        // System.out.println("Hi1");
        User.loadUsers();
        // System.out.println("Hi2");
        Student.loadStudents();
        // System.out.println("Hi3");
        Classroom.loadClassrooms();
        // System.out.println("Hi4");
    }

    public static void saveData(){
        // System.out.println("Hii1");
        User.saveUsers();
        // System.out.println("Hii2");
        Student.saveStudents();
        // System.out.println("Hii3");
        Classroom.saveClassrooms();
        // System.out.println("Hii4");
    }

}