package org.emmek.beu2w2p.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "devices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Device {

    @Id
    @GeneratedValue
    private long id;
    private String category;
    private String state;
    private String picture;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
