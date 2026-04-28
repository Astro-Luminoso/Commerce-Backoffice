package dev.nbcsparta.assignment.commerce_backoffice.entity;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL, orphanRemoval = true)
    List<HasAuthority> toAuthority;


    protected Authority() {
    }
}
