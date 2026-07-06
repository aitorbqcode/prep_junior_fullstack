public class EmailSender implements MesageSender{

    @Override
    public String send(String message) {
        return "Email message: " + message;
    }
}
