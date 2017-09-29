package com.gruisem.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import com.gruisem.modelo.Marcas;


public class ControladorMarcas implements Initializable {
	@FXML TextField txtMarcas, txtBuscar;
	@FXML Button btnNuevo, btnGuardar, btnEliminar, btnSalir,btnCancelar,btnTabla;
	@FXML ImageView imgBuscar,n;
	@FXML TableView <Marcas> tvMarcas;

	private Marcas marcas;
	private ObservableList<Marcas> lista;
	private ControladorVentanas error;
	private FilteredList<Marcas> listaBusqueda;
	public ControladorMarcas() {
		this.marcas=new Marcas();
		error = ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		txtMarcas.setDisable(true);
		btnCancelar.setDisable(true);
		tvMarcas.setVisible(false);
		imgBuscar.setVisible(false);
		txtBuscar.setVisible(false);
		actualizarTabla();
	}

	private void limpiar() {
		txtMarcas.clear();
	}

	private void actualizarTabla() {
		tvMarcas.getItems().clear();
		lista = marcas.consultar("select * from marcas where estatus = true;");
		tvMarcas.setItems(lista);
		tvMarcas.refresh();
		listaBusqueda=new FilteredList<Marcas>(lista);
	}

	@FXML public void clickNuevo(){
		txtMarcas.setDisable(false);
		btnGuardar.setDisable(false);
		btnNuevo.setDisable(true);
		btnCancelar.setDisable(false);
		tvMarcas.setVisible(false);
		imgBuscar.setVisible(false);
		txtBuscar.setVisible(false);
		btnTabla.setDisable(false);
		n.setVisible(true);

	}

	@FXML public void clickGuardar(){
		try {
			if(txtMarcas.getText().trim().isEmpty()){
				error.ventanaError("../vista/notificacion.fxml", "Vacio");
			}
			else
			{
				marcas.setNombre(txtMarcas.getText());
				if(marcas.insertar()){
					error.ventanaError("../vista/success.fxml", "Vacio");
				}
				else{
					error.ventanaError("../vista/Adver.fxml", "Vacio");
				}
					actualizarTabla();
					limpiar();
					btnNuevo.setDisable(false);
					btnGuardar.setDisable(true);
					txtMarcas.setDisable(true);
					tvMarcas.setVisible(false);
					imgBuscar.setVisible(false);
					txtBuscar.setVisible(false);
					btnTabla.setDisable(false);
					n.setVisible(true);
				}
			}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML public void clickTableView(){
		if(tvMarcas.getSelectionModel().getSelectedItem() != null){
			marcas = tvMarcas.getSelectionModel().getSelectedItem();
			txtMarcas.setText(Marcas.getNombre());


		    btnEliminar.setDisable(false);
		    btnGuardar.setDisable(true);
			btnCancelar.setDisable(false);
			tvMarcas.setVisible(true);
			btnNuevo.setDisable(true);
			btnTabla.setDisable(true);

			txtMarcas.setDisable(false);
			n.setVisible(false);
		}
	}

	@FXML public void clickEliminar(){
		if(marcas.getId() > 0){
			marcas.eliminar();
			tvMarcas.getItems().clear();
			tvMarcas.setItems(marcas.consultar("select * from marcas where estatus = true"));
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("NO SE SELECCIONÓ NADA");
			alert.setHeaderText(null);
			alert.setContentText("Selecciona una categoria de la tabla.");
			alert.showAndWait();
		}
		limpiar();
		bloquear();
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		txtMarcas.setDisable(true);
		tvMarcas.setVisible(false);
		imgBuscar.setVisible(false);
		txtBuscar.setVisible(false);
		btnTabla.setDisable(false);
		n.setVisible(true);
	}

	private void bloquear() {
		txtMarcas.setDisable(true);
	}

	@FXML public void cerrar(){
	     // get a handle to the stage
	     Stage stage = (Stage) btnSalir.getScene().getWindow();
	     // do what you have to do
	     stage.close();
	}

	@FXML public void textChange_busqueda(){
        if(txtBuscar.getText().trim().isEmpty()){
        	tvMarcas.refresh();
        	tvMarcas.setItems(lista);
        }else{
        	try{
        		listaBusqueda.setPredicate(Clientes->{
        			if(Clientes.getNombre().toLowerCase().contains(txtBuscar.getText().toLowerCase())){
        				return true;
        			}else{
        				return false;
        			}
        		});
        		tvMarcas.refresh();
        		tvMarcas.setItems(listaBusqueda);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	}

	@FXML public void clickCancelar(){
		txtMarcas.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(true);
		btnTabla.setDisable(false);
		tvMarcas.setVisible(false);
		txtBuscar.setVisible(false);
		imgBuscar.setVisible(false);
		tvMarcas.setVisible(false);
		imgBuscar.setVisible(false);
		txtBuscar.setVisible(false);
		n.setVisible(true);
		limpiar();
	}

	@FXML public void clickTabla(){
		tvMarcas.setVisible(true);
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(true);
		btnNuevo.setDisable(true);
		txtBuscar.setVisible(true);
		imgBuscar.setVisible(true);
		btnCancelar.setDisable(false);
		btnTabla.setDisable(true);
        tvMarcas.setOpacity(1);

		n.setVisible(false);
	}
}