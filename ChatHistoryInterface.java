import java.util.ArrayList;

public interface ChatHistoryInterface {
    boolean addMessage(Message message);
    boolean deleteMessage(int messageId);
    String getChatId();
    User getParticipantOne();
    User getParticipantTwo();
    ArrayList<Message> getMessages();
    void setMessages(ArrayList<Message> messages);
}
