package com.example.dto;

public class AlumnoXCentroDTO {
	public String idAlumno;
    public String idCentro;
    public int fechaIngresoAlumnoCentro;
    public int fechaEgresoAlumnoCentro;
    
    public String getIdAlumno() {
	  return this.idAlumno;
	 }
	 public void setIdAlumno(String idAlumno) {
	  this.idAlumno = idAlumno;
	 }
	
	 public String getIdCentro() {
	  return this.idCentro;
	 }
	 public void setIdCentro(String idCentro) {
	  this.idCentro = idCentro;
	 }
	 
	 public int getFechaIngresoAlumnoCentro() {
	  return this.fechaIngresoAlumnoCentro;
	 }
	 public void setFechaIngresoAlumnoCentro(int fechaIngresoAlumnoCentro) {
	  this.fechaIngresoAlumnoCentro = fechaIngresoAlumnoCentro;
	 }
		 
	 public int getFechaEgresoAlumnoCentro() {
		 return this.fechaEgresoAlumnoCentro;
	 }
	 public void setFechaEgresoAlumnoCentro(int fechaEgresoAlumnoCentro) {
		 this.fechaEgresoAlumnoCentro = fechaEgresoAlumnoCentro;
	}
}
