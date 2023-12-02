import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp {
    private static final Logger logger = Logger.getLogger(ClientApp.class.getName());

    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1";
        final int SERVER_PORT = 8081;

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String serverGreeting = in.readLine();
            logger.info("Server: " + serverGreeting);

            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        logger.info("Server: " + response);
                    }
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Exception occurred while reading from server", e);
                }
            }).start();


            out.println("привіт");

            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введіть що-небудь та натисніть Enter: ");
            String userInput = userInputReader.readLine();
            out.println(userInput);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
        }
    }
}
