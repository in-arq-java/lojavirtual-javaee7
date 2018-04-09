/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.rest.client;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.nom.penha.bruno.arquiteto.model.Produto;
import br.nom.penha.bruno.arquiteto.service.persistence.JsonFactory;

/**
 *
 * @author Arquiteto
 */
public class TestProdutosRest {

    private static Client client;
    private static WebTarget target;

    @BeforeClass
    public static void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/lojavirtual-javaee7-web/resources/produtos");
    }

    @Test
    public void testLeProduto() {
        JsonObject produtoJson = target.path("1").request().get(JsonObject.class);
        Produto produto = JsonFactory.createProduto(produtoJson);
        System.out.println(produto);
    }

    @Test
    public void testLeTodosProdutos() {
        JsonArray produtosJsonArray = target.request().get(JsonArray.class);
        for (int i = 0; i < produtosJsonArray.size(); i++) {
            System.out.println(produtosJsonArray.getJsonObject(i));
        }
    }
    
//    @Test
//    public void testSalvaProduto() {
//        String produto = "{\"nome\":\"Vingadores\",\"preco\":89.9,\"marca\":\"Marvel Studios\",\"categoria\":{\"id\":2,\"descricao\":\"Blu-ray\"},\"ativo\":true,\"type\":\"material\",\"taxaEntrega\":5.0}";
//        Response post = target.request().post(Entity.json(produto));
//        int status = post.getStatus();
//        System.out.println("================ Resposta recebida em testSalvaProduto \n" + post + "\n =====================");
//        Assert.assertTrue(status < 400);
//    }
//    
//    @Test
//    public void testAtualizaProduto() {
//        String produto = "{\"id\":1,\"nome\":\"Vingadores\",\"preco\":89.9,\"marca\":\"Marvel Studios\",\"categoria\":{\"id\":2,\"descricao\":\"Blu-ray\"},\"ativo\":true,\"type\":\"material\",\"taxaEntrega\":5.0}";
//        Response post = target.path("1").request().put(Entity.json(produto));
//        int status = post.getStatus();
//        System.out.println("================ Resposta recebida em testSalvaProduto \n" + post + "\n =====================");
//        Assert.assertTrue(status < 400);
//    }
//
//    @Test
//    public void testRemoveProduto() {
//        Response post = target.path("6").request().delete();
//        int status = post.getStatus();
//        System.out.println("================ Resposta recebida em testSalvaProduto \n" + post + "\n =====================");
//        Assert.assertTrue(status < 400);
//    }
    
}
