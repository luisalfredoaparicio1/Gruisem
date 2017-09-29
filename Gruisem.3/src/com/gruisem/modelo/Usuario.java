package com.gruisem.modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.gruisem.modelo.Usuario;

public class Usuario {
	private int id;
	private int idempleado;
	private String nombre;
	private String contrasena;
	private String nivel;
	private boolean estatus;
	private PreparedStatement comando;

	public Usuario(){
		this.id=0;
		this.idempleado=0;
		this.nombre = "";
		this.contrasena = "";
		this.nivel = "";
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id=id;
	}

	public int getIdEmpleado() {
		return this.idempleado;
	}

	public void setIdEmpleado(int idempleado) {
		this.idempleado=idempleado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre=nombre;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena=contrasena;
	}

	public String getNivel() {
		return this.nivel;
	}

	public void setNivel(String nivel) {
		this.nivel=nivel;
	}

	public boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus=estatus;
	}



	public Usuario validarUsuario(){
   	 Usuario usuario=null;
   	 ResultSet rs=null;
   	 try{
   			 String sql="select * from usuarios where nombre='"+this.nombre+"' and contrasena='"+this.contrasena+"' and estatus=true";
   			 comando=Conexion.getInstance().prepareStatement(sql);
   			 rs=comando.executeQuery();
   			 while(rs.next()){
   				 usuario=new Usuario();
   				 usuario.nombre=rs.getString("nombre");
   				 usuario.nivel=rs.getString("nivel");
   				 usuario.id=rs.getInt("id");
   			 }

   	 }
   	 catch(Exception e){
   		 e.printStackTrace();
   	 }
   	 return usuario;
    }
}
