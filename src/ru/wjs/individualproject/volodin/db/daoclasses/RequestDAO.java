package ru.wjs.individualproject.volodin.db.daoclasses;


import ru.wjs.individualproject.volodin.baseclasses.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class RequestDAO {
    private final Connection connection;

    public RequestDAO(Connection connection) {
        this.connection = connection;
    }

    public Request registerRequest(Request request, Integer candidateId, String text) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sqlInserting = String.format("insert into requests(candidate_id,request_category,request_status,request_time)" +
                        " values('%d','%s','%s','%s')",
                candidateId, request.getRequestCategory(), request.getRequestStatus(), request.getTimeOfRequest().format(formatter));
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlInserting);
            Request.getRequestMap().put(candidateId, text);
        }
        return request;
    }

    public void updateRequestStatus(int requestId, String newStatus) throws SQLException {
        String sql = "UPDATE requests SET request_status = ? WHERE request_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, requestId);
            preparedStatement.executeUpdate();
            connection.commit();
        }
    }


}
