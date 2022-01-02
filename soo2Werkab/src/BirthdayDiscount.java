import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BirthdayDiscount implements Discount {
    User user;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate now = LocalDate.now();
    DataRetriever db = DataRetriever.getInstance();
    BirthdayDiscount(User user) {
        this.user = user;
    }

    @Override
    public double getDiscount(double price) {
        String bd = db.getBirthday(user),temp="",temp2 = "";
        temp = bd.substring(0,bd.length()-5);
        temp2=now.getDayOfMonth()+"/"+now.getMonthValue();
        if (temp.equals(temp2)) {
            System.out.println("Happy birthDay");
            return price - (price * 0.1);
        }
        return price;
    }
}
