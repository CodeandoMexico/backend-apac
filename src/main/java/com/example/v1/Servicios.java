/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.v1;

import com.example.Main;
import com.example.dto.ApacBaseDTO;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
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
    public static final String ENDPOINT_EVALUACIONES_REGISTRO = "evaluaciones_reg";
    public static final String ENDPOINT_EVALUACIONES_CONSULTA_ID = "evaluaciones_con";
    public static final int CONSULTA_EVALUACIONES_POR_ALUMNO = 1;
    public static final int CONSULTA_EVALUACIONES_POR_EVALUADOR = 2;
    public static final int CONSULTA_EVALUACIONES_POR_PERIODO = 3;
    

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
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    @RequestMapping(value = ENDPOINT_USUARIOS, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> setUsers(@RequestParam("v") String usuarios, @RequestParam("d") String digestion) {

        ApacBaseDTO dto = new ApacBaseDTO();
        String llave = "heNFkdTzSGbKsseIX4bBB33tZGWFgmyRDlRu2DnCRrRMHxepY0H7liUJmc4I25yFcfThuq8Os59QjUwaQCDAkQ==";
        
        if (usuarios != null && !usuarios.isEmpty() && digestion != null && !digestion.isEmpty()) {

            dto.setLlave(llave);
            dto.setValor(usuarios);
            dto.setDigestion(digestion);
            if (actualizaBDServicio(dto) > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>(new Gson().toJson(dto),HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getAlumnos() {

        String llave = "DJGK22ya891zn3I3wFE1KpJacWmiTL+NhKmi7HWgmDkh4C7pmdmFp98vV1ruA+55tdicbvJFQuqJ33IFo1eqyw==";
        String response = consultaBDServicio(llave);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> setAlumnos(@RequestParam("v") String alumnos, @RequestParam("d") String digestion) {

        ApacBaseDTO dto = new ApacBaseDTO();
        
        String llave = "DJGK22ya891zn3I3wFE1KpJacWmiTL+NhKmi7HWgmDkh4C7pmdmFp98vV1ruA+55tdicbvJFQuqJ33IFo1eqyw==";
        
        if (alumnos != null && !alumnos.isEmpty() && digestion != null && !digestion.isEmpty()) {

            dto.setLlave(llave);
            dto.setValor(alumnos);
            dto.setDigestion(digestion);
            if (actualizaBDServicio(dto) > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = ENDPOINT_ALUMNOS_X_CENTRO, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getAlumnosXCentro() {

        String llave = "P2iExqhMcOepEZa5vUKQrqA62DPUlmhOxQhfPjXLMEJ3KNq+dGMmQm0EaIJZ4xmVZPNVHONmYbmPSxJgxHvaWw==";
        String response = consultaBDServicio(llave);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    @RequestMapping(value = ENDPOINT_ALUMNOS_X_CENTRO, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> setAlumnosXCentro(@RequestParam("v") String alumnosXcentro, @RequestParam("d") String digestion) {

        ApacBaseDTO dto = new ApacBaseDTO();
        
        String llave = "P2iExqhMcOepEZa5vUKQrqA62DPUlmhOxQhfPjXLMEJ3KNq+dGMmQm0EaIJZ4xmVZPNVHONmYbmPSxJgxHvaWw==";
        
        if (alumnosXcentro != null && !alumnosXcentro.isEmpty() && digestion != null && !digestion.isEmpty()) {

            dto.setLlave(llave);
            dto.setValor(alumnosXcentro);
            dto.setDigestion(digestion);
            if (actualizaBDServicio(dto) > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = ENDPOINT_EVALUADORES, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<String> getEvaluadores() {

        String llave = "qzIn2O4fFro4SrquFyiXiRPCes9KqlmNjtFZJzwjgcvXhe6nkfvT+FL81ozGg0YSruFMdhPUjmNDP7DIMbSf5A==";
        String response = consultaBDServicio(llave);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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

    @RequestMapping(value = ENDPOINT_EVALUACIONES_REGISTRO, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> setEvaluaciones(@RequestParam("k") String idEvaluacion, @RequestParam("v") String evaluacion, @RequestParam("d") String digestion, @RequestParam("x") String adicional) {
        ApacBaseDTO dto = new ApacBaseDTO();
        if (idEvaluacion != null && !idEvaluacion.isEmpty()
                && evaluacion != null && !evaluacion.isEmpty()
                && digestion != null && !digestion.isEmpty()
                && adicional != null && !adicional.isEmpty()) {

            dto.setLlave(idEvaluacion);
            dto.setValor(evaluacion);
            dto.setDigestion(digestion);

            if (registraEvaluacionBD(dto, adicional) > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Gson().toJson(dto), HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = ENDPOINT_EVALUACIONES_CONSULTA_ID, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> getEvaluacion(@RequestParam("k") String tipoConsulta, @RequestParam("v") String evaluacion, @RequestParam("d") String digestion) {
        if (tipoConsulta != null
                && (tipoConsulta.equals(String.valueOf(CONSULTA_EVALUACIONES_POR_ALUMNO))
                || tipoConsulta.equals(String.valueOf(CONSULTA_EVALUACIONES_POR_ALUMNO))
                || tipoConsulta.equals(String.valueOf(CONSULTA_EVALUACIONES_POR_ALUMNO)))
                && evaluacion != null && !evaluacion.isEmpty()
                && digestion != null && !digestion.isEmpty()
                && evaluacion.equals(digestion)) {
            String respuesta = consultaEvaluacionBD(Integer.valueOf(tipoConsulta), evaluacion);
            if (respuesta != null) {
                return new ResponseEntity<>(respuesta,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(tipoConsulta+"-"+evaluacion+"-"+digestion, HttpStatus.BAD_REQUEST);

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

    private int registraEvaluacionBD(ApacBaseDTO dto, String adicional) {

        String query = "insert into apac_schema.informacion_eval (\n"
                + "	llave,\n"
                + "	valor,\n"
                + "	digestion,\n"
                + "	estampa_generacion,\n"
                + "	detalle\n"
                + "	)\n"
                + "	values (\n"
                + "	'" + dto.getLlave() + "',\n"
                + "	'" + dto.getValor() + "',\n"
                + "	'" + dto.getDigestion() + "',\n"
                + "       (extract(epoch from now() at time zone 'UTC')*1000)::bigint,"
                + "	'" + adicional + "'\n"
                + "	);";

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

    private String generaConsulta(int tipoConsulta, String valor) {
        
        String predicadoCampos = "select"
                + " e.llave,"
                + " e.valor,"
                + " e.digestion,"
                + " e.estampa_generacion,"
                + " e.detalle"
                + " from apac_schema.informacion_eval e";
                
        String predicadoClasificacion = " where";
        String predicadoPresentacion = " order by e.estampa_generacion desc;";
        
        switch(tipoConsulta) {
            case CONSULTA_EVALUACIONES_POR_ALUMNO:
                //Se considera el campo detalle con formato idAlumno-idEvaluador
                predicadoClasificacion += " split_part(e.detalle,'-',1) = '" + valor + "'";
                break;
            case CONSULTA_EVALUACIONES_POR_EVALUADOR:
                //Se considera el campo detalle con formato idAlumno-idEvaluador
                predicadoClasificacion += " split_part(e.detalle,'-',2) = '" + valor + "'";
                break;
            case CONSULTA_EVALUACIONES_POR_PERIODO:
                //Se debe tener en el valor la estructura inicio-fin con tiempos Unix(long)
                String [] estampas = valor.split("-");
                if(estampas.length != 2) {
                    //El valor no cuenta con las dos estampas de tiempo
                    return null;
                }
                try {
                    Long.valueOf(estampas[0]);
                    Long.valueOf(estampas[1]);
                } catch (NumberFormatException nfe) {
                    //Los valores no corresponden a tiempos Unix en segundos
                    return null;
                }
                predicadoClasificacion += " e.estampa_generacion between "+ estampas[0] +" and "+ estampas[1];
                break;
            default:
                //Tipo de consulta invalida
                return null;
                //break;
        }
        return predicadoCampos + predicadoClasificacion + predicadoPresentacion;    
    }
    
    private String consultaEvaluacionBD(int tipoConsulta, String valor) {

        Gson gson = new Gson();
        List<ApacBaseDTO> listaResultados = new ArrayList<>();
        ApacBaseDTO dto;

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = generaConsulta(tipoConsulta, valor);
        if(query == null) {
            return null;
        }
        
        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dto = new ApacBaseDTO();
                dto.setLlave(rs.getString("llave"));
                dto.setValor(rs.getString("valor"));
                dto.setDigestion(rs.getString("digestion"));
                dto.setUltimaActualizacion(rs.getLong("estampa_generacion"));
                listaResultados.add(dto);
            }
            return gson.toJson(listaResultados);
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
}
