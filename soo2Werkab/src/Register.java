public class Register {
    Account acc;
    boolean isDriver = false;
    DataRetriever data = new DataRetriever();
    public Register(Account account,boolean isDriver){
        acc=account;
        this.isDriver=isDriver;
        if(!isDriver){
            data.UserRegister(account);
        }else{

        }
    }

}
