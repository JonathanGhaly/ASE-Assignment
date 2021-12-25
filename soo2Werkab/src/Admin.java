public class Admin {


    DataRetriever dataRetriever = DataRetriever.getInstance();
    Account account;

    public Admin(String username, String password, String email, String mobileNumber) {
        account = new Account(username, password, email, mobileNumber);
    }

    public void suspend(Account account, int type) {
        dataRetriever.changeStateDB(account.getUsername(), type);
    }

    public void verifyDriver(Driver driver) {
        dataRetriever.verifyDriverDB(dataRetriever.getID(driver.account.getUsername()));
    }

    public void manageUser(User user) {

    }

}
