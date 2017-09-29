package com.gruisem.modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import controlador.ControladorErrores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Marcas {
	private int id;
	private static String nombre_mar;
	private boolean estatus;
	private PreparedStatement comando;
	private ObservableList<Marcas> lista;
	private Marcas lis;

	public Marcas(){
		this.id =0;
		this.nombre_mar ="";
		this.lista=FXCollections.observableArrayList();
	}

	public int getId(){
		return id;
	}
	public static String getNombre(){
		return nombre_mar;
	}
	public boolean getEstatus(){
		return estatus;
	}

	public void setId(int id){
		this.id = id;
	}
	public void setNombre(String nombre_mar){
		this.nombre_mar = nombre_mar;
	}
	public void setEstatus(boolean estatus){
		this.estatus=estatus;
	}

	public boolean insertar(){
		try{
			String sql="";
				sql="insert into marcas(id, nombre_mar, estatus) values(default, ?, true)";
				comando=Conexion.getInstance().prepareStatement(sql);
				comando.setString(1, this.nombre_mar);
				comando.execute();
				return true;
			}
		catch (Exception e) {
			//ce.bitacora(e.getMessage(), "DAOMarcas");
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminar(){
		try {

 			String sql="update marcas set estatus=false where id=?";
 			comando=Conexion.getInstance().prepareStatement(sql);
 			comando.setInt(1, this.id);
			comando.execute();
 			return true;
 		} catch (Exception e) {
 			e.printStackTrace();
 			return false;
 		}
	}
	public ObservableList<Marcas> consultar(String consulta){
   		ResultSet rs = null;
   		try {
   			comando = Conexion.getInstance().prepareStatement(consulta);
   	  		rs =  comando.executeQuery();
   	  		while(rs.next()){
   	  			Marcas m = new Marcas();
   	  			m.setId(rs.getInt("id"));
   	  			m.setNombre(rs.getString("nombre_mar"));
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
 			String sql = "update marcas set estatus=true where id=?";
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