package br.com.alura.banking.agencia.validation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/situacao-cadastral")
@RegisterRestClient(configKey = "situacao-cadastral-api")
public interface AgenciaValidationApi {

    @GET
    @Path("/{cnpj}")
    AgenciaDTO buscarPorCnpj(String cnpj);

}
