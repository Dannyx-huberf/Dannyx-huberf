import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpDictionaryServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port = 7000;

        try {
            System.out.println("[TcpDictionaryServer] listening on port = " + port);
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = null;
                try {
                    System.out.println("[TcpDictionaryServer] waiting for connection");
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection Established with " + clientSocket.getInetAddress());
                    new Thread(new ClientHandler(clientSocket)).start();
                } catch (Exception r) {
                    System.out.println("Connection not Established");
                    System.out.println(r.getMessage());
                    r.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the server socket when done
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("[TcpDictionaryServer] Server socket closed.");
                }
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;
    private static final Dictionary dictionary = new Dictionary();

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Initialize the dictionary with some words
            dictionaryDetails();

            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = read.readLine()) != null) {
                System.out.println("\nMessage received from client: " + message);
                String responseMessage = getResponseMessage(message);
                out.println(responseMessage); // Send response back to client
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            // Close the client socket when done
            try {
                if (socket != null) {
                    socket.close();
                    System.out.println("[ClientHandler] Client socket closed.");
                }
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public static String getResponseMessage(String message) {
        StringBuilder responseMessage = new StringBuilder();
        if (dictionary.containsWord(message)) {
            WordDefinition wordDef = dictionary.getWordDefinition(message);
            responseMessage.append("Definition: ").append(wordDef.getDefinition())
                    .append(" | Examples: ").append(wordDef.getExamples().toString())
                    .append(" | Synonyms: ").append(wordDef.getSynonyms().toString());
        } else {
            responseMessage.append(message).append(" is not in the dictionary.");
        }
        return responseMessage.toString();
    }

    public static void dictionaryDetails() {
        dictionary.addWord("Kaka", "verb' the ability to poo ");
        dictionary.addExample("Kaka", "I wan go 'Kaka'.");
        dictionary.addSynonym("Kaka", "Gbola");

        dictionary.addWord("Japa", "'verb' The ability to run away ");
        dictionary.addExample("Japa", "I want to 'Japa' from this country.");
        dictionary.addSynonym("Japa", "Sare");

        dictionary.addWord("Dem", "'pronoun' A group of People");
        dictionary.addExample("Dem", "Look at Dem.");
        dictionary.addSynonym("Dem", "[no suitable synonym]");

        dictionary.addWord("Wetin", "'verb' What");
        dictionary.addExample("Wetin", "Wetin dey sup");
        dictionary.addSynonym("Wetin", "[no suitable synonym]");

        dictionary.addWord("Dey", "'conjunction, verb',Is,Can also mean the state of a thing");
        dictionary.addExample("Dey", "Wetin dey sup.");
        dictionary.addSynonym("Dey", "[no suitable synonym]");

        dictionary.addWord("Sup", "'verb' the state of a thing");
        dictionary.addExample("Sup", "Wetin dey sup");
        dictionary.addSynonym("Sup", "dey");
    }
}