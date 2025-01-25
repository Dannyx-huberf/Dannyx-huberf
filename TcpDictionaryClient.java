import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TcpDictionaryClient {
    public static void main(String[] args) {
        Socket clientSocket = null;
        String serverHostName = "localhost";
        int port = 7000;
        Scanner input = new Scanner(System.in);

        try {
            System.out.println("Attempting to connect to the server on port: " + port + " Server address: " + serverHostName);
            clientSocket = new Socket(serverHostName, port);
            System.out.println("Connection established");

            PrintStream out = new PrintStream(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String word;
            while (true) {
                System.out.print("Enter the word you are looking for (or type 'exit' to quit): ");
                word = input.nextLine().trim();

                if (word.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the client.");
                    break; // Exit the loop if the user types 'exit'
                }

                out.println(word); // Send the word to the server
                System.out.println("Requesting the meaning of: " + word);

                // Read the response from the server
                String response = in.readLine();
                if (response != null) {
                    // Split the response if needed
                    String[] parts = response.split(" \\| "); // Split by the delimiter
                    for (String part : parts) {
                        System.out.println(part); // Print each part on a new line
                    }
                } else {
                    System.out.println("No response from server.");
                }
            }

        } catch (IOException e) {
            System.out.println("Connection not successful");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    System.out.println("Connection closed.");
                }
                input.close();
            } catch (IOException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}