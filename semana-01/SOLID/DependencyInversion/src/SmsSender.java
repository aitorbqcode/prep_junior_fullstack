public class SmsSender implements MesageSender{

    @Override
    public String send(String message) {
        return "Sms message: " + message;
    }
}
