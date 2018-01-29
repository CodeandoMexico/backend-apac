/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.example.dao.ApacBaseDTO;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Gad
 */
@Controller
@RequestMapping(value = Main.API_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class Servicios {

    public static final String ENDPOINT_USUARIOS = "usuarios";
    public static final String ENDPOINT_ALUMNOS = "alumnos";
    public static final String ENDPOINT_ALUMNOS_X_CENTRO = "alumnosxcentro";
    public static final String ENDPOINT_EVALUADORES = "evaluadores";

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = ENDPOINT_USUARIOS, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getUsers() {

        String llave = "heNFkdTzSGbKsseIX4bBB33tZGWFgmyRDlRu2DnCRrRMHxepY0H7liUJmc4I25yFcfThuq8Os59QjUwaQCDAkQ==";
        String response = consultaBDServicio(llave);
        
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
    }

    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getAlumnos() {

        String llave = "DJGK22ya891zn3I3wFE1KpJacWmiTL+NhKmi7HWgmDkh4C7pmdmFp98vV1ruA+55tdicbvJFQuqJ33IFo1eqyw==";
        String response = consultaBDServicio(llave);
        
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
    
    @RequestMapping(value = ENDPOINT_ALUMNOS_X_CENTRO, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getAlumnosXCentro() {

        String llave = "P2iExqhMcOepEZa5vUKQrqA62DPUlmhOxQhfPjXLMEJ3KNq+dGMmQm0EaIJZ4xmVZPNVHONmYbmPSxJgxHvaWw==";
        String response = consultaBDServicio(llave);
        
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
    
    @RequestMapping(value = ENDPOINT_EVALUADORES, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getEvaluadores() {

        String llave = "qzIn2O4fFro4SrquFyiXiRPCes9KqlmNjtFZJzwjgcvXhe6nkfvT+FL81ozGg0YSruFMdhPUjmNDP7DIMbSf5A==";
        String response = consultaBDServicio(llave);
        
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
    
    
    private String consultaBDServicio(String digestionServicio) {

        String query = "select \n"
                + "	b.llave,\n"
                + "	b.valor,\n"
                + "	b.digestion,\n"
                + "	b.ultima_actualizacion\n"
                + "	from apac_schema.informacion_base b \n"
                + "	where b.llave = '" + digestionServicio + "';";
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        Gson gson = new Gson();
        ApacBaseDTO dto = new ApacBaseDTO();

        try {
            connection = dataSource.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                dto.setLlave(rs.getString("llave"));
                dto.setValor(rs.getString("valor"));
                dto.setDigestion(rs.getString("digestion"));
                dto.setUltimaActualizacion(rs.getLong("ultima_actualizacion"));
            }
            return gson.toJson(dto);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
