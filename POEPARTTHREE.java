/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepartthree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

 import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
/**
 *
 * @author mphah
 */
public class POEPARTTHREE {

    }
    public static void main(String[] args) throws JSONException {
      
      System.out.println("Welcome to the Register Page");
        Scanner sc = new Scanner(System.in);
      
        System.out.println("Please Enter Your Username : ");
        String username = sc.nextLine();
        System.out.println("Please Enter Your Password");
        String Password = sc.nextLine();
        System.out.println("Enter a valid SA Number(+27)");
        String Phone = sc.nextLine();
        
        if(username.length() <= 5 && 
                username.contains("_")){
            System.out.println("True Message");
        }else{
            System.out.println("False Message");
        }
        
        
        
        int numMessagesSend = 0;
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        JSONArray messagesArray = new JSONArray();

        System.out.println("Welcome to Quick Chat");
        System.out.println("How many messages do you want to enter");
        int NumMessages = input.nextInt();
        input.nextLine(); // clear the leftover newline

        for (int x = 0; x < NumMessages; x++) {

            System.out.println("=====Menu=====");
            System.out.println("Option 1) Send Message");
            System.out.println("Option 2) Disregard Message");
            System.out.println("Option 3) Store Message To Send Later");
            int Choice = input.nextInt();
            input.nextLine();

            if (Choice != 1 && Choice != 2 && Choice != 3) {
                System.out.println("Invalid input, please enter 1, 2, or 3");
                x--; // repeat this message
                continue;
            }

            if (Choice == 2) {
                System.out.println("Message disregarded");
                continue;
            }

           
            long maximum = 9999999999L;
            long minimum = 1000000000L;
            long MessageID = minimum + (long) (random.nextDouble() * (maximum - minimum));

            System.out.println("Enter The Recipient Number");
            String RNumber = input.nextLine();

            System.out.println("Enter your message");
            String MessageText = input.nextLine();

            if (MessageText.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters");
                x--; 
                continue;
            }

            if (Choice == 1) {
                numMessagesSend++;
            }

            
            String firstTwoDigits = String.valueOf(MessageID).substring(0, 2);
            String[] Words = MessageText.split(" ");
            String firstword = Words[0];
            String lastword = Words[Words.length - 1];
            String Hash = (firstTwoDigits + ":" + numMessagesSend + ":" + firstword + lastword).toUpperCase();

            if (Choice == 1) {
                System.out.println("Message Sent");
                System.out.println("Message ID: " + MessageID);
                System.out.println("Message Hash: " + Hash);
                System.out.println("Recipient: " + RNumber);
                System.out.println("Message: " + MessageText);
            } else { 
                JSONObject messageObj = new JSONObject();
                messageObj.put("recipient", RNumber);
                messageObj.put("message", MessageText);
                messageObj.put("messageID", MessageID);
                messagesArray.put(messageObj);

                storeMessages("storedMessages.json", messagesArray);
                System.out.println("Message successfully stored");
            }
        }

        System.out.println("Total messages sent: " + numMessagesSend);
    }

    public static void storeMessages(String fileName, JSONArray array) throws JSONException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(array.toString(4));
        } catch (IOException e) {
            System.out.println("Could not store messages: " + e.getMessage());
        }
        
        
    }

 // POE PART 3 START HERE 

   
    static ArrayList<String> storedMessages = new ArrayList<>();
    static ArrayList<String> storedRecipients = new ArrayList<>();
    static ArrayList<String> storedSenders = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIds = new ArrayList<>();

    static String sender = "Developer";

    
    static String longestStoredMessage() {
        String longest = "";
        for (String m : storedMessages) {
            if (m.length() > longest.length()) {
                longest = m;
            }
        }
        return longest;
    }

    
    static void storedMessagesMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nSTORED MESSAGES");
            System.out.println("a) Sender and recipient of all stored messages");
            System.out.println("b) Longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search messages for a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Report of all stored messages");
            System.out.println("g) Back");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("a")) {
                if (storedMessages.isEmpty()) {
                    System.out.println("No stored messages.");
                } else {
                    for (int i = 0; i < storedMessages.size(); i++) {
                        System.out.println("Sender: " + storedSenders.get(i) + " | Recipient: " + storedRecipients.get(i) + " | Message: " + storedMessages.get(i));
                    }
                }
            } else if (choice.equals("b")) {
                System.out.println(longestStoredMessage());
            } else if (choice.equals("c")) {
                System.out.print("Enter Message ID to search: ");
                String searchId = scanner.nextLine().trim();
                boolean found = false;
                for (int i = 0; i < messageIds.size(); i++) {
                    if (messageIds.get(i).equals(searchId)) {
                        System.out.println("Recipient: " + storedRecipients.get(i));
                        System.out.println("Message: " + storedMessages.get(i));
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Message ID not found.");
                }
            } else if (choice.equals("d")) {
                System.out.print("Enter recipient to search: ");
                String searchRecipient = scanner.nextLine().trim();
                boolean found = false;
                for (int i = 0; i < storedRecipients.size(); i++) {
                    if (storedRecipients.get(i).equals(searchRecipient)) {
                        System.out.println("Message: " + storedMessages.get(i));
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No messages found.");
                }
            } else if (choice.equals("e")) {
                System.out.print("Enter Message Hash to delete: ");
                String searchHash = scanner.nextLine().trim();
                boolean found = false;
                for (int i = 0; i < messageHashes.size(); i++) {
                    if (messageHashes.get(i).equals(searchHash)) {
                        System.out.println("Message successfully deleted: " + storedMessages.get(i));
                        storedMessages.remove(i);
                        storedRecipients.remove(i);
                        storedSenders.remove(i);
                        messageHashes.remove(i);
                        messageIds.remove(i);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Hash not found.");
                }
            } else if (choice.equals("f")) {
                if (storedMessages.isEmpty()) {
                    System.out.println("No stored messages to display.");
                } else {
                    for (int i = 0; i < storedMessages.size(); i++) {
                        System.out.println("Hash: " + messageHashes.get(i));
                        System.out.println("Sender: " + storedSenders.get(i));
                        System.out.println("Recipient: " + storedRecipients.get(i));
                        System.out.println("Message: " + storedMessages.get(i));
                        System.out.println("---");
                    }
                }
            } else if (choice.equals("g")) {
                back = true;
            } else {
                System.out.println("Please choose a to g.");
            }
        }
    }

    