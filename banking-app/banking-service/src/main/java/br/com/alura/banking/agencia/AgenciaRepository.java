package br.com.alura.banking.agencia;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class AgenciaRepository implements PanacheRepository<Agencia> {

    public Optional<Agencia> findByCnpj(String cnpj) {
        return find("cnpj", cnpj).firstResultOptional();
    }

}
