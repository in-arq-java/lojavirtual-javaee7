/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.rest.client;

import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.rest.client.CategoriasRestClient;
import br.nom.penha.bruno.arquiteto.rest.client.json.CategoriasRestJSONClient;
import br.nom.penha.bruno.arquiteto.rest.client.xml.CategoriasRestXMLClient;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arquiteto
 */
public class TestCategoriaRest {

    private static CategoriasRestClient clientXML;
    private static CategoriasRestClient clientJSON;

    @BeforeClass
    public static void init() {
        clientXML = new CategoriasRestXMLClient();
        clientJSON = new CategoriasRestJSONClient();
    }

    @AfterClass
    public static void destroy() {
        clientXML.close();
        clientJSON.close();
    }

    @Test
    public void leSingleXML() {
        Categoria membro = clientXML.findByPrimaryKey(1);
        System.out.println("=========================== Single XML ================================");
        System.out.println(membro);
    }

    @Test
    public void leSingleJSON() {
        Categoria membro = clientJSON.findByPrimaryKey(10);
        System.out.println("=========================== Single JSON ================================");
        System.out.println(membro);
    }

    @Test
    public void leArrayXML() {
        Categoria[] membros = clientXML.findAll();
        System.out.println("=========================== Array XML ================================");
        for (Categoria membro : membros) {
            System.out.println(membro);
        }
    }
    
    @Test
    public void leArrayJSON() {
        Categoria[] membros = clientJSON.findAll();
        System.out.println("=========================== Array JSON ================================");
        for (Categoria membro : membros) {
            System.out.println(membro);
        }
    }

}
