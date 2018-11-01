package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.json.GsonJsonParser;

import com.example.CryptoUtils;
import com.example.dto.ApacBaseDTO;
import com.google.gson.Gson;
import  java.util.function.Function;
import java.util.ArrayList;

public class DataAccessHelper<T> {
	private String Query ="";
	private DataSource DataSource= null;
	private ArrayList<Object> Parameters=new ArrayList<>();
	public DataAccessHelper(DataSource dataSource)
	{
		DataSource = dataSource;
	}
	public DataAccessHelper<T> PrepararConsulta(String query)
	{
		 this.Query = query;
		 return this; 
	}
	
	public  <P> DataAccessHelper<T> SetParameter(P paramValue)
	{
		if(Parameters == null) Parameters = new ArrayList<>();
		Parameters.add(paramValue);
		return this;
	}
	
	
	public ArrayList<T> ResultSet(Function<ResultSet,T> parser)
	{
		return this.ResultSet(parser, false);
	}
	
	public T First(Function<ResultSet,T> parser)
	{
		ArrayList<T> resultSet = this.ResultSet(parser, true);
		if(resultSet != null  && resultSet.size()>0)
			return resultSet.get(0);
		else
			return null;
	}
	
	public boolean Execute()
	{
		Connection connection = null;
        PreparedStatement stmt = null;
        boolean result;
        try {
        	
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(Query);
            for(int i =0; i<Parameters.size(); i++) {
            	stmt.setObject(i + 1,Parameters.get(i));
        	}
            int iresult = stmt.executeUpdate();
            result = iresult>0;
            connection.commit();
        } catch (Exception e) {
        	try {
	        	if (connection != null) {
	            	connection.rollback();
	            }
        	} catch(Exception ex) {}
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                return false;
            }
        }
        return result;
	}
	
	private ArrayList<T> ResultSet(Function<ResultSet,T> parser, boolean isFirst)
	{
		ArrayList<T> resultSet = new ArrayList<T>();
		Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ApacBaseDTO dto = new ApacBaseDTO();

        try {
            connection = DataSource.getConnection();
            stmt = connection.prepareStatement(Query);
            for(int i =0; i<Parameters.size(); i++) {
            	stmt.setString(i + 1,Parameters.get(i).toString());
        	}
            rs = stmt.executeQuery();
            while (rs.next()) {
            	resultSet.add(parser.apply(rs));
            	if(isFirst)
            		break;
            }
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
        return resultSet;
	}


	
}
