package ru.wjs.individualproject.volodin.clientserver;

import ru.wjs.individualproject.volodin.typesrequests.RequestCategory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) throws IOException {
        try (Socket clientSocket = new Socket("127.0.0.1", 8000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            Scanner scanner = new Scanner(System.in);
            Integer numberCandidates = 0;
            new Thread(() -> readServerMessages(reader, numberCandidates)).start();
            new Thread(() -> processUserInput(writer, scanner, numberCandidates)).start();
        }
    }

    private static void processUserInput(BufferedWriter writer, Scanner scanner, Integer numberCandidates) {
        try {
            while (true) {
                System.out.println("Количество прочитанных сообщений: " + numberCandidates);
                System.out.println("Введите номер кандидата (1-" + numberCandidates + "):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Введите корректное целое число.");
                    scanner.next();
                }
                int candidateNumber = scanner.nextInt();
                if (candidateNumber >= 1 && candidateNumber <= numberCandidates) {
                    writer.write(candidateNumber + "\n");
                    writer.flush();
                    break;
                } else {
                    System.out.println("Введите число от 1 до " + numberCandidates);
                }
                System.out.println("Введите категорию: вопрос, жалоба, благодарность:");
                while (true) {
                    String userInput = scanner.next();
                    if (checkCategory(RequestCategory.values(), userInput)) {
                        writer.write(userInput + "\n");
                        writer.flush();
                        break;
                    } else {
                        System.out.println("Попробуйте еще раз.");
                    }
                    createText(scanner, writer);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readServerMessages(BufferedReader reader, Integer numberCandidates) {
        try {
            String serverMessage;
            while ((serverMessage = reader.readLine()) != null) {
                System.out.println(serverMessage);
                ++numberCandidates;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean checkCategory(RequestCategory[] CategoryValues, String input) {
        for (RequestCategory category : CategoryValues) {
            if (category.getTranslation().equals(input)) {
                return true;
            }
        }
        return false;
    }

    private static String createText(Scanner scanner, BufferedWriter writer) throws IOException {
        StringBuilder text = new StringBuilder();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("end")) {
                writer.write(String.valueOf(text));
                break;
            }
            text.append(input).append("\n");
        }
        return String.valueOf(text);
    }


}
