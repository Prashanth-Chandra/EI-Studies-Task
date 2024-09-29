import java.io.*;

public class Assignment{
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
            bw.close();
        } 
        catch (IOException e){
            System.out.println(e);
        }
    }
}