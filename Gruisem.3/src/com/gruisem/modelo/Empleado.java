package com.gruisem.modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Empleado {
	private int id;
	private String nombre, aPaterno, aMaterno, sexo, telefono, email, calle, numero, colonia, ciudad;
	private Date fechaNaci;
	private int cp;
	private boolean estatus;

	private PreparedStatement comando;
	private ObservableList<Empleado> lista;


	public Empleado(){
		this.id=0;
		this.nombre ="";
		this.aPaterno = "";
		this.aMaterno = "";
		this.fechaNaci = new Date(0);
		this.sexo= "";
		this.telefono = "";
		this.email = "";
		this.calle = "";
		this.numero="";
		this.colonia = "";
		this.ciudad  ="";
		this.cp = 0;
		this.lista=FXCollections.observableArrayList();
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id=id;
	}

	public String getNombre(){
		return this.nombre;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	public String getAPaterno(){
		return this.aPaterno;
	}
	public void setAPaterno(String apaterno){
		this.aPaterno=apaterno;
	}

	public String getAMaterno(){
		return this.aMaterno;
	}
	public void setAMaterno(String amaterno){
		this.aMaterno=amaterno;
	}

	public Date getFechaNaci(){
		return this.fechaNaci;
	}
	public void setFechaNaci(Date fechanaci){
		this.fechaNaci=fechanaci;
	}

	public String getSexo(){
		return this.sexo;
	}
	public void setSexo(String sexo){
		this.sexo=sexo;
	}

	public String getTelefono(){
		return this.telefono;
	}
	public void setTelefono(String telefono){
		this.telefono=telefono;
	}

	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email=email;
	}

	public String getCalle(){
		return this.calle;
	}
	public void setCalle(String calle){
		this.calle=calle;
	}

	public String getNumero(){
		return this.numero;
	}
	public void setNumero(String numero){
		this.numero=numero;
	}

	public String getColonia(){
		return this.colonia;
	}
	public void setColonia(String colonia){
		this.colonia=colonia;
	}

	public String getCiudad(){
		return this.ciudad;
	}
	public void setCiudad(String ciudad){
		this.ciudad=ciudad;
	}

	public int getCp(){
		return this.cp;
	}
	public void setCp(int cp){
		this.cp=cp;
	}

	public boolean getEstatus(){
		return this.estatus;
	}
	public void setEstatus(boolean estatus){
		this.estatus=estatus;
	}

	public boolean insertar(){
   	 try{
   		 String sql="";
   			 sql="insert into empleados(id, nombre, apaterno, amaterno, fechanaci, sexo, telefono, email, calle, numero, colonia, ciudad, cp, estatus) "
   			 		+ "values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, true)";
   			 comando = Conexion.getInstance().prepareStatement(sql);
   			 System.out.println(this.nombre);
   			 comando.setString(1, this.nombre);
   			 comando.setString(2, this.aPaterno);
   			 comando.setString(3, this.aMaterno);
   			 comando.setDate(4, this.fechaNaci);
   			 comando.setString(5, this.sexo);
   			 comando.setString(6, String.valueOf( this.telefono));
   			 comando.setString(7, this.email);
   			 comando.setString(8, this.calle);
   			 comando.setString(9, this.numero);
   			 comando.setString(10, this.colonia);
   			 comando.setString(11, this.ciudad);
   			 comando.setString(12, String.valueOf(this.cp));
   			 comando.execute();
   			 return true;

   	 }catch(Exception e){
   		 e.printStackTrace();
   		 return false;
   	 }

    }

	public boolean eliminar(){
		try{
			String sql="";
					sql="update empleados set estatus = false where id=?";
			 comando = Conexion.getInstance().prepareStatement(sql);
			 comando.setInt(1, this.id);
			 comando.execute();
			 return true;

		}catch(Exception e){
  		 e.printStackTrace();
  		 return false;
		}


	}

	public boolean editar(){
	   	 try{
	   		 String sql="";
	   			 sql="update empleados set nombre=?, apaterno=?, amaterno=?, fechanaci=?, sexo=?, "
	   			 		+ "telefono=?, email=?, calle=?, numero=?, colonia=?, ciudad=?, cp=?, estatus=true where id=?";

	   			 comando = Conexion.getInstance().prepareStatement(sql);

	   			 comando.setString(1, this.nombre);
	   			 comando.setString(2, this.aPaterno);
	   			 comando.setString(3, this.aMaterno);
	   			 comando.setDate(4, this.fechaNaci);
	   			 comando.setString(5, this.sexo);
	   			 comando.setString(6, String.valueOf( this.telefono));
	   			 comando.setString(7, this.email);
	   			 comando.setString(8, this.calle);
	   			 comando.setString(9, this.numero);
	   			 comando.setString(10, this.colonia);
	   			 comando.setString(11, this.ciudad);
	   			 comando.setString(12, String.valueOf(this.cp));
	   			 comando.setInt(13, this.id);
	   			 comando.execute();
	   			 return true;

	   	 }catch(Exception e){
	   		 e.printStackTrace();
	   		 return false;
	   	 }

	    }

	public ObservableList<Empleado> consultar(String string) {
		ResultSet rs = null;
   		try {

   				comando = Conexion.getInstance().prepareStatement(string);
   	  			rs =  comando.executeQuery();
   	  			while(rs.next()){
   	  				Empleado l = new Empleado();
   	  				l.setId(rs.getInt("id"));
   	  			    l.setNombre(rs.getString("nombre"));
   	  				l.setAPaterno(rs.getString("apaterno"));
   	  				l.setAMaterno(rs.getString("amaterno"));
   	  				l.setFechaNaci(rs.getDate("fechanaci"));
   	  				l.setSexo(rs.getString("sexo"));
   	  				l.setTelefono(rs.getString("telefono"));
   	  				l.setEmail(rs.getString("email"));
   	  				l.setCalle(rs.getString("calle"));
   	  				l.setNumero(rs.getString("numero"));
   	  				l.setColonia(rs.getString("colonia"));
   	  				l.setCiudad(rs.getString("ciudad"));
   	  				l.setCp(Integer.parseInt(rs.getString("cp")));

   	  				lista.add(l);
   	  			}

   		} catch (Exception ex) {
   			ex.printStackTrace();
   		}
 		return lista;
	}
	public boolean reactivar(){
 		try{
 			String sql = "update empleados set estatus=true where id=?";
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
