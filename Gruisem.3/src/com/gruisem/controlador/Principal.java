package com.gruisem.controlador;

import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application{

	private ControladorVentanas ventanas;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ventanas = ControladorVentanas.getInstancia();
		ventanas.setPrimaryStage(primaryStage);
		ventanas.ponerVentanaModal("../vista/login.fxml", "Inicio de sesión");
	}


}
