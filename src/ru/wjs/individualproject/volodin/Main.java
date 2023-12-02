package ru.wjs.individualproject.volodin;

import ru.wjs.individualproject.volodin.baseclasses.Candidate;
import ru.wjs.individualproject.volodin.baseclasses.Request;
import ru.wjs.individualproject.volodin.db.DataBaseWorker;
import ru.wjs.individualproject.volodin.db.daoclasses.CandidateDAO;
import ru.wjs.individualproject.volodin.db.daoclasses.RequestDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBaseWorker dataBaseWorker = new DataBaseWorker("jdbc:h2:~/test", "sa", "");
        dataBaseWorker.createTables();
        CandidateDAO candidateDAO = new CandidateDAO(dataBaseWorker.getConnection());
        Candidate candidate1 = new Candidate("Горчанов", "Иван", "Васильевич", 40, "Айва");
        Candidate candidate2 = new Candidate("Иванов", "Иван", "Иван", 35, "Белый Лотос");
        RequestDAO requestDAO = new RequestDAO(dataBaseWorker.getConnection());
        Scanner scanner = new Scanner(System.in);
         candidateDAO.registerCandidate(candidate1);
        candidateDAO.registerCandidate(candidate2);
        for (Candidate candidate : candidateDAO.getAllCandidates()) {
            System.out.println(candidate);
        }
      //  System.out.println(Request.getRequestMap().get(1));
         org.h2.tools.Console.main("-web", "-browser");
        dataBaseWorker.getConnection().close();
    }
}
