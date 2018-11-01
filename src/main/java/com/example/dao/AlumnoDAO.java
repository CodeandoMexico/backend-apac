package com.example.dao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.example.Consts;
import com.example.CryptoUtils;
import com.example.dto.AlumnoDTO;
import com.example.dto.AlumnoXCentroDTO;
import com.example.dto.ApacBaseDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AlumnoDAO {

	private ApacBaseDAO apacBaseDAO;
	private DataSource dataSource;
	private String Llave = DataLlaves.Alumno;
	
	public AlumnoDAO(DataSource dataSource)
	{
		apacBaseDAO= new ApacBaseDAO(dataSource);
		this.dataSource = dataSource;
	}
	
	public List<AlumnoDTO> getAlumnos() {
		ApacBaseDTO apac_data = apacBaseDAO.obtenerBaseInfo(Llave);
		List<AlumnoDTO> response= ApacBaseDAO.DecriptarDataSet(apac_data.getValor(), AlumnoDTO[].class);
		return response;
	}
	
	public AlumnoDTO crearAlumno(AlumnoDTO nuevoAlumno) throws Exception
	{
		nuevoAlumno.setEstadoAlumno(Consts.ID_ESTADO_ACTIVO);
		
		List<AlumnoDTO> alumnos = getAlumnos();
		
		if(alumnos.stream().anyMatch((a)-> a.idAlumno.equalsIgnoreCase(nuevoAlumno.idAlumno))) {
			throw new Exception("invalid");
		}
		alumnos.add(nuevoAlumno);
		ApacBaseDTO apac_data = ApacBaseDAO.EncriptarDataSet(Llave, alumnos);
		if(apacBaseDAO.actualizarBaseInfo(apac_data))		
			return nuevoAlumno;
		else
			return null;
	}
	
	public AlumnoDTO actualizarAlumno(AlumnoDTO valor) throws Exception
	{
		
		List<AlumnoDTO> alumnos = getAlumnos();
		alumnos.removeIf(a->a.idAlumno.equals(valor.idAlumno));
		alumnos.add(valor);		
		ApacBaseDTO apac_data = ApacBaseDAO.EncriptarDataSet(Llave, alumnos);
		if(apacBaseDAO.actualizarBaseInfo(apac_data))		
			return valor;
		else
			return null;
	}
	
	public AlumnoDTO eliminarAlumno(AlumnoDTO valor) throws Exception
	{
		
		List<AlumnoDTO> alumnos = getAlumnos();
		alumnos.removeIf(a->a.idAlumno.equals(valor.idAlumno));
		ApacBaseDTO apac_data = ApacBaseDAO.EncriptarDataSet(Llave, alumnos);
		if(apacBaseDAO.actualizarBaseInfo(apac_data))		
			return valor;
		else
			return null;
	}

	
}
