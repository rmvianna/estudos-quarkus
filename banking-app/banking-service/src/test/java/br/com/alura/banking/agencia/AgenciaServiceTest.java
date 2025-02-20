package br.com.alura.banking.agencia;

import br.com.alura.banking.agencia.validation.AgenciaDTO;
import br.com.alura.banking.agencia.validation.AgenciaValidationApi;
import br.com.alura.banking.agencia.validation.SituacaoCadastral;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class AgenciaServiceTest {

    @InjectMock
    private AgenciaRepository agenciaRepository;

    @InjectMock
    @RestClient
    private AgenciaValidationApi agenciaValidationApi;

    @Inject
    private AgenciaService agenciaService;

    @Test
    void naoDeveCadastrarQuandoAgenciaNaoExistir() {
        Mockito.when(agenciaValidationApi.buscarPorCnpj("123")).thenReturn(null);

        Agencia agencia = criarAgencia("123");
        assertThrows(AgenciaInativaOuInexistenteException.class, () -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    void naoDeveCadastrarQuandoAgenciaEstahInativa() {
        Mockito.when(agenciaValidationApi.buscarPorCnpj("123")).thenReturn(new AgenciaDTO("nome", "razao", "123", SituacaoCadastral.INATIVO));

        Agencia agencia = criarAgencia("123");
        assertThrows(AgenciaInativaOuInexistenteException.class, () -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    void naoDeveCadastrarQuandoAgenciaJahExiste() {
        Mockito.when(agenciaValidationApi.buscarPorCnpj("123")).thenReturn(new AgenciaDTO("nome", "razao", "123", SituacaoCadastral.ATIVO));
        Mockito.when(agenciaRepository.findByCnpj("123")).thenReturn(java.util.Optional.of(new Agencia()));

        Agencia agencia = criarAgencia("123");
        assertThrows(AgenciaDuplicadaException.class, () -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    void deveCadastrarQuandoAgenciaEstahAtivaEehUnica() {
        Mockito.when(agenciaValidationApi.buscarPorCnpj("123")).thenReturn(new AgenciaDTO("nome", "razao", "123", SituacaoCadastral.ATIVO));
        Mockito.when(agenciaRepository.findByCnpj("123")).thenReturn(java.util.Optional.empty());

        Agencia agencia = criarAgencia("123");
        assertDoesNotThrow(() -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository).persist(agencia);
    }

    private Agencia criarAgencia(String cnpj) {
        Agencia ag = new Agencia();
        ag.setCnpj(cnpj);

        return ag;
    }

}