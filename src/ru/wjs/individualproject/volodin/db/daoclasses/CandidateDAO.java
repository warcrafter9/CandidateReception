package ru.wjs.individualproject.volodin.db.daoclasses;

import ru.wjs.individualproject.volodin.baseclasses.Candidate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO {
    private Connection connection;

    public CandidateDAO(Connection connection) {
        this.connection = connection;
    }

    public Candidate registerCandidate(Candidate candidate) throws SQLException {
        String sqlInserting = String.format("insert into candidates(surname,name,patronymic,age,party) values('%s','%s','%s','%d','%s')",
                candidate.getSurname(), candidate.getName(), candidate.getPatronymic(), candidate.getAge(), candidate.getParty());
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlInserting);
        }
        return candidate;
    }


    public Candidate findCandidate(Integer id) {
        String sqlSelector = String.format("select * from candidates where candidate_id = %d", id);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlSelector);
            if (resultSet.next()) {
                return new Candidate(
                        resultSet.getInt("CANDIDATE_ID"),
                        resultSet.getString("SURNAME"),
                        resultSet.getString("NAME"),
                        resultSet.getString("PATRONYMIC"),
                        resultSet.getInt("AGE"),
                        resultSet.getString("PARTY"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCandidate(Integer id) throws SQLException {
        String sqlDeleter = String.format("delete from candidates where candidate_id = %d", id);
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlDeleter);
        }
    }

    public List<Candidate> getAllCandidates() {
        String sqlRequest = "select * from candidates";
        List<Candidate> candidateList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                Candidate candidate = new Candidate(
                        resultSet.getInt("CANDIDATE_ID"),
                        resultSet.getString("SURNAME"),
                        resultSet.getString("NAME"),
                        resultSet.getString("PATRONYMIC"),
                        resultSet.getInt("AGE"),
                        resultSet.getString("PARTY"));
                candidateList.add(candidate);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return candidateList;
    }

    public void printAllCandidate() {
        for (Candidate candidate : getAllCandidates()) {
            System.out.println(candidate);
        }
    }
}

