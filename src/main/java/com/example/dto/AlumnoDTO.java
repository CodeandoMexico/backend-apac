package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlumnoDTO {
 public String idAlumno;
 public int estadoAlumno;
 public int idDiagnostico;
 public String nombreAlumno;
 public String apellidoPaternoAlumno;
 public String apellidoMaternoAlumno;
 public int fechaNacimientoAlumno;

 public String getIdAlumno() {
  return this.idAlumno;
 }
 public void setIdAlumno(String idAlumno) {
  this.idAlumno = idAlumno;
 }

 public int getEstadoAlumno() {
  return this.estadoAlumno;
 }
 public void setEstadoAlumno(int estadoAlumno) {
  this.estadoAlumno = estadoAlumno;
 }

 public int getIdDiagnostico() {
  return this.idDiagnostico;
 }
 public void setIdDiagnostico(int idDiagnostico) {
  this.idDiagnostico = idDiagnostico;
 }

 public String getNombreAlumno() {
  return this.nombreAlumno;
 }
 public void setNombreAlumno(String nombreAlumno) {
  this.nombreAlumno = nombreAlumno;
 }

 public String getApellidoPaternoAlumno() {
  return this.apellidoPaternoAlumno;
 }

 public void setApellidoPaternoAlumno(String apellidoPaternoAlumno) {
  this.apellidoPaternoAlumno = apellidoPaternoAlumno;
 }

 public String getApellidoMaternoAlumno() {
  return this.apellidoMaternoAlumno;
 }
 public void setApellidoMaternoAlumno(String apellidoMaternoAlumno) {
  this.apellidoMaternoAlumno = apellidoMaternoAlumno;
 }
 public int getFechaNacimientoAlumno() {
  return this.fechaNacimientoAlumno;
 }
 public void setFechaNacimientoAlumno(int fechaNacimientoAlumno) {
  this.fechaNacimientoAlumno = fechaNacimientoAlumno;
 }
}