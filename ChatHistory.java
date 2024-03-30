import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatHistory implements ChatHistoryInterface {
    private final String chatId;
    private final User participantOne;
    private final User participantTwo;
    private ArrayList<Message> messages = SocialMediaChatService.loadMessages(this);

    public ChatHistory(User participantOne, User participantTwo) {
        this.participantOne = participantOne;
        this.participantTwo = participantTwo;
        this.chatId = participantOne.getUsername() + "-" + participantTwo.getUsername();
        SocialMediaChatService.updateChatHistories(this);

    }

    public boolean addMessage(Message message) {
        boolean check = SocialMediaChatService.updateChatHistories(this);
        if (check) {
            this.messages.add(message);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteMessage(int messageId) {
        Message toRemove = null;
        boolean check = SocialMediaChatService.updateChatHistories(this);
        if (check) {
            for (Message message : this.messages) {
                if (message.getMessageId() == messageId) {
                    toRemove = message;
                    break;
                }
            }
            if (toRemove != null) {
                this.messages.remove(toRemove);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getChatId() {
        return chatId;
    }
    public User getParticipantOne() {
        return participantOne;
    }
    public User getParticipantTwo() {
        return participantTwo;
    }
    public ArrayList<Message> getMessages() {
        return new ArrayList<>(messages); // returning a copy
    }
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        SocialMediaChatService.updateChatHistories(this);
    }

}
