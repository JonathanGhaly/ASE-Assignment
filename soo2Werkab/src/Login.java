public class Login {
    DataRetriever d=DataRetriever.getInstance();
    String username;
    String password;
    Boolean isDriver = false;
    CarDriver c;
    User u;
    Login(String username,String password){
        this.username=username;
        this.password=password;
        if(d.Login(this)) {
            System.out.println("Logged in successfully");
            if(isDriver) c = getCarDriver(username);
            else u = getUser(username);
        }
    }
    CarDriver getCarDriver(String username){
        return d.getCarDriver(username);
    }
    User getUser(String username){return  d.getUser(username);}
    User getUser(){
        return this.u;
    }
    CarDriver getCarDriver(){
        return this.c;
    }
}
