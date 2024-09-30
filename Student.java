import java.io.*;

public class Student{
    private String name;
    private int id;
    private static int count = loadCount();
    private static String path = "./Data/students.csv";

    Student(String name){
        this.name = name;
        this.id = count;
        count += 1;
        // System.out.println(this.name + " " + this.id);
        GlobalVariables.students.put(id, this);
        new User(name, name + "@rakesh", 2);
        updateCount();
    }

    Student(String name, int id){
        this.name = name;
        this.id = id;
    }

    private static int loadCount(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("./Data/variables.csv"));
            String line = br.readLine();
            if (line != null) {
                br.close();
                return Integer.parseInt(line) + 1;
            }
            br.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 1;
    }

    private static void updateCount(){
        Classroom.updateClassroomCount();
    }

    public static int getCount(){
        return count - 1;
    }

    public static void loadStudents(){
        try{
            BufferedReader br=new BufferedReader(new FileReader(path));
            String line;
            while((line=br.readLine())!=null){
                String[] data=line.split(",");
                GlobalVariables.students.put(Integer.parseInt(data[0]),new Student(data[1],Integer.parseInt(data[0])));
            }

            br.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public static void saveStudents(){
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(path,false));
            for(Student student:GlobalVariables.students.values()){
                bw.write(student.id+","+student.name);
                bw.newLine();
            }
            bw.close();
        } catch(Exception e){
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
