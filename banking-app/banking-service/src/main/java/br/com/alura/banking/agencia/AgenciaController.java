package br.com.alura.banking.agencia;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/agencias")
public class AgenciaController {

    private AgenciaService agenciaService;

    AgenciaController(AgenciaService agenciaService) {
        this.agenciaService = agenciaService;
    }

    @POST
    public RestResponse<Void> criar(Agencia agencia, @Context UriInfo uriInfo) {
        agenciaService.cadastrar(agencia);
        return RestResponse.created(uriInfo.getAbsolutePath());
    }

    @GET
    @Path("{id}")
    public RestResponse<Agencia> buscar(Long id) {
        Agencia agencia = agenciaService.buscarPorId(id);
        return RestResponse.ok(agencia);
    }

    @PUT
    public RestResponse<Void> alterar(Agencia agencia) {
        agenciaService.alterar(agencia);
        return RestResponse.ok();
    }

    @DELETE
    @Path("{id}")
    public RestResponse<Void> excluir(Long id) {
        agenciaService.excluirPorId(id);
        return RestResponse.ok();
    }

}
