/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.example.dao.ApacBaseDTO;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getAlumnos() {

        String llave = "DJGK22ya891zn3I3wFE1KpJacWmiTL+NhKmi7HWgmDkh4C7pmdmFp98vV1ruA+55tdicbvJFQuqJ33IFo1eqyw==";
        String response = consultaBDServicio(llave);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = ENDPOINT_ALUMNOS_X_CENTRO, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getAlumnosXCentro() {

        String llave = "P2iExqhMcOepEZa5vUKQrqA62DPUlmhOxQhfPjXLMEJ3KNq+dGMmQm0EaIJZ4xmVZPNVHONmYbmPSxJgxHvaWw==";
        String response = consultaBDServicio(llave);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = ENDPOINT_EVALUADORES, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getEvaluadores() {

        String llave = "qzIn2O4fFro4SrquFyiXiRPCes9KqlmNjtFZJzwjgcvXhe6nkfvT+FL81ozGg0YSruFMdhPUjmNDP7DIMbSf5A==";
        String response = consultaBDServicio(llave);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = ENDPOINT_EVALUADORES, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> setEvaluadores(@RequestParam("v") String evaluadores, @RequestParam("d") String digestion) {
        ApacBaseDTO dto = new ApacBaseDTO();
        String llave = "qzIn2O4fFro4SrquFyiXiRPCes9KqlmNjtFZJzwjgcvXhe6nkfvT+FL81ozGg0YSruFMdhPUjmNDP7DIMbSf5A==";
        if (evaluadores != null && !evaluadores.isEmpty() && digestion != null && !digestion.isEmpty()) {

            dto.setLlave(llave);
            dto.setValor(evaluadores);
            dto.setDigestion(digestion);
            if (actualizaBDServicio(dto) > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Gson().toJson(dto), HttpStatus.BAD_REQUEST);

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
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Gson gson = new Gson();
        ApacBaseDTO dto = new ApacBaseDTO();

        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
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
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    private int actualizaBDServicio(ApacBaseDTO dto) {

        String query = "update apac_schema.informacion_base b \n"
                + "	set valor = '" + dto.getValor() + "',\n"
                + "	digestion = '" + dto.getDigestion() + "',\n"
                + "	ultima_actualizacion = (extract(epoch from now() at time zone 'UTC')*1000)::bigint \n"
                + "	where b.llave = '" + dto.getLlave() + "';";
        
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            return stmt.executeUpdate();
        } catch (Exception e) {
            return -1;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                return -2;
            }
        }
    }
}
