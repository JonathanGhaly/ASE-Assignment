import java.util.ArrayList;

public interface Subject {
    String areaName = "";
    ArrayList<Driver> drivers = new ArrayList<Driver>();

    void notifyDrivers();
    void Subscribe();
    void UnSubscribe();

}
