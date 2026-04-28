package dev.nbcsparta.assignment.commerce_backoffice.entity;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"manager_id", "authority_id"}),
        name = "has_authority"
)
public class HasAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Manager manager;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Authority authority;

    protected HasAuthority() {
    }
}
