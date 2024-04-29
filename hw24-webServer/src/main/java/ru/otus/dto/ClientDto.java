package ru.otus.dto;

import java.util.Arrays;
import lombok.Data;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

@Data
public class ClientDto {
    String name;
    String address;
    String phones;

    public Client fromClientDtoToClient() {
        String[] phones = this.getPhones().split(",");
        return new Client(
                null,
                this.getName(),
                new Address(null, this.getAddress()),
                Arrays.stream(phones).map(phone -> new Phone(null, phone)).toList());
    }
}
