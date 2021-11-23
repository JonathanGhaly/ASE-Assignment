public class Admin {


    DataRetriever dataRetriever = DataRetriever.getInstance();

    public Admin() {

    }

    public void suspend(Account account) {
        dataRetriever.changeStateDB(account.getUsername(), 1);
    }

    public void unsuspend(Account account) {
        dataRetriever.changeStateDB(account.getUsername(), 0);
    }

    public void verifyDriver(Driver driver) {
        dataRetriever.verifyDriverDB(dataRetriever.getID(driver.account.getUsername()));
    }

    public void manageUser(User user) {

    }

}
