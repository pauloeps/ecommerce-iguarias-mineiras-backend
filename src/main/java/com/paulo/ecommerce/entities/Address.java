package com.paulo.ecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @NotBlank(message = "O CEP não pode estar vazio")
    @Column(nullable = false, length = 9)
    private String cep;

    @NotBlank(message = "A rua não pode estar vazia")
    @Column(nullable = false, length = 120)
    private String street;

    @NotBlank(message = "O número não pode estar vazio")
    @Column(nullable = false, length = 10)
    private String number;

    @NotBlank(message = "A cidade não pode estar vazia")
    @Column(nullable = false, length = 60)
    private String city;

    @NotBlank(message = "A UF não pode estar vazia")
    @Size(min = 2, max = 2, message = "A UF deve ter 2 caracteres")
    @Column(nullable = false, length = 2)
    private String uf;
}
