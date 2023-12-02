import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketApp {
    private static final Logger logger = Logger.getLogger(ServerSocketApp.class.getName());

    public static void main(String[] args) {
        final int SERVER_PORT = 8081;

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            logger.info("Server is listening on port " + SERVER_PORT);

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                // Отримуємо привітання від клієнта
                String clientGreeting = in.readLine();
                logger.info("Client: " + clientGreeting);

                // Питаємо клієнта "Що таке паляниця?"
                if (clientGreeting.contains("паляниця")) {
                    logger.info("Server: Що таке паляниця?");

                    boolean correctAnswer = false;

                    do {
                        // Отримуємо відповідь від користувача за допомогою Scanner
                        Scanner scanner = new Scanner(System.in);
                        String userAnswer = scanner.nextLine();

                        // Перевіряємо відповідь користувача
                        if (userAnswer.equalsIgnoreCase("український хліб")) {
                            // Відправляємо поточну дату та час, якщо відповідь правильна
                            out.println("Поточна дата та час: " + java.time.LocalDateTime.now());
                            correctAnswer = true;
                        } else {
                            // Повторюємо запитання, якщо відповідь неправильна
                            logger.info("Server: Неправильна відповідь. Спробуйте ще раз.");
                            out.println("Що таке паляниця?");
                        }
                    } while (!correctAnswer);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Exception occurred", e);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
        }
    }
}
