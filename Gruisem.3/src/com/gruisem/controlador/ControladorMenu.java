package com.gruisem.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.gruisem.modelo.Conexion;
import com.gruisem.modelo.Usuario;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class ControladorMenu implements Initializable{

	private ControladorVentanas instancia;
	@SuppressWarnings("unused")
	private Usuario usuario;
	private ControladorLogin cl;
	@FXML BorderPane bp;

	@FXML Button btnVentas, btnProductos, btnProveedores, btnClientes;
	@FXML Label lblMensaje, lblHora;

	public ControladorMenu(){
		instancia = ControladorVentanas.getInstancia();
		usuario = new Usuario();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Usuario usuario = (Usuario) instancia.getPrimaryStage().getUserData();
		lblMensaje.setText(usuario.getNombre() + " - " + usuario.getNivel());
		lblHora.setText((new Date()).toString());

		switch (usuario.getNivel()) {

			case "Admin":

				btnClientes.setDisable(false);
				btnProductos.setDisable(false);
				btnVentas.setDisable(false);
				btnProveedores.setDisable(false);
				break;

			case "Invitado":

				btnClientes.setDisable(false);
				btnProductos.setDisable(false);
				btnVentas.setDisable(true);
				btnProveedores.setDisable(false);
				break;

			default:

				btnClientes.setDisable(false);
				btnProductos.setDisable(false);
				btnVentas.setDisable(false);
				btnProveedores.setDisable(false);
				break;

		}
	}
	@FXML public void clickProductos() throws IOException{
		instancia.ponerEnVis("../vista/productos.fxml", bp);
	}

	@FXML public void clickClientes() throws IOException{
		instancia.ponerEnVis("../vista/clientes.fxml", bp);
	}

	@FXML public void clickProveedores()throws IOException{
		instancia.ponerEnVis("../vista/proveedoress.fxml", bp);
	}
	@FXML public void clickEmpleados() throws IOException{
        instancia.ponerEnVis("../vista/empleados.fxml", bp);
	}
	@FXML public void clickVen() throws IOException{
        instancia.ponerEnVis("../vista/Ventas.fxml", bp);
	}
	@FXML public void clickCompras() throws IOException{
        instancia.ponerEnVis("../vista/Compras.fxml", bp);
	}
	@FXML public void clickCategorias() throws IOException{
		instancia.ponerVentanaModal("../vista/categorias.fxml", "");
	}
	@FXML public void clickMarcas() throws IOException{
		instancia.ponerVentanaModal("../vista/Marcas.fxml", "");
	}

	@FXML public void clickCerrarSesion(){
		instancia.cerrarMenu();
		instancia.ponerVentanaModal("../vista/login.fxml", "Inicio de sesión");
	}
	@FXML public void clickPepelera() throws IOException{
		instancia.ponerVentanaModal("../vista/papelera.fxml", "");
	}

	@FXML public void clickSalir(){
		Conexion.desconectar();
		System.exit(0);
	}


}
