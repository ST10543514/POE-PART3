/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mphah
 */
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class messagesTest {

    ArrayList<String> storedMessages;
    ArrayList<String> storedRecipients;
    ArrayList<String> storedSenders;
    ArrayList<String> messageHashes;
    ArrayList<String> messageIds;

    @BeforeEach
    void setUp() {
        storedMessages = new ArrayList<>();
        storedRecipients = new ArrayList<>();
        storedSenders = new ArrayList<>();
        messageHashes = new ArrayList<>();
        messageIds = new ArrayList<>();

        // Test data from brief - Part 3
        addStored("+27838884567", "Where are you? You are late! I have asked you to be on time.", "00:1:WHEREYOU", "1000000001");
        addStored("+27838884567", "Ok, I am leaving without you.", "00:2:OKYOU", "1000000002");
    }

    void addStored(String recipient, String message, String hash, String id) {
        storedMessages.add(message);
        storedRecipients.add(recipient);
        storedSenders.add("Developer");
        messageHashes.add(hash);
        messageIds.add(id);
    }

    String longestStoredMessage() {
        String longest = "";
        for (String m : storedMessages) {
            if (m.length() > longest.length()) longest = m;
        }
        return longest;
    }

    @Test
    void testStoredMessagesPopulated() {
        assertEquals(2, storedMessages.size());
        assertTrue(storedMessages.contains("Where are you? You are late! I have asked you to be on time."));
    }

    @Test
    void testLongestStoredMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", longestStoredMessage());
    }

    @Test
    void testSearchForMessageId() {
        int index = messageIds.indexOf("1000000002");
        assertEquals("+27838884567", storedRecipients.get(index));
        assertEquals("Ok, I am leaving without you.", storedMessages.get(index));
    }

    @Test
    void testSearchAllMessagesForRecipient() {
        int count = 0;
        for (String r : storedRecipients) {
            if (r.equals("+27838884567")) count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testDeleteMessageUsingHash() {
        int index = messageHashes.indexOf("00:1:WHEREYOU");
        String deleted = storedMessages.get(index);
        storedMessages.remove(index);
        storedRecipients.remove(index);
        storedSenders.remove(index);
        messageHashes.remove(index);
        messageIds.remove(index);

        assertEquals("Where are you? You are late! I have asked you to be on time.", deleted);
        assertEquals(1, storedMessages.size());
    }

    @Test
    void testReportListsHashRecipientAndMessage() {
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < storedMessages.size(); i++) {
            report.append("Hash: ").append(messageHashes.get(i));
            report.append(" Recipient: ").append(storedRecipients.get(i));
            report.append(" Message: ").append(storedMessages.get(i));
        }
        assertTrue(report.toString().contains("+27838884567"));
        assertTrue(report.toString().contains("00:1:WHEREYOU"));
    }

    @Test
    void testHashesAndIdsPopulated() {
        assertEquals(2, messageHashes.size());
        assertEquals(2, messageIds.size());
    }
}