import java.util.Scanner;
import java.lang.Thread;


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

class Cli{
    String session;

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
        new Auth();
    }
}
