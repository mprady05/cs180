import java.io.*;
import java.util.*;


public class SocialMediaChatService {
    private static String FILE_PATH = "ChatHistories.txt";
    private static ArrayList<ChatHistory> chatHistories = new ArrayList<>();

    public SocialMediaChatService() {
        readChatHistories();
    }

    public static boolean readChatHistories() {
        chatHistories.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            ChatHistory currentChatHistory = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ChatID: ")) {
                    String chatId = line.substring("ChatID: ".length());
                    line = reader.readLine(); // For Participants
                    if (line.startsWith("Participants: ")) {
                        String[] participantIds = line.substring("Participants: ".length()).split(", ");
                        User participantOne = UserDatabase.getUserById(participantIds[0].trim());
                        User participantTwo = UserDatabase.getUserById(participantIds[1].trim());
                        if (participantOne == null || participantTwo == null) {
                            System.err.println("Error: Participant not found.");
                            continue; // Skip this chat history if participants are invalid
                        }
                        currentChatHistory = new ChatHistory(participantOne, participantTwo);
                    }
                } else if (line.startsWith("Messages:") && currentChatHistory != null) {
                    ArrayList<Message> currentMessages = new ArrayList<>();
                    while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                        Message message = Message.parseMessage(line);
                        if (message != null) {
                            currentMessages.add(message);
                        } else {
                            System.err.println("Error parsing message: " + line);
                        }
                    }
                    currentChatHistory.setMessages(currentMessages);
                    chatHistories.add(currentChatHistory);
                    currentChatHistory = null; // Reset for the next chat history
                }
            }
            saveChatHistories(chatHistories);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Message> loadMessages(ChatHistory chatHistory) {
        ArrayList<Message> loadedMessages = new ArrayList<>();
        String chatIdPattern = "ChatID: " + chatHistory.getChatId();
        boolean isCurrentChat = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(chatIdPattern)) {
                    isCurrentChat = true; // Start reading messages for the current chat history
                    continue;
                }
                if (isCurrentChat && line.startsWith("Messages:")) {
                    while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                        // Parse the message and add to loadedMessages
                        try {
                            Message message = Message.parseMessage(line);
                            loadedMessages.add(message);
                        } catch (Exception e) {
                            System.err.println("Error parsing message: " + e.getMessage());
                        }
                    }
                    break; // Stop reading after collecting all messages for this chat history
                }
                if (line.startsWith("ChatID: ")) {
                    isCurrentChat = false; // Encountered a new chat history
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading chat history file: " + e.getMessage());
        }

        return loadedMessages;
    }


    public static void saveChatHistories(ArrayList<ChatHistory> chatHistories) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (ChatHistory chatHistory : chatHistories) {
                writer.write("ChatID: " + chatHistory.getChatId());
                writer.newLine();
                writer.write("Participants: " + chatHistory.getParticipantOne().getUsername() + ", " + chatHistory.getParticipantTwo().getUserId());
                writer.newLine();
                writer.write("Messages:");
                writer.newLine();
                for (Message message : chatHistory.getMessages()) {
                    writer.write(message.toString());
                    writer.newLine();
                }
                writer.newLine(); // Separator between chat histories
            }
        } catch (IOException e) {
            System.err.println("Error saving chat histories: " + e.getMessage());
        }
    }

    public static boolean updateChatHistories(ChatHistory newChatHistory) {
        boolean found = false;
        for (int i = 0; i < chatHistories.size(); i++) {
            ChatHistory existingChatHistory = chatHistories.get(i);
            if (existingChatHistory.getChatId().equals(newChatHistory.getChatId())) {
                chatHistories.set(i, newChatHistory);
                found = true;
                break;
            }
        }
        if (!found) {
            chatHistories.add(newChatHistory);
        }
        return updateChatHistoriesFile();
    }

    private static boolean updateChatHistoriesFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (ChatHistory chatHistory : chatHistories) {
                writer.write("ChatID: " + chatHistory.getChatId());
                writer.newLine();
                writer.write("Participants: " + chatHistory.getParticipantOne().getUsername() + ", " + chatHistory.getParticipantTwo().getUserId());
                writer.newLine();
                writer.write("Messages:");
                writer.newLine();
                for (Message message : chatHistory.getMessages()) {
                    writer.write(message.toString());
                    writer.newLine();
                }
                writer.newLine(); // Separator between chat histories
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error updating chat histories file: " + e.getMessage());
            return false;
        }
    }

}
