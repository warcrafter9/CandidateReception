package ru.wjs.individualproject.volodin.clientserver;

import ru.wjs.individualproject.volodin.baseclasses.Candidate;
import ru.wjs.individualproject.volodin.baseclasses.Request;
import ru.wjs.individualproject.volodin.db.DataBaseWorker;
import ru.wjs.individualproject.volodin.db.daoclasses.CandidateDAO;
import ru.wjs.individualproject.volodin.db.daoclasses.RequestDAO;
import ru.wjs.individualproject.volodin.typesrequests.RequestCategory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        DataBaseWorker dataBaseWorker = new DataBaseWorker("jdbc:h2:~/test", "sa", "");
        CandidateDAO candidateDAO = new CandidateDAO(dataBaseWorker.getConnection());
        RequestDAO requestDAO = new RequestDAO(dataBaseWorker.getConnection());
        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println(clientSocket + " connect ");
                new Thread(() -> handleClient(clientSocket, candidateDAO, requestDAO)).start();
            }
        }

    }

    private static void handleClient(Socket clientSocket, CandidateDAO candidateDAO, RequestDAO requestDAO) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            writer.write("Выберите номер кандидата, которому хотите отправить своё обращение:\n");
            writer.flush();
            for (Candidate candidate : candidateDAO.getAllCandidates()) {
                writer.write(candidate.toString() + "\n");
                writer.flush();
            }
            int id = Integer.parseInt(reader.readLine());
            String responseCategory = reader.readLine();
            RequestCategory category = RequestCategory.findCategoryByTranslation(responseCategory);
            String text = reader.readLine();
            Request request = new Request(category, text);
            requestDAO.registerRequest(request, id, text);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println(clientSocket + " disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
