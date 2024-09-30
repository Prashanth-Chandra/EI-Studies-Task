import java.util.*;
import java.io.*;

public class Classroom{
    private static int classroom_count = loadClassroomCount();
    private String name;
    private List<Integer> studIds;
    private List<Assignment> assignments = new ArrayList<>();
    private static String path = "./Data/classroom.csv";

    Classroom(String name){
        this.name = name;
        classroom_count += 1;
        studIds = new ArrayList<>();
        add();
        updateClassroomCount();
    }

    Classroom(String name, int count){
        this.name = name;
        classroom_count = count;
        studIds = new ArrayList<>();
    }

    private static int loadClassroomCount(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("./Data/variables.csv"));
            br.readLine();
            String line = br.readLine();
            if (line != null) {
                br.close();
                return Integer.parseInt(line); 
            }
            br.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public static void updateClassroomCount(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("./Data/variables.csv", false));
            bw.write(String.valueOf(Student.getCount()));
            bw.newLine();
            bw.write(String.valueOf(classroom_count));
            bw.newLine();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void loadClassrooms(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while(line != null){
                String[] data = line.split(",");
                Classroom classroom = new Classroom(data[0], Integer.parseInt(data[1]));
                Classroom.classroom_count = Integer.parseInt(data[1]);
                GlobalVariables.classrooms.put(data[0], classroom);
                line = br.readLine();
            }
            br.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void saveClassrooms(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
            for(Classroom classroom : GlobalVariables.classrooms.values()){
                bw.write(classroom.name + "," + Classroom.classroom_count);
                bw.newLine();
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void add(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(name + "," + classroom_count + "");
            bw.newLine();
            bw.close();

            GlobalVariables.classrooms.put(name, this);
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

    public boolean submitAssignment(int assignmentId) {
        for (Assignment as : assignments) {
            if (as.id == assignmentId) {
                System.out.println("Assignment " + assignmentId + " submitted for Classroom " + name);
                return true;
            }
        }
        System.out.println("Assignment not found.");
        return false;
    }

    @Override
    public String toString(){
        return getDetails();
    }

}