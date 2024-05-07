package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.model.Client;
import ru.otus.service.ClientService;
import java.util.List;


@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }

    @PostMapping("/client/create")
    public String createClient(@RequestParam String name) {
        Client client = new Client();
        client.setName(name);
        clientService.save(client);
        return "redirect:/client/list";
    }

    @GetMapping("/client/update/{id}")
    public String updateClientView(@PathVariable long id, Model model) {
        Client client = clientService.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        model.addAttribute("client", client);
        return "updateClient";
    }

    @PostMapping("/client/update")
    public String updateClient(@RequestParam long id, @RequestParam String name) {
        Client existingClient = clientService.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        existingClient.setName(name);
        clientService.save(existingClient);
        return "redirect:/client/list";
    }

    @GetMapping("/client/delete/{id}")
    public String deleteClient(@PathVariable long id) {
        Client client = clientService.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        clientService.delete(client);
        return "redirect:/client/list";
    }
}
