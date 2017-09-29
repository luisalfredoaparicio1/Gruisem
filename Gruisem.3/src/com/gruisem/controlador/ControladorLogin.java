package com.gruisem.controlador;


import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;

import com.gruisem.modelo.Usuario;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ControladorLogin implements Initializable {

	@FXML TextField usu, lbla;
    @FXML PasswordField con;
    private ControladorVentanas cv;
    private Usuario usuarios;
    private String user;
    private ControladorVentanas error;
    @FXML GridPane gp;

    public ControladorLogin(){
    	error = ControladorVentanas.getInstancia();
    	this.usuarios=new Usuario();
        cv = ControladorVentanas.getInstancia();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	usu.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9]{0,20}") || newValue.length()>20)
	            ((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior
	        else
	            ((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
    	});
    }
	@FXML public void salir(){
		System.exit(0);
	}

    @FXML public void clickValidar(){
        try{
            if(usu.getText().trim().isEmpty() || con.getText().trim().isEmpty()){
            	 error.ventanaError("../vista/notificacion.fxml", "Vacio");
            }
            else{
            	 usuarios.setNombre(usu.getText());
            	 usuarios.setContrasena(con.getText());
            	 Usuario temp = usuarios.validarUsuario();
            	 user=usu.getText();
            	 if(temp!=null){

                   cv.cerrarAcceso();
                   cv.asignarMenu("../vista/menu3.fxml","Bienvenido " + temp.getNombre().toUpperCase(), temp);

            	 }
                else{
                	Alert alert = new Alert(AlertType.ERROR);
        	    	alert.setTitle("Error");
        	    	alert.setHeaderText(null);
        	    	alert.setContentText("Datos de usuario incorrectos.");
        	    	alert.showAndWait();
                    usu.clear();
                    con.clear();
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void start(Stage primaryStage) {
    AnchorPane root = new AnchorPane();
    ProgressBar pbRed = new ProgressBar(0.4);
    ProgressBar pbGreen = new ProgressBar(0.6);
    pbRed.setLayoutY(10);
    pbGreen.setLayoutY(30);

    pbRed.setStyle("-fx-accent: red;");       // line (1)
    pbGreen.setStyle("-fx-accent: green;");   // line (2)

    root.getChildren().addAll(pbRed, pbGreen);
    Scene scene = new Scene(root, 150, 50);
    primaryStage.setTitle("Hello World!");
    primaryStage.setScene(scene);
    primaryStage.show();
    }
}

