package br.com.alura.banking.agencia;

import br.com.alura.banking.agencia.validation.AgenciaDTO;
import br.com.alura.banking.agencia.validation.AgenciaValidationApi;
import br.com.alura.banking.agencia.validation.SituacaoCadastral;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AgenciaService {

    @RestClient
    private AgenciaValidationApi agenciaValidationApi;

    private AgenciaRepository agenciaRepository;

    public AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    @Transactional
    public void cadastrar(Agencia agencia) {
        validarSituacaoAgencia(agencia);
        validarAgenciaDuplicada(agencia);

        agenciaRepository.persist(agencia);
    }

    @Transactional
    public void alterar(Agencia agencia) {
        validarSituacaoAgencia(agencia);

        agenciaRepository.update("nome = ?1, razaoSocial = ?2, cnpj = ?3 where id = ?4",
                agencia.getNome(),
                agencia.getRazaoSocial(),
                agencia.getCnpj(),
                agencia.getId());
    }

    public Agencia buscarPorId(Long id) {
        return agenciaRepository.findByIdOptional(id)
                .orElseThrow(AgenciaInativaOuInexistenteException::new);
    }

    public void excluirPorId(Long id) {
        agenciaRepository.deleteById(id);
    }

    private void validarSituacaoAgencia(Agencia agencia) {
        AgenciaDTO agenciaDTO = agenciaValidationApi.buscarPorCnpj(agencia.getCnpj());

        if (agenciaDTO == null || agenciaDTO.situacaoCadastral() != SituacaoCadastral.ATIVO) {
            throw new AgenciaInativaOuInexistenteException();
        }
    }

    private void validarAgenciaDuplicada(Agencia agencia) {
        if (agenciaRepository.findByCnpj(agencia.getCnpj()).isPresent()) {
            throw new AgenciaDuplicadaException();
        }
    }

}
