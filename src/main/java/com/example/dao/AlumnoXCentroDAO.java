package com.example.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.example.Consts;
import com.example.dto.AlumnoDTO;
import com.example.dto.AlumnoXCentroDTO;
import com.example.dto.ApacBaseDTO;
import com.example.dto.EvaluadorDTO;

public class AlumnoXCentroDAO {
	private ApacBaseDAO apacBaseDAO;
	private DataSource dataSource;
	private String Llave = DataLlaves.AlumnoXCentro;
	
	public AlumnoXCentroDAO(DataSource dataSource)
	{
		apacBaseDAO= new ApacBaseDAO(dataSource);
		this.dataSource = dataSource;
	}
	
	public List<AlumnoXCentroDTO> getAlumnoXCentro() {
		ApacBaseDTO apac_data = apacBaseDAO.obtenerBaseInfo(Llave);
		List<AlumnoXCentroDTO> response= ApacBaseDAO.DecriptarDataSet(apac_data.getValor(), AlumnoXCentroDTO[].class);
		return response;
	}
	
	public AlumnoXCentroDTO crearAlumnoXCentro(AlumnoXCentroDTO valor) {
		List<AlumnoXCentroDTO> alumnoxcentro = getAlumnoXCentro();
		//poner con fecha de salida los centros anteriores
		alumnoxcentro.stream()
			.filter(a->a.idAlumno.equals(valor.idAlumno) && a.getFechaEgresoAlumnoCentro() == Consts.FECHA_EGRESO_INDEFINIDA)
			.forEach(a-> a.setFechaEgresoAlumnoCentro(Integer.parseInt(Consts.FORMATO_FECHA_DEFAULT.format(new Date()))));
		
		alumnoxcentro.add(valor);
		ApacBaseDTO apac_data = ApacBaseDAO.EncriptarDataSet(Llave, alumnoxcentro);
		if(apacBaseDAO.actualizarBaseInfo(apac_data))		
			return valor;
		else
			return null;
	}
}
