package com.gruisem.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;


import com.gruisem.modelo.Usuario;


public class ControladorVentanas {

	private static ControladorVentanas instancia;
	private Stage primaryStage;
	private Stage escenario2,  taskUpdateStage;
	private AnchorPane subcontenedor1;
	private AnchorPane subcontenedor2;
	private AnchorPane contenedorMenu1;
    private Scene escena;



	public static ControladorVentanas getInstancia() {
		if (instancia == null) {
            instancia = new ControladorVentanas();
        }
        return instancia;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

	public void asignarMenu(String ruta, String titulo, Usuario temp) {
        try {
            primaryStage.setUserData(temp);
            FXMLLoader parent = new FXMLLoader(getClass().getResource(ruta));
            contenedorMenu1 = (AnchorPane) parent.load();

            //Se recupera el tamaño de la ventana


            //Se carga la escena con el tamaño de la ventana
            escena = new Scene(contenedorMenu1,926,675);
            primaryStage.setScene(escena);
            primaryStage.setTitle(titulo);
            primaryStage.resizableProperty().setValue(Boolean.FALSE);

            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	public void cerrarMenu(){
		primaryStage.close();
	}

	public void ponerVentanaModal(String src, String titulo){
		try{
			FXMLLoader loader=new FXMLLoader(getClass().getResource(src));
			subcontenedor1=(AnchorPane) loader.load();
			escenario2=new Stage();
			escena=new Scene(subcontenedor1);

			escenario2.setScene(escena);
	        escenario2.setTitle(titulo);
	        escenario2.centerOnScreen();

	        escenario2.initModality(Modality.WINDOW_MODAL);
	        escenario2.initOwner(primaryStage);
	        escenario2.resizableProperty().setValue(Boolean.FALSE);


	        escenario2.show();
		}catch(Exception ex) {
            ex.printStackTrace();

		}
	}

	public void ventanaError(String src, String titulo){
		try{
			FXMLLoader loader=new FXMLLoader(getClass().getResource(src));
			subcontenedor1=(AnchorPane) loader.load();
			escenario2=new Stage();
			escena=new Scene(subcontenedor1);

			escenario2.setScene(escena);
	        escenario2.setTitle(titulo);
	        escenario2.centerOnScreen();
	        escenario2.initModality(Modality.WINDOW_MODAL);
	        escenario2.initOwner(primaryStage);
	        escenario2.resizableProperty().setValue(Boolean.FALSE);
	        escenario2.initStyle(StageStyle.UNDECORATED);
	        escenario2.centerOnScreen();

	        escenario2.show();
		}catch(Exception ex) {
            ex.printStackTrace();

		}
	}

	public Stage getPrimaryStage() {
	        return primaryStage;
	}

	public void cerrarAcceso(){
	        escenario2.close();
	}

    public void ponerEnVis(String ru, BorderPane bp) throws IOException {
			FXMLLoader ven= new FXMLLoader(getClass().getResource(ru));
			subcontenedor2=(AnchorPane) ven.load();

			bp.setCenter(subcontenedor2);


	}

}
