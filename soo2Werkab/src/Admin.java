public class Admin {


    DataRetriever dataRetriever = DataRetriever.getInstance();

    public Admin() {

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
