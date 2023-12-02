package ru.wjs.individualproject.volodin.baseclasses;
import ru.wjs.individualproject.volodin.typesrequests.RequestCategory;
import ru.wjs.individualproject.volodin.typesrequests.RequestStatus;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Request {
    private RequestCategory requestCategory;
    private RequestStatus requestStatus;
    private int id;
    private String text;
    private LocalDateTime timeOfRequest;
    private static HashMap<Integer, String> requestMap = new HashMap<>();

    public static HashMap<Integer, String> getRequestMap() {
        return requestMap;
    }

    public Request(RequestCategory requestCategory, String text) {
        this.requestCategory = requestCategory;
        this.requestStatus = RequestStatus.OPEN;
        this.text = text;
        this.timeOfRequest = LocalDateTime.now();
    }

    public RequestCategory getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(RequestCategory requestCategory) {
        this.requestCategory = requestCategory;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimeOfRequest() {
        return timeOfRequest;
    }

    public void setTimeOfRequest(LocalDateTime timeOfRequest) {
        this.timeOfRequest = timeOfRequest;
    }
}
