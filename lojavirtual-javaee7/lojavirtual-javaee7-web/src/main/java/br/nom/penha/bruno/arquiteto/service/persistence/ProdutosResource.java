package br.nom.penha.bruno.arquiteto.service.persistence;

import br.nom.penha.bruno.arquiteto.business.ProdutoFacade;
import br.nom.penha.bruno.arquiteto.model.Produto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status;

/**
 * Recurso JAX-RS para gerenciamento de produtos.
 *
 * A anotação @RequestScoped é utilizada para transformar o recurso em um bean
 * CDI e permitir a utilização da injeção via @Inject sem a necessidade de
 * configurações adicionais.
 *
 * @author Arquiteto
 */
@Path("produtos")
@RequestScoped
public class ProdutosResource {

    @Inject
    private ProdutoFacade facade;

    @POST
    @Consumes("application/json")
    public Response save(JsonObject entity) {
        //TODO implemente aqui o código para salvar o produto

        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void update(@PathParam("id") Long id, JsonObject entity) {
        //TODO implemente aqui o código para salvar o produto
        
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        Produto prod = facade.findByPrimaryKey(id);
        facade.delete(prod);
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public JsonObject getByPrimaryKey(@PathParam("id") Integer id) {
        //TODO implemente aqui o código para ler o produto
        
        return null;
    }

    @GET
    @Produces("application/json")
    public JsonArray getAll() {
        //TODO implemente aqui o código para ler os produtos
        
        return null;
    }

}
