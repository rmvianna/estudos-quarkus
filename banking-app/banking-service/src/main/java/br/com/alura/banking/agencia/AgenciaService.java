package br.com.alura.banking.agencia;

import br.com.alura.banking.agencia.validation.AgenciaDTO;
import br.com.alura.banking.agencia.validation.AgenciaValidationApi;
import br.com.alura.banking.agencia.validation.SituacaoCadastral;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AgenciaService {

    private static final Set<Agencia> REPOSITORY = new HashSet<>();

    @RestClient
    private AgenciaValidationApi agenciaValidationApi;

    public void cadastrar(Agencia agencia) {
        validarSituacaoAgencia(agencia);
        validarAgenciaDuplicada(agencia);

        REPOSITORY.add(agencia);
    }

    public void alterar(Agencia agencia) {
        validarSituacaoAgencia(agencia);

        REPOSITORY.removeIf(a -> a.id().equals(agencia.id()));
        REPOSITORY.add(agencia);
    }

    public Agencia buscarPorId(Integer id) {
        return REPOSITORY.stream()
                .filter(agencia -> agencia.id().equals(id))
                .findFirst()
                .orElseThrow(AgenciaInativaOuInexistenteException::new);
    }

    public void excluirPorId(Integer id) {
        REPOSITORY.removeIf(agencia -> agencia.id().equals(id));
    }

    private void validarSituacaoAgencia(Agencia agencia) {
        AgenciaDTO agenciaDTO = agenciaValidationApi.buscarPorCnpj(agencia.cnpj());

        if (agenciaDTO == null || agenciaDTO.situacaoCadastral() != SituacaoCadastral.ATIVO) {
            throw new AgenciaInativaOuInexistenteException();
        }
    }

    private void validarAgenciaDuplicada(Agencia agencia) {
        if (REPOSITORY.stream().anyMatch(a ->
                a.cnpj().equals(agencia.cnpj())
                || a.id() == agencia.id())) {
            throw new AgenciaDuplicadaException();
        }
    }

}
