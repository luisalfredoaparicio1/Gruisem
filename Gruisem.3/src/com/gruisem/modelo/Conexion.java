package com.gruisem.modelo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gruisem.modelo.ParametrosConexion;

public class Conexion {
	private static Connection unicaConexion ;
	private static Statement un_st=null;
	private static DatabaseMetaData dbmd;
	public String un_sql;
	public ResultSet resultado=null;

	private Conexion(){

	}
	public static Connection getInstance() throws SQLException, ClassNotFoundException{
		if(unicaConexion==null){
			ParametrosConexion op = new ParametrosConexion();
			op.asignarParametros();
			System.out.println("jdbc:postgresql://"+op.getIp()+":"+op.getPuerto()+"/"+op.getBaseDatos()+","+op.getUsuario()+","+op.getContrasenia());
			Class.forName("org.postgresql.Driver");
			try{
				unicaConexion=DriverManager.getConnection("jdbc:postgresql://"+op.getIp()+":"+op.getPuerto()+"/"+op.getBaseDatos()+"",""+op.getUsuario()+"",""+op.getContrasenia()+"");
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}


			dbmd=unicaConexion.getMetaData();
			un_st=unicaConexion.createStatement();
			System.out.println("si conecta");
			return unicaConexion;

		}else{
			System.out.println("si conecta");
			return unicaConexion;

		}

	}

	public static void desconectar(){
        try{

            unicaConexion.close();
            System.out.println("desconectado");
        }catch(Exception ex){
            ex.getMessage();
        }
    }
}
