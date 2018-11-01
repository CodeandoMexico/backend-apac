package com.example.dao;

import java.util.List;

import javax.sql.DataSource;

import com.example.dto.AlumnoDTO;
import com.example.dto.ApacBaseDTO;
import com.example.dto.EvaluadorDTO;

public class EvaluadoresDAO {
	private ApacBaseDAO apacBaseDAO;
	private DataSource dataSource;
	private String Llave = DataLlaves.Evaluadores;
	
	public EvaluadoresDAO(DataSource dataSource)
	{
		apacBaseDAO= new ApacBaseDAO(dataSource);
		this.dataSource = dataSource;
	}
	
	public List<EvaluadorDTO> getEvaluadores() {
		ApacBaseDTO apac_data = apacBaseDAO.obtenerBaseInfo(Llave);
		List<EvaluadorDTO> response= ApacBaseDAO.DecriptarDataSet(apac_data.getValor(), EvaluadorDTO[].class);
		return response;
	}
	
	
}
