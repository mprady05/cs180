import java.io.*;
import java.util.*;

public class SocialMediaChatService {
    private static final String CHAT_HISTORY_DIRECTORY = "chat_histories/";
    private static final String conversationsFile = "conversations.txt";
    private Map<String, ChatHistory> chatHistories = new HashMap<>();
    private List<Conversation> conversations = new ArrayList<>();

    static class User {
        String userId;
        String username;

        public User(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }
    }

    static class Message {
        String messageId;
        String senderId;
        String receiverId;
        String content;
        Date timestamp;

        public Message(String messageId, String senderId, String receiverId, String content, Date timestamp) {
            this.messageId = messageId;
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.content = content;
            this.timestamp = timestamp;
        }
    }

    static class Conversation {
        private ArrayList<Message> messages = new ArrayList<>();
        private User userOne;
        private User userTwo;

        public Conversation(User userOne, User userTwo) {
            this.userOne = userOne;
            this.userTwo = userTwo;
        }

        public void addMessage(Message message) {
            messages.add(message);
        }
    }

    static class ChatHistory {
        private String chatId;
        private Set<String> participantIds;
        private List<Message> messages = new ArrayList<>();
        private Date lastUpdated;

        public ChatHistory(String chatId, Set<String> participantIds) {
            this.chatId = chatId;
            this.participantIds = participantIds;
        }

        public void addMessage(Message message) {
            messages.add(message);
            this.lastUpdated = new Date();
        }
    }

    public SocialMediaChatService() {
        loadConversations();
    }

    public void addMessageToConversation(User sender, User receiver, Message message) {
        Conversation conversation = findOrCreateConversation(sender, receiver);
        conversation.addMessage(message);

        ChatHistory chatHistory = findOrCreateChatHistory(sender.userId, receiver.userId);
        chatHistory.addMessage(message);

        saveConversations();
    }

    private Conversation findOrCreateConversation(User sender, User receiver) {
        for (Conversation conv : conversations) {
            if ((conv.userOne == sender && conv.userTwo == receiver) ||
                    (conv.userOne == receiver && conv.userTwo == sender)) {
                return conv;
            }
        }
        Conversation newConv = new Conversation(sender, receiver);
        conversations.add(newConv);
        return newConv;
    }

    private ChatHistory findOrCreateChatHistory(String senderId, String receiverId) {
        String chatId = senderId + "-" + receiverId;
        return chatHistories.computeIfAbsent(chatId, k -> new ChatHistory(chatId, new HashSet<>(Arrays.asList(senderId, receiverId))));
    }

    private void loadConversations() {
        Map<String, User> userMap = new HashMap<>(); // Cache users by their IDs to avoid duplicates

        try (BufferedReader reader = new BufferedReader(new FileReader(CHAT_HISTORY_DIRECTORY + conversationsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue; // Skip if the line does not have enough parts

                String senderId = parts[0];
                String receiverId = parts[1];
                String messageId = parts[2];
                String content = parts[3];
                long timestampLong = Long.parseLong(parts[4]);
                Date timestamp = new Date(timestampLong);

                User sender = userMap.computeIfAbsent(senderId, id -> new User(id, "Username_" + id)); // Placeholder usernames
                User receiver = userMap.computeIfAbsent(receiverId, id -> new User(id, "Username_" + id));

                Message message = new Message(messageId, senderId, receiverId, content, timestamp);


                Conversation conversation = findOrCreateConversation(sender, receiver);
                conversation.addMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error loading conversations: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing timestamp in conversations file: " + e.getMessage());
        }
    }

    private void saveConversations() {
        try (PrintWriter writer = new PrintWriter(new File(CHAT_HISTORY_DIRECTORY + conversationsFile))) {
            for (Conversation conv : conversations) {

                for (Message msg : conv.messages) {
                    writer.println(msg.senderId + "," + msg.receiverId + "," + msg.messageId + "," + msg.content);
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving conversations: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
    }
}
