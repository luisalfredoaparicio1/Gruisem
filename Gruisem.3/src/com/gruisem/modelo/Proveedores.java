package com.gruisem.modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import controlador.ControladorErrores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Proveedores {
	private int id;
	private boolean estatus;
	private String contacto, telefono, email, calle, numero, colonia, ciudad, cp, razonsocial;


	private PreparedStatement comando;
	private ObservableList<Proveedores> lista;
	private Proveedores lis;


	public Proveedores() {
		this.id=0;
		this.razonsocial="";
		this.contacto="";
		this.telefono="";
		this.email="";
		this.calle="";
		this.numero="";
		this.colonia="";
		this.ciudad="";
		this.cp="";
		this.lista=FXCollections.observableArrayList();
	}

	public int getId(){
		return id;
	}
	public String getContacto(){
		return contacto;
	}
	public String getTelefono(){
		return telefono;
	}
	public String getEmail(){
		return email;
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
	public boolean getEstatus(){
		return estatus;
	}
	public String getRazonSocial(){
		return razonsocial;
	}


	public void setId(int id){
		this.id=id;
	}
	public void setContacto(String contacto){
		this.contacto = contacto;
	}
	public void setTelefono(String telefono){
		this.telefono=telefono;
	}
	public void setEmail(String email){
		this.email=email;
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
	public void setEstatus(boolean estatus){
		this.estatus=estatus;
	}
	public void setRazonSocial(String razonsocial){
		this.razonsocial = razonsocial;
	}


	public boolean insertar(){
		try{
			String sql="";
				sql="insert into proveedores(id,razonsocial, contacto,telefono, email,calle,numero, colonia,ciudad,cp, estatus) values(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, true)";
				comando=Conexion.getInstance().prepareStatement(sql);
				comando.setString(1, this.razonsocial);
				comando.setString(2, this.contacto);
				comando.setString(3, this.telefono);
				comando.setString(4, this.email);
				comando.setString(5, this.calle);
				comando.setString(6, this.numero);
				comando.setString(7, this.colonia);
				comando.setString(8, this.ciudad);
				comando.setString(9, this.cp);
				comando.execute();
				return true;

		}catch (Exception e) {
			//ce.bitacora(e.getMessage(), "DAOProveedores");
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminar(){
		try {
 				String sql="update proveedores set estatus = false where id=?";
 				comando=Conexion.getInstance().prepareStatement(sql);
 				comando.setInt(1, this.id);
 				comando.execute();
 			return true;

 		} catch (Exception e) {
 			//ce.bitacora(e.getMessage(), "DAOProveedores");
 			return false;
 		}
	}

	public boolean editar(){
		String sql="";
		try {
 				sql="update proveedores set razonsocial=?, contacto=?, telefono=?, email=?, calle=?, numero=?, colonia=?, ciudad=?, cp=? where id=?";
 				comando=Conexion.getInstance().prepareStatement(sql);
 				comando.setString(1, this.razonsocial);
 				comando.setString(2, this.contacto);
 				comando.setString(3, this.telefono);
 				comando.setString(4, this.email);
 				comando.setString(5, this.calle);
 				comando.setString(6, this.numero);
 				comando.setString(7, this.colonia);
 				comando.setString(8, this.ciudad);
 				comando.setString(9, this.cp);
 				comando.setInt(10, this.id);
 				comando.execute();
 				return true;
 		}
		catch (Exception e) {
 			//ce.bitacora(e.getMessage(), "DAOProveedores");
 			return false;
 		}
	}

	public ObservableList<Proveedores> consultar(String consulta){
   		ResultSet rs = null;
   		try {
   			comando = Conexion.getInstance().prepareStatement(consulta);
   	  		rs =  comando.executeQuery();
   	  		while(rs.next()){
   	 			Proveedores l = new Proveedores();
   	  		    l.setId(rs.getInt("id"));
   	  		    l.setRazonSocial(rs.getString("razonsocial"));
   	  			l.setContacto(rs.getString("contacto"));
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
   			//ce.bitacora(ex.getMessage(), "Proveedores");
   		}
 		return lista;
   	}
	public ObservableList<Proveedores> consultarProve(String consulta){
   		ResultSet rs = null;
   		try {
   				comando = Conexion.getInstance().prepareStatement(consulta);
   	  			rs =  comando.executeQuery();
   	  			while(rs.next()){
   	  				Proveedores l = new Proveedores();
   	  				l.setContacto(rs.getString("contacto").toString());
   	  				lista.add(l);   	  			}
   		} catch (Exception ex) {
   			//ce.bitacora(ex.getMessage(), "DAOProveedores");
   		}
 		return lista;
   	}
	@Override public String toString(){
		return contacto;
	}
	public int consultarId(String consulta) {
		ResultSet rs = null;
		int numeroid=0;
   		try {
   			comando = Conexion.getInstance().prepareStatement(consulta);
   			System.out.println("lo que lleva la consulta: "+consulta);
   			rs =  comando.executeQuery();
   			while(rs.next()){
   				numeroid =rs.getInt("id");
	  		}
   		}
   		catch (Exception ex) {
   			//ce.bitacora(ex.getMessage(), "DAOProveedores");
   		}
 		return numeroid;
	}
	public String consultarNombreProveedor(String consulta) {
		ResultSet rs = null;
		String nombrePro="";
   		try {
   			comando = Conexion.getInstance().prepareStatement(consulta);
   			System.out.println("lo que lleva la consulta: "+consulta);
   	  		rs =  comando.executeQuery();
   	  	    while(rs.next()){
   	  	    	nombrePro =rs.getString("contacto");
	  			System.out.println("lo que lleva numeroid: "+nombrePro);
	  		}
   		}
   		catch (Exception ex) {
   			//ce.bitacora(ex.getMessage(), "DAOProveedores");
   		}
 		return nombrePro;
	}
	public boolean consultarCorreo(String consulta) {
		ResultSet rs = null;
		boolean confirmar=false;
		int numeroDeCorreos=0;
   		try {
   			comando = Conexion.getInstance().prepareStatement(consulta);
   			System.out.println("lo que lleva la consulta: "+consulta);
   	  		rs =  comando.executeQuery();
   	  	   while(rs.next()){
   	  		   numeroDeCorreos=rs.getInt("count");
   	  		   System.out.println("lo que lleva la numeroDeCorreos: "+numeroDeCorreos);
	  		}
   	  		if(numeroDeCorreos>0){
   	  	    	confirmar=true;
   	  	    }
   	  		else{
   	  		  	confirmar=false;
   	  		}
   		}
   		catch (Exception ex) {
   			//ce.bitacora(ex.getMessage(), "DAOProveedores");
   		}
 		return confirmar;
	}
	public boolean reactivar(){
 		try{
 			String sql = "update proveedores set estatus=true where id=?";
 			comando=Conexion.getInstance().prepareStatement(sql);
 			comando.setInt(1,this.id);
 			comando.execute();
 			return true;
 		}
 		catch (Exception e){

 			e.printStackTrace();
 			return false;
 		}
 	}

}
