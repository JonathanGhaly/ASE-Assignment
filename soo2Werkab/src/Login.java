public class Login {
    DataRetriever d=DataRetriever.getInstance();
    String username;
    String password;
    Boolean isDriver = false;
    Login(String username,String password){
        this.username=username;
        this.password=password;
        if(d.Login(this))
            System.out.println("Logged in successfully");
    }

}
