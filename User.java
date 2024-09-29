import java.io.*;

public class User{
    private String username;
    private String password;
    private int type;
    private static String path = "./Data/user.csv";

    public User(String username, String password, int type){
        this.username = username;
        this.password = password;
        this.type = type;
        GlobalVariables.users.put(username, this);
    }

    public static void loadUsers(){
        try{
            BufferedReader br = new BufferedReader(new FileReader((path)));
            String line = br.readLine();

            while(line != null){
                String[] data = line.split(",");
                GlobalVariables.users.put(data[0], new User(data[0], data[1], Integer.parseInt(data[2])));
                line = br.readLine();
            }
            br.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void saveUsers(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
            for(User user: GlobalVariables.users.values()){
                bw.write(user.username + "," + user.password + "," + user.type);
                bw.newLine();
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static int authenticate(String username, String password){
        User user = GlobalVariables.users.get(username);

        if(user != null && user.password.equals(password)){
            return user.type;
        }

        return 0;
    }
}
