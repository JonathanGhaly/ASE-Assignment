public class Event {
    String eventInfo,eventSender,eventReceiver;

    Event(String eventName,Account sender,Account receiver){
    this.eventInfo=eventName;
    this.eventSender=sender.getUsername();
    this.eventReceiver=receiver.getUsername();
    }
}
