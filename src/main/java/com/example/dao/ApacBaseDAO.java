package com.example.dao;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.CryptoUtils;
import com.example.dto.ApacBaseDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApacBaseDAO {

    private DataSource dataSource;
    
	public ApacBaseDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ApacBaseDTO obtenerBaseInfo(String llave) {
		ApacBaseDTO apac_data = new DataAccessHelper<ApacBaseDTO>(dataSource)
				.PrepararConsulta(DataQueries.GET_INFORMACION_BASE)
				.SetParameter(llave)
				.First(rs-> mapeoBaseDTO(rs));
		
		return apac_data;
	}
	
	public boolean actualizarBaseInfo(ApacBaseDTO valor) {
		boolean result= new DataAccessHelper<ApacBaseDTO>(dataSource)
				.PrepararConsulta(DataQueries.UPD_INFORMACION_BASE)				
				.SetParameter(valor.getValor())
				.SetParameter(valor.getDigestion())
				.SetParameter(valor.getLlave())
				.Execute();
		
		return result;
		
	}
	
	public static  <T> List<T> DecriptarDataSet(String valor, Class<T[]> clazz) 
    {
    	T[] data = null;
    	
    	//Type listType = new TypeToken<ArrayList<>>(){}.getType();
    	try {
    		String json = CryptoUtils.Decrypt(valor);
    		data  = new Gson().fromJson(json, clazz);
    	}
    	catch(Exception ex) {}
    	return new ArrayList<>(Arrays.asList(data));
           
    }
	
	public static <T> ApacBaseDTO EncriptarDataSet(String llave,T valor) {
		Type listType = new TypeToken<T>(){}.getType();		
		String json = new Gson().toJson(valor, listType);
		String valorEncrypted = CryptoUtils.Encrypt(json);
		String digestion = CryptoUtils.GenerarDigestion(json);
		ApacBaseDTO apacBase =new ApacBaseDTO();
		apacBase.setLlave(llave);
		apacBase.setValor(valorEncrypted);
		apacBase.setDigestion(digestion);
		apacBase.setUltimaActualizacion(Instant.now().toEpochMilli());
		return apacBase;
		
	}
	
	private ApacBaseDTO mapeoBaseDTO(ResultSet rs) {
    	
    	ApacBaseDTO data = null;
    	try {
    		data = new ApacBaseDTO();
    		data.setLlave(rs.getString("llave"));
    		data.setDigestion(rs.getString("digestion"));
    		data.setUltimaActualizacion(rs.getLong("ultima_actualizacion"));
    		data.setValor(rs.getString("valor"));
    		
    	}
    	catch(SQLException ex) {}
        
    	return data;
    }
}
