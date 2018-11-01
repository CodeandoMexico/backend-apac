package com.example.dto;

import com.example.dao.UsuarioInput;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioDTO {
	
	public String user;
	public String centro;
	public boolean su;
	public String password;
	
	public String getUser() {
		return user;
	}
	
	public void SetUser(String user){
		this.user= user;
	}
	
	public String getCentro() {
		return this.centro;
	}
	
	public void SetCentro(String centro) {
		this.centro = centro;
	}
	
	public boolean GetSu() {
		return this.su;
	}
	
	public void SetSu(boolean su) {
		this.su= su;
	}
	
	
	public String GetPassword() {
		return this.password;
	}
	public void SetPassword(String password) {
		this.password = password;
	}
	
	public boolean Validate(UsuarioInput usuario) {
		return usuario!=null && 
				this.getUser().equals(usuario.getUser()) && 
				this.GetPassword().equals(usuario.GetPassword());
	}
}
