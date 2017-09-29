package com.gruisem.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.gruisem.controlador.ControladorVentanas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControladorNotificacion implements Initializable{
	private ControladorVentanas in;

	public ControladorNotificacion() {
		in = ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {


	}

	@FXML public void clickAceptar(){
		in.cerrarAcceso();
	}

}
