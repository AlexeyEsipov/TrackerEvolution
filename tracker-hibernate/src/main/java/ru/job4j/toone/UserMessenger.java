package ru.job4j.toone;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "j_user_notification")
public class UserMessenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String messenger;
    private String identify;
}