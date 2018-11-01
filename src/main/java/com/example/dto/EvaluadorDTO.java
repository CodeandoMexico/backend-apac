package com.example.dto;


public class EvaluadorDTO {
	
 public int idEvaluador;
 public String nombreEvaluador;
 public String apellidoPaternoEvaluador;
 public String apellidoMaternoEvaluador;
 
 public int getIdEvaluador() {
  return this.idEvaluador;
 }
 public void setIdEvaluador(int idEvaluador) {
  this.idEvaluador = idEvaluador;
 }

 public String getNombreEvaluador() {
  return this.nombreEvaluador;
 }
 public void setNombreEvaluador(String nombreEvaluador) {
  this.nombreEvaluador = nombreEvaluador;
 }

 public String getApellidoPaternoEvaluador() {
  return this.apellidoPaternoEvaluador;
 }
 public void setAapellidoPaternoEvaluador(String apellidoPaternoEvaluador) {
  this.apellidoPaternoEvaluador = apellidoPaternoEvaluador;
 }

 public String getApellidoMaternoEvaluador() {
  return this.apellidoMaternoEvaluador;
 }
 public void setApellidoMaternoEvaluador(String apellidoMaternoEvaluador) {
  this.apellidoMaternoEvaluador = apellidoMaternoEvaluador;
 }

}