package br.nom.penha.bruno.arquiteto.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Superclasse abstrata com as funcionalidades básicas para acessar serviços
 * REST. Cada classe de serviço específica estende esta classe.
 * 
 * @author Arquiteto
 */
public abstract class RestClient<E> {

    protected Client client;
    protected WebTarget webTarget;

    public RestClient() {
        client = ClientBuilder.newClient();
    }
    
    public abstract void update(E categoria);
    public abstract void save(E categoria);
    public abstract E findByPrimaryKey(Integer id);
    public abstract E[] findAll();


    protected void validaResponse(Response response) {
        if (response.getStatus() >= 400) {
            throw new RuntimeException("resposta invalida " + response);
        }
    }

    protected WebTarget getWebTarget() {
        return webTarget;
    }

    public void close() {
        client.close();
    }

}
