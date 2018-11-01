package com.example.dao;

public abstract class DataQueries {
	public final static String GET_INFORMACION_BASE=  "select \n"
            + "	b.llave,\n"
            + "	b.valor,\n"
            + "	b.digestion,\n"
            + "	b.ultima_actualizacion\n"
            + "	from apac_schema.informacion_base b \n"
            + "	where b.llave = ?;";
	
	public final static String UPD_INFORMACION_BASE=   "update apac_schema.informacion_base b \n"
            + "	set valor = ?, \n"
            + "	digestion = ? ,\n"
            + "	ultima_actualizacion = (extract(epoch from now() at time zone 'UTC')*1000)::bigint \n"
            + "	where b.llave = ?;";
}
