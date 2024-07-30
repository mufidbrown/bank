package com.dbs.entity;

import com.dbs.entity.Enum.Role;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

//    @OneToMany(mappedBy = "user")
//    private List<Account> accounts;

    @OneToMany(mappedBy = "user")
    private Set<Account> accounts;
}
