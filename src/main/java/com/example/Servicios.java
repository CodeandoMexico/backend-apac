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

    public static final String ENDPOINT = "usuarios";

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getUsers() {

//        ApacBaseDTO dto = new ApacBaseDTO();
//        dto.setLlave("llave-ñlajsdflñkjaskdljf");
//        dto.setValor("valor-asdlkfjqowijer");
//        dto.setDigestion("digestion-alksdf8wro3");
//        dto.setUltimaActualizacion(System.currentTimeMillis());
//        
//        Gson gson = new Gson();
//        return new ResponseEntity<String>(gson.toJson(dto), HttpStatus.OK);
        String query = "select \n"
                + "	b.llave,\n"
                + "	b.valor,\n"
                + "	b.digestion,\n"
                + "	b.ultima_actualizacion\n"
                + "	from apac_schema.informacion_base b \n"
                + "	where b.llave = 'heNFkdTzSGbKsseIX4bBB33tZGWFgmyRDlRu2DnCRrRMHxepY0H7liUJmc4I25yFcfThuq8Os59QjUwaQCDAkQ==';";
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
            return new ResponseEntity<String>(gson.toJson(dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
