import java.util.*;

class Authentication{
    Scanner sc;
    String session;
    Cli cli;
    

    Authentication(){
        Util.clear();
        sc = new Scanner(System.in);
        session = "guest";
        // System.out.println("Hi");
        GlobalVariables.loadData();
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

        if(!passcode.equals("Password")){
            System.out.println("Wrong passcode. Taking you back to main menu.");
            Util.slp();
            Util.clear();
            run();
            return;
        }

        new User(username, password, 1);

        User.saveUsers();
        
        System.out.println("\nYou have successfully registered, please sign now");

        Util.slp();

        Util.clear();

        signin();

    }
}
