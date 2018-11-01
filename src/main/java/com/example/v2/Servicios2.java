/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.v2;

import com.example.Consts;
import com.example.CryptoUtils;
import com.example.Main;
import java.lang.reflect.Type;

import com.example.dao.AlumnoDAO;
import com.example.dao.AlumnoInput;
import com.example.dao.AlumnoXCentroDAO;
import com.example.dao.ApacBaseDAO;
import com.example.dao.DataAccessHelper;
import com.example.dao.DataLlaves;
import com.example.dao.DataQueries;
import com.example.dao.EvaluadoresDAO;
import com.example.dao.UsuarioInput;
import com.example.dto.AlumnoDTO;
import com.example.dto.AlumnoXCentroDTO;
import com.example.dto.ApacBaseDTO;
import com.example.dto.EvaluadorDTO;
import com.example.dto.UsuarioDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Gad
 */
@Controller
@RequestMapping(value = Main.API_V2, produces = MediaType.APPLICATION_JSON_VALUE)
public class Servicios2 {
	public static final String ENDPOINT_AUTH = "autorizar";
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

    @RequestMapping(value = ENDPOINT_AUTH, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<UsuarioDTO> postAuth(@RequestBody UsuarioInput usuario ) {

        
        
        UsuarioDTO response = new DataAccessHelper<UsuarioDTO>(dataSource)
        							.PrepararConsulta(DataQueries.GET_INFORMACION_BASE)
        							.SetParameter(DataLlaves.Usuarios)
        							.First((rs)->validarUsuarios(rs, usuario));
        
        if (response != null) {
            return new ResponseEntity<UsuarioDTO>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<AlumnoDTO>> getAlumnos() {

    	AlumnoDAO dao = new AlumnoDAO(dataSource);
        List<AlumnoDTO> response= dao.getAlumnos();
        
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<AlumnoDTO> setAlumnos(@RequestBody AlumnoInput alumnoInput) throws Exception {

    	
    	AlumnoDTO alumno= new AlumnoDTO();
    	AlumnoXCentroDTO alumnoXCentro = new AlumnoXCentroDTO();
    	
    	alumno.setIdAlumno(alumnoInput.getIdAlumno());
    	alumno.setEstadoAlumno(alumnoInput.getEstadoAlumno());
    	alumno.setIdDiagnostico(alumnoInput.getIdDiagnostico());
    	alumno.setNombreAlumno(alumnoInput.getIdAlumno());
    	alumno.setApellidoPaternoAlumno(alumnoInput.getApellidoPaternoAlumno());
    	alumno.setApellidoMaternoAlumno(alumnoInput.getApellidoMaternoAlumno());
    	alumno.setFechaNacimientoAlumno(alumnoInput.getFechaNacimientoAlumno());
    	
    	alumnoXCentro.setIdAlumno(alumno.getIdAlumno());
    	alumnoXCentro.setIdCentro(alumnoInput.getIdCentro());
    	alumnoXCentro.setFechaIngresoAlumnoCentro(Integer.parseInt(Consts.FORMATO_FECHA_DEFAULT.format(new Date())));
    	alumnoXCentro.setFechaEgresoAlumnoCentro(Consts.FECHA_EGRESO_INDEFINIDA);
    	
    	AlumnoDAO dao = new AlumnoDAO(dataSource);    	
        AlumnoDTO response= dao.crearAlumno(alumno);
        
        AlumnoXCentroDAO daoCentro = new AlumnoXCentroDAO(dataSource);
    	AlumnoXCentroDTO responseCentro = daoCentro.crearAlumnoXCentro(alumnoXCentro);
    	
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        
                
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    
    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.PUT)
    @ResponseBody
    public HttpEntity<AlumnoDTO> updateAlumnos(@RequestBody AlumnoDTO alumno) throws Exception {

    	AlumnoDAO dao = new AlumnoDAO(dataSource);
        AlumnoDTO response= dao.actualizarAlumno(alumno);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        
                
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    @RequestMapping(value = ENDPOINT_ALUMNOS, method = RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<AlumnoDTO> deleteAlumnos(@RequestBody AlumnoDTO alumno) throws Exception {

    	AlumnoDAO dao = new AlumnoDAO(dataSource);
        AlumnoDTO response= dao.eliminarAlumno(alumno);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        
                
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    
    
    @RequestMapping(value = ENDPOINT_ALUMNOS_X_CENTRO, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<AlumnoXCentroDTO>> getAlumnosXCentro() {

        AlumnoXCentroDAO dao = new AlumnoXCentroDAO(dataSource);
        List<AlumnoXCentroDTO> response = dao.getAlumnoXCentro();
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    
    @RequestMapping(value = ENDPOINT_ALUMNOS_X_CENTRO, method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<AlumnoXCentroDTO> setAlumnosXCentro(@RequestBody AlumnoXCentroDTO valor) {

    	AlumnoXCentroDAO dao = new AlumnoXCentroDAO(dataSource);
    	AlumnoXCentroDTO response= dao.crearAlumnoXCentro(valor);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        
                
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    
    @RequestMapping(value = ENDPOINT_EVALUADORES, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<EvaluadorDTO>> getEvaluadores() {

        
        EvaluadoresDAO dao = new EvaluadoresDAO(dataSource);
        List<EvaluadorDTO> response= dao.getEvaluadores();
		
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    
    private UsuarioDTO validarUsuarios(ResultSet rs, UsuarioInput usuario) 
    {
    	UsuarioDTO userDto = null;
    	try {
    		String json = CryptoUtils.Decrypt(rs.getString("valor"));
    		UsuarioDTO[] list  = new Gson().fromJson(json, UsuarioDTO[].class);
    		for (UsuarioDTO u : list) {
				if( u.Validate(usuario)) {
					userDto= u;
					userDto.SetPassword(null); //blank password!
					break;
				}
			}
    	}
    	catch(SQLException ex) {}
        
    	return userDto;
           
    }
    
    
    
    
  /*  
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
*/    
    
    

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
    
    
    
}
