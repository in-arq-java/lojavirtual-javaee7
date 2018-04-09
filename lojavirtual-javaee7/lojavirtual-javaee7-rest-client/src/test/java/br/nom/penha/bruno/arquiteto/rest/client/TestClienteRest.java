/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.rest.client;

import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.rest.client.ClientesRestClient;
import br.nom.penha.bruno.arquiteto.rest.client.json.ClientesRestJSONClient;
import br.nom.penha.bruno.arquiteto.rest.client.xml.ClientesRestXMLClient;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Arquiteto
 */
public class TestClienteRest {

    private static ClientesRestClient clientXML;
    private static ClientesRestClient clientJSON;

    @BeforeClass
    public static void init() {
        clientXML = new ClientesRestXMLClient();
        clientJSON = new ClientesRestJSONClient();
    }

    @AfterClass
    public static void destroy() {
        clientXML.close();
        clientJSON.close();
    }

    @Test
    public void leSingleXML() {
        Cliente membro = clientXML.findByPrimaryKey(1);
        System.out.println("======================= Cliente Single XML ================================");
        System.out.println(membro);
    }
    
    @Test
    public void leSingleJSON() {
        Cliente membro = clientJSON.findByPrimaryKey(2);
        System.out.println("======================= Cliente Single JSON ================================");
        System.out.println(membro);
    }


    @Test
    public void leArrayXML() {
        Cliente[] membros = clientXML.findAll();
        System.out.println("======================== Cliente Array XML ================================");
        for (Cliente membro : membros) {
            System.out.println(membro);
        }
    }

    @Test
    public void leArrayJSON() {
        Cliente[] membros = clientJSON.findAll();
        System.out.println("======================== Cliente Array JSON ================================");
        for (Cliente membro : membros) {
            System.out.println(membro);
        }
    }

}
