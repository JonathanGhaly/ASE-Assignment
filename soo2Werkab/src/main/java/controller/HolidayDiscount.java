package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HolidayDiscount implements Discount {
    ArrayList<String> holDays = new ArrayList<>();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");
    LocalDateTime now = LocalDateTime.now();

    public HolidayDiscount() {
        holDays.add("2/1");
        holDays.add("10/1");
        holDays.add("7/1");
    }

    @Override
    public double getDiscount(double price) {
        String temp = now.getDayOfMonth() + "/" + now.getMonthValue();
        if (holDays.contains(temp)) {
            return price - (price * 0.05);
        }
        return price;
    }
}
