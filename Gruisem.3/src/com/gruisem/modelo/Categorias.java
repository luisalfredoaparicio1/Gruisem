package com.gruisem.modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Categorias {
	private int id;
	private String nombre_cat;
	private boolean estatus;
	private PreparedStatement comando;
	private ObservableList<Categorias> lista;

	public Categorias(){
		this.id=0;
		this.nombre_cat="";
		this.lista=FXCollections.observableArrayList();
	}

	public int getIdCategorias(){
		return id;
	}
	public String getNombre(){
		return nombre_cat;
	}
	public boolean getEstatus(){
		return estatus;
	}

	public void setIdMarcas(int id){
		this.id = id;
	}
	public void setNombre(String nombre_cat){
		this.nombre_cat = nombre_cat;
	}
	public void setEstatus(boolean estatus){
		this.estatus=estatus;
	}

	public boolean insertar(){
		try{
			String sql="";
			sql="insert into categorias values(default, ?, true)";
			comando=Conexion.getInstance().prepareStatement(sql);
			comando.setString(1, this.nombre_cat);
			comando.execute();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminar(){
		try {
 			String sql="update categorias set estatus = false where id=?";
 			comando=Conexion.getInstance().prepareStatement(sql);
 			comando.setInt(1, this.id);
 			comando.execute();
 			return true;

 		} catch (Exception e) {
 			e.printStackTrace();
 			return false;
 		}
	}

	public boolean editar(){
		String sql="";
		try {
 			sql="update categrias set nombre_cat=?, where id=?";
 			comando=Conexion.getInstance().prepareStatement(sql);
 			comando.setString(1, this.nombre_cat);
			comando.execute();
 			return true;
		}
		catch (Exception e) {
 			e.printStackTrace();
 			return false;
 		}
 	}

	public ObservableList<Categorias> consultar(String consulta){
   		ResultSet rs = null;
   		try {
   			comando=Conexion.getInstance().prepareStatement(consulta);
   	  		rs =  comando.executeQuery();
   	  		while(rs.next()){
   	  			Categorias m = new Categorias();
   	  				m.setIdMarcas(rs.getInt("id"));
   	  				m.setNombre(rs.getString("nombre_cat"));
   	  				m.setEstatus(rs.getBoolean("estatus"));
   	  				lista.add(m);
   	  			}
   		}
   		catch (Exception ex) {
   			ex.printStackTrace();
   		}
   		return lista;
   	}
	public boolean reactivar(){
 		try{
 			String sql = "update categorias set estatus=true where id=?";
 			comando=Conexion.getInstance().prepareStatement(sql);
 			comando.setInt(1,this.id);
 			comando.execute();
 			return true;
 		}
 		catch (Exception e){
 			//ce.bitacora(e.getMessage(), "DAOClientes");
 			e.printStackTrace();
 			return false;
 		}
 	}
}
