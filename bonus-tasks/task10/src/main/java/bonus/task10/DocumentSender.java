package bonus.task10;

public interface DocumentSender {
    SendResult send(Document document, String recipientId);
}
