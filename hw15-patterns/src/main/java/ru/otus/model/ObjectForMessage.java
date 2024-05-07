package ru.otus.model;

import java.util.ArrayList;
import java.util.List;


public class ObjectForMessage {

    private List<String> data;

    public List<String> getData() {
        return data;
    }
    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage() {
        this.data = new ArrayList<>();
    }

    public ObjectForMessage(List<String> data) {
        this.data = new ArrayList<>(data); // Making a copy of the list to ensure deep copying
    }

    public static ObjectForMessage deepCopy(ObjectForMessage src) {
        if (src == null) {
            return null;
        }

        ObjectForMessage objectForMessage = new ObjectForMessage();

        if (src.getData() == null) {
            return objectForMessage;
        }

        objectForMessage.setData(List.copyOf(src.getData()));
        return objectForMessage;
    }
}
