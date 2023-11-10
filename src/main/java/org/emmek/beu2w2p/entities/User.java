package org.emmek.beu2w2p.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @OneToMany(mappedBy = "user")
    @ToStringExclude
    @JsonIgnore
    @ToString.Exclude
    List<Device> devices;
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String name;
    private String surname;
    private String email;
}
