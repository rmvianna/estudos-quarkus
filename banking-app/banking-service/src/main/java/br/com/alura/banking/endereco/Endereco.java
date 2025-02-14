package br.com.alura.banking.endereco;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Endereco(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id,

        String rua,

        String logradouro,

        String complemento,

        Integer numero) {
}
