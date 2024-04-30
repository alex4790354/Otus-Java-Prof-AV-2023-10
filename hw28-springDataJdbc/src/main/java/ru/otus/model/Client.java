package ru.otus.model;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    private Long id;
    private String name;

}
