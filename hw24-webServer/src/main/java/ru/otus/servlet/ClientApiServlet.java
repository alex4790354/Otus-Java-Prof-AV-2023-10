package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDto;

public class ClientApiServlet extends HttpServlet {

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String value = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ClientDto clientDto = gson.fromJson(value, ClientDto.class);
        Client clientDb = dbServiceClient.saveClient(clientDto.fromClientDtoToClient());
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();
        out.print("Client successfully added with id: " + clientDb.getId());
    }
}
