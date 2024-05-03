package ru.otus.model;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import lombok.*;


@Getter
@AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    private final Long id;
    private final String name;

}
