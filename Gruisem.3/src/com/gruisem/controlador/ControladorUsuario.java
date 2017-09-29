package com.gruisem.controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.gruisem.modelo.Conexion;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ControladorUsuario  implements Initializable{
	@FXML private ComboBox nivelUsu;
	@FXML private PasswordField passUsu;
	@FXML private TextField nomUsu, idEmp;
	private Connection conexion;
	private ControladorVentanas error;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nivelUsu.getItems().addAll("Administrador","Invitado");
	}

	public ControladorUsuario(){
		error = ControladorVentanas.getInstancia();
	}

	   @FXML
	   private void agregarUsuario(ActionEvent event) throws ClassNotFoundException {
	       try {
	    	   if(idEmp.getText().trim().isEmpty() || nomUsu.getText().trim().isEmpty()||passUsu.getText().trim().isEmpty()||nivelUsu.getSelectionModel().getSelectedItem()==null){
				   error.ventanaError("../vista/notificacion.fxml", "Vacio");
			   }else{

	    	   conexion = Conexion.getInstance();
	           String sql = "INSERT INTO usuarios "
	                   + " (idempleado, nombre, contrasena, nivel, estatus) "
	                   + " VALUES (?, ?, ?, ?, true)";
	           PreparedStatement estado = conexion.prepareStatement(sql);
	           estado.setInt(1, Integer.parseInt(idEmp.getText()));
	           estado.setString(2, nomUsu.getText());
	           estado.setString(3, passUsu.getText());
	           estado.setString(4, nivelUsu.getValue().toString());

	           int n  = estado.executeUpdate();

	           if (n > 0) {

			            Alert alert = new Alert(AlertType.INFORMATION);
			   			alert.setTitle("Mensaje de información");
			   			alert.setHeaderText(null);
			   			alert.setContentText("¡Usuario agregado con éxito!");
			   			alert.showAndWait();

	           }
	           estado.close();
			   }

	       } catch (SQLException e) {
	           System.out.println("Error " + e);
	       }

	   }

	 @FXML public void cancelar(){
		   idEmp.clear();
		   nomUsu.clear();
		   passUsu.clear();
		   nivelUsu.getSelectionModel().clearSelection();
	   }
	 @FXML private javafx.scene.control.Button closeButton;

	 @FXML
	 private void cerrar(){
	     // get a handle to the stage
	     Stage stage = (Stage) closeButton.getScene().getWindow();
	     // do what you have to do
	     stage.close();
	 }

}
