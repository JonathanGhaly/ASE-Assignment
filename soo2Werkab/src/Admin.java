public class Admin {


    DataRetriever dataRetriever = DataRetriever.getInstance();
    Account account;

    public Admin(Account account) {this.account = account;
    }

    public void suspend(String username, int type) {
        dataRetriever.changeStateDB(username, type);
    }

    public void verifyDriver(String username) {
        dataRetriever.verifyDriverDB(dataRetriever.getID(username));
    }

    public void manageUser(User user) {

    }

}
