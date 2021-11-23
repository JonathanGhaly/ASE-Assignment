public class CarRequest {


    User user;
    Area source, destination;
    CarRequest(User user, Area source, Area destination) {
        this.user = user;
        this.source = source;
        this.destination = destination;

        source.notify();
    }

    void rate(int stars) {

    }

    String getRating() {
        return "";
    }

    void responce( Boolean response){

    }
}
