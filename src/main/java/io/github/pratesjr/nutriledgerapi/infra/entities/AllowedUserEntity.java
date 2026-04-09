package io.github.pratesjr.nutriledgerapi.infra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "allowed_users", schema = "nutri_ledger")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllowedUserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
}
