public class Logger{

    static Logger logger;
    DataRetriever db = DataRetriever.getInstance();

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    private Logger(){
    }

    void log(Event event){
        db.logEvent(event.type, event.rideID, event.time);
    }
}
