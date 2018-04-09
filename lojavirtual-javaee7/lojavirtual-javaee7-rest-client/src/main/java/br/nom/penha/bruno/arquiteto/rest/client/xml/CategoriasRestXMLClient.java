package br.nom.penha.bruno.arquiteto.rest.client.xml;

import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.rest.client.CategoriasRestClient;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Arquiteto
 */
public class CategoriasRestXMLClient extends CategoriasRestClient {

    @Override
    public Categoria[] findAll() throws ClientErrorException {
        throw new UnsupportedOperationException("Esse método deve ser implementado!!!");
    }

    @Override
    public void update(Categoria categoria) throws ClientErrorException {
        throw new UnsupportedOperationException("Esse método deve ser implementado!!!");
    }

    @Override
    public void save(Categoria categoria) throws ClientErrorException {
        throw new UnsupportedOperationException("Esse método deve ser implementado!!!");
    }

    @Override
    public Categoria findByPrimaryKey(Integer id) throws ClientErrorException {
        throw new UnsupportedOperationException("Esse método deve ser implementado!!!");
    }
}
