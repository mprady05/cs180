public interface MessageInterface {
    int getMessageId();
    User getSender();
    User getRecipient();
    String getContent();
}
