package dev.smirnoff.model;

import lombok.*;

/**
 * @author pavelsmirnov
 * @version 1.0
 * дата создания 16.03.2021
 */
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String lastName;
    private Byte age;
}
