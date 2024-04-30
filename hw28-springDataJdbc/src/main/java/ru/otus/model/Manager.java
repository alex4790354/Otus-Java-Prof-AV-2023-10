package ru.otus.model;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Manager {

    @Id
    private Long no;
    private String label;
    private String param1;

}
