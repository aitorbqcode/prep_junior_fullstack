public class NotificationSender {

    private final MesageSender mesageSender;

    public NotificationSender(MesageSender mesageSender){
        this.mesageSender = mesageSender;
    }

    public void notificar(String message){
        mesageSender.send(message);
    }
}
