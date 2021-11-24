public class Login {
    DataRetriever d=DataRetriever.getInstance();
    String username;
    String password;
    Boolean isDriver = false;
    CarDriver c;
    Login(String username,String password){
        this.username=username;
        this.password=password;
        if(d.Login(this)) {
            System.out.println("Logged in successfully");
            if(isDriver) c = getCarDriver(username);
        }
    }
    CarDriver getCarDriver(String username){
        return d.getCarDriver(username);
    }
}
