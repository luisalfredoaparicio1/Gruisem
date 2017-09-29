package com.gruisem.modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clientes {
	private int id;
	private String nombre, aPaterno, aMaterno, email, telefono, calle, numero, colonia, ciudad, cp;
	private boolean estatus;
	private Conexion conectar;
	private PreparedStatement comando;
	private ObservableList<Clientes> lista;
	//private ControladorErrores ce;

	public Clientes(){
		this.id=0;
		this.nombre="";
		this.aPaterno="";
		this.aPaterno="";
		this.email="";
		this.telefono="";
		this.calle="";
		this.numero="";
		this.colonia="";
		this.ciudad="";
		this.cp="";
		//this.conectar=new Conexion();
		this.lista=FXCollections.observableArrayList();
		//this.ce=new ControladorErrores();
	}

	//getters

	public int getIdCliente(){
		return id;
	}
	public String getNombre(){
		return nombre;
	}
	public String getAPaterno(){
		return aPaterno;
	}
	public String getAMaterno(){
		return aMaterno;
	}
	public String getEmail(){
		return email;
	}
	public boolean getEstatus(){
		return estatus;
	}
	public String getTelefono(){
		return telefono;
	}
	public String getCalle(){
		return calle;
	}
	public String getNumero(){
		return numero;
	}
	public String getColonia(){
		return colonia;
	}
	public String getCiudad(){
		return ciudad;
	}
	public String getCodigoP(){
		return cp;
	}

	//setters

	public void setIdCliente(int id){
		this.id=id;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setAPaterno(String aPaterno){
		this.aPaterno=aPaterno;
	}
	public void setAMaterno(String aMaterno){
		this.aMaterno=aMaterno;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public void setEstatus(boolean estatus){
		this.estatus=estatus;
	}
	public void setTelefono(String telefono){
		this.telefono=telefono;
	}
	public void setCalle(String calle){
		this.calle=calle;
	}
	public void setNumero(String numero){
		this.numero=numero;
	}
	public void setColonia(String colonia){
		this.colonia=colonia;
	}
	public void setCiudad(String ciudad){
		this.ciudad=ciudad;
	}
	public void setCodigoP(String cp){
		this.cp=cp;
	}

	//Operaciones dentro de la bd

	public boolean insertar(){
		try{
			String sql="";

				sql="insert into clientes values(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, true)";
				comando=Conexion.getInstance().prepareStatement(sql);
				comando.setString(1, this.nombre);
				comando.setString(2, this.aPaterno);
				comando.setString(3, this.aMaterno);
				comando.setString(4, this.telefono);
				comando.setString(5, this.email);
				comando.setString(6, this.calle);
				comando.setString(7, this.numero);
				comando.setString(8, this.colonia);
				comando.setString(9, this.ciudad);
				comando.setString(10, this.cp);
				comando.execute();
				return true;
		}catch (Exception e) {
			//ce.bitacora(e.getMessage(), "DAOClientes");
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminar(){
		try {
 			String sql="update clientes set estatus = false where id=?";
 			comando=Conexion.getInstance().prepareStatement(sql);
 			comando.setInt(1, this.id);
 			comando.execute();
 			return true;

 		}
		catch (Exception e) {
 			//ce.bitacora(e.getMessage(), "DAOClientes");
 			e.printStackTrace();
 			return false;
 		}
	}

	public boolean editar(){
		String sql="";
		try {
 			sql="update clientes set nombre=?, apaterno=?, amaterno=?, telefono=?, email=?, calle=?, numero=?, colonia=?, ciudad=?, cp=? where id=?";
 			comando=Conexion.getInstance().prepareStatement(sql);
 			comando.setString(1, this.nombre);
 			comando.setString(2, this.aPaterno);
 			comando.setString(3, this.aMaterno);
 			comando.setString(4, this.telefono);
 			comando.setString(5, this.email);
 			comando.setString(6, this.calle);
 			comando.setString(7, this.numero);
 			comando.setString(8, this.colonia);
 			comando.setString(9, this.ciudad);
 			comando.setString(10, this.cp);
 			comando.setInt(11, this.id);
 			comando.execute();
 			return true;
 		}
		catch (Exception e) {
 			//ce.bitacora(e.getMessage(), "DAOClientes");
 			e.printStackTrace();
 			return false;
 		}
	}

	public ObservableList<Clientes> consultar(String consulta){
   		ResultSet rs = null;
   		try {
   			comando=Conexion.getInstance().prepareStatement(consulta);
   	  		rs =  comando.executeQuery();
   	  		while(rs.next()){
   	  			Clientes l = new Clientes();
   	  			l.setIdCliente(rs.getInt("id"));
   	  			l.setNombre(rs.getString("nombre"));
   	  			l.setAPaterno(rs.getString("apaterno"));
   	  			l.setAMaterno(rs.getString("amaterno"));
   	  			l.setTelefono(rs.getString("telefono"));
   	  			l.setEmail(rs.getString("email"));
   	  			l.setCalle(rs.getString("calle"));
   	  			l.setNumero(rs.getString("numero"));
   	  			l.setColonia(rs.getString("colonia"));
   	  			l.setCiudad(rs.getString("ciudad"));
   	  			l.setCodigoP(rs.getString("cp"));
   	  			lista.add(l);
   	  		}
   		}
   		catch (Exception ex) {
   			//ce.bitacora(ex.getMessage(), "DAOClientes");
   			ex.printStackTrace();
   		}
   		return lista;
   	}
	public boolean reactivar(){
 		try{
 			String sql = "update clientes set estatus=true where id=?";
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
	public boolean consultarCorreo(String consulta) {
		ResultSet rs = null;
		boolean confirmar=false;
		int numeroDeCorreos=0;
   		try {
   			comando=Conexion.getInstance().prepareStatement(consulta);
   			//System.out.println("lo que lleva la consulta: "+consulta);
   	  		rs =  comando.executeQuery();
   	  		while(rs.next()){
   	  			numeroDeCorreos=rs.getInt("count");
 			    //System.out.println("lo que lleva la numeroDeCorreos: "+numeroDeCorreos);
 			}
	  		if(numeroDeCorreos>0){
	  		    confirmar=true;
	  		}
	  		else{
	  			confirmar=false;
	  		}

   		} catch (Exception ex) {
   			//ce.bitacora(ex.getMessage(), "DAOClientes");
   		}
   		return confirmar;

	}
}