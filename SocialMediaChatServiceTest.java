import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SocialMediaChatServiceTest {
    private final static String TEST_FILE_PATH = "testChatHistories.txt";



    public void setUp() throws Exception {
        SocialMediaChatService.FILE_PATH = TEST_FILE_PATH;
        new PrintWriter(TEST_FILE_PATH).close(); // Ensure the file is empty
    }

    
    @After
    public void tearDown() throws Exception {
        new File(TEST_FILE_PATH).delete();
    }

    @Test
    public void testReadAndSaveChatHistories() throws Exception {
        ArrayList<ChatHistory> chatHistories = new ArrayList<>();
        chatHistories.add(createDummyChatHistory());
        SocialMediaChatService.saveChatHistories(chatHistories);

        assertTrue(SocialMediaChatService.readChatHistories());

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            assertTrue(reader.readLine().contains("ChatID: "));
        }
    }


    public void testUpdateChatHistories() throws Exception {
        ChatHistory newChatHistory = createDummyChatHistory();
        assertTrue(SocialMediaChatService.updateChatHistories(newChatHistory));

        assertTrue(SocialMediaChatService.readChatHistories());
    }


    public void testLoadMessages() throws Exception {
        ChatHistory chatHistory = createDummyChatHistoryWithMessages();
        ArrayList<ChatHistory> chatHistories = new ArrayList<>();
        chatHistories.add(chatHistory);
        SocialMediaChatService.saveChatHistories(chatHistories);

        ArrayList<Message> loadedMessages = SocialMediaChatService.loadMessages(chatHistory);

        assertNotNull(loadedMessages);
        assertFalse(loadedMessages.isEmpty());
        assertEquals(2, loadedMessages.size()); // Assuming 2 messages were added


        tearDown(); // Cleanup
    }

    private ChatHistory createDummyChatHistory() {

        User participantOne = new User("User1", "User1ID");
        User participantTwo = new User("User2", "User2ID");
        ChatHistory chatHistory = new ChatHistory(participantOne, participantTwo);
        return chatHistory;

    }

    private ChatHistory createDummyChatHistoryWithMessages() {

        User participantOne = new User("User1", "User1ID");
        User participantTwo = new User("User2", "User2ID");
        ChatHistory chatHistory = new ChatHistory(participantOne, participantTwo);
        chatHistory.addMessage(new Message(participantOne, "Hello, how are you?"));
        chatHistory.addMessage(new Message(participantTwo, "I'm fine, thank you!"));
        return chatHistory;

    }
}
