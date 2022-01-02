import java.util.ArrayList;
import java.util.Date;

public class User {
    Account account;
    DataRetriever db = DataRetriever.getInstance();

    User(Account account) {
        this.account = account;
    }

    public User() {

    }

    public String getUserStatus() {
        return db.getUserStatus(this);
    }
}
