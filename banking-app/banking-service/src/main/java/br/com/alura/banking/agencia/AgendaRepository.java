package br.com.alura.banking.agencia;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgendaRepository implements PanacheRepository<Agencia> {



}
