import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SocialMediaDatabase {
    private Map<String, SocialMediaChatService.User> users = new HashMap<>();
    private Map<String, SocialMediaChatService.ChatHistory> chatHistories = new HashMap<>();
    private List<SocialMediaChatService.Conversation> conversations = new ArrayList<>();
    private String databaseOutput = "chat_histories/conversations.txt";

    public SocialMediaDatabase() {
        loadConversations();
    }

    private void loadConversations() {
        try (BufferedReader reader = new BufferedReader(new FileReader(databaseOutput))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assume each line contains messageId,senderId,receiverId,content,timestamp
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    continue;
                }

                String messageId = parts[0];
                String senderId = parts[1];
                String receiverId = parts[2];
                String content = parts[3];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date timestamp = sdf.parse(parts[4]);

                SocialMediaChatService.User sender = users.computeIfAbsent(senderId, id -> new SocialMediaChatService.User(id, "Unknown Sender"));
                SocialMediaChatService.User receiver = users.computeIfAbsent(receiverId, id -> new SocialMediaChatService.User(id, "Unknown Receiver"));
                SocialMediaChatService.Message message = new SocialMediaChatService.Message(messageId, senderId, receiverId, content, timestamp);

                SocialMediaChatService.Conversation conversation = findOrCreateConversation(sender, receiver);
                conversation.addMessage(message);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error loading conversations: " + e.getMessage());
        }
    }

    private void saveConversations() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(databaseOutput))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (SocialMediaChatService.Conversation conversation : conversations) {
                for (SocialMediaChatService.Message msg : conversation.messages) {
                    String timestampStr = sdf.format(msg.timestamp);
                    writer.printf("%s,%s,%s,%s,%s\n", msg.messageId, msg.senderId, msg.receiverId, msg.content, timestampStr);
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving conversations: " + e.getMessage());
        }
    }

    private SocialMediaChatService.Conversation findOrCreateConversation(SocialMediaChatService.User sender, SocialMediaChatService.User receiver) {
        return new SocialMediaChatService.Conversation(sender, receiver);
    }
    private void saveMessages() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (PrintWriter pw = new PrintWriter(new File(messagesFile))) {
            for (ChatHistory history : chatHistories.values()) {
                for (Message message : history.getMessages()) {
                    String timestamp = dateFormat.format(message.getTimestamp());
                    pw.println(message.getMessageId() + "," + message.getSenderId() + "," +
                            message.getReceiverId() + "," + message.getContent() + "," + timestamp);
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving messages: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new File(usersFile))) {
            for (User user : users.values()) {
                pw.println(user.getUserId() + "," + user.getUsername());
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        SocialMediaDatabase db = new SocialMediaDatabase();

    }


}

