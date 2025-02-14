package br.com.alura.banking.agencia;

import br.com.alura.banking.endereco.Endereco;
import jakarta.persistence.*;

@Entity
public record Agencia(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id,

        String nome,

        @Column(name = "razao_social")
        String razaoSocial,

        String cnpj,

        @OneToOne
        @JoinColumn(name = "endereco_id")
        Endereco endereco) {
}
