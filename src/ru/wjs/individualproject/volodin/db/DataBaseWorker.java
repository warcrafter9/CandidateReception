package ru.wjs.individualproject.volodin.db;

import ru.wjs.individualproject.volodin.exceptions.DataBaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseWorker {
    private Connection connection;

    public DataBaseWorker(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataBaseConnectionException("Не удалось соединиться с БД");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        String candidatesTable = " create table IF NOT EXISTS candidates(" +
                "candidate_id integer primary key AUTO_INCREMENT," +
                "surname varchar not null," +
                "name varchar not null," +
                "patronymic varchar not null," +
                "age integer not null," +
                "party varchar not null)";
        String requestsTable = "create table IF NOT EXISTS requests (" +
                "request_id integer primary key AUTO_INCREMENT, " +
                "candidate_id integer not null, " +
                "request_category varchar not null, " +
                "request_status varchar not null," +
                "request_time timestamp not null, " +
                "FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id)" +
                ")";

        statement.execute(candidatesTable);
        statement.execute(requestsTable);
    }
}
