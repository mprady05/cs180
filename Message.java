import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements MessageInterface {
    private int messageId = 0; // each user has a uniqueID
    private final User sender;
    private final User recipient;
    private final String content;
    private static int nextID = 1;

    public Message(User sender, User recipient, String content) {
        this.messageId += nextID;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public int getMessageId() {
        return messageId;
    }
    public User getSender() {
        return sender;
    }
    public User getRecipient() {
        return recipient;
    }
    public String getContent() {
        return content;
    }
    public String toString() {
        return String.format("msg%d,%s,%s,\"%s\"",
                messageId,
                sender.getUserId(),
                recipient.getUserId(),
                content);
    }
    public static Message parseMessage(String line) {
        String[] parts = line.split(",", 4);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid message format");
        }
        User sender = User.getUserById(parts[1].trim()); // todo: need to create a static getUserByID method in UserDatabase/Manager class
        User recipient = User.getUserById(parts[2].trim()); // todo: need to create a static getUserByID method in UserDatabase/Manager class
        String content = parts[3].substring(1, parts[3].length() - 1);
        return new Message(sender, recipient, content);
    }
}
