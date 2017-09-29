package com.gruisem.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import com.gruisem.modelo.Categorias;

public class ControladorCategorias implements Initializable {
	@FXML TextField txtCategorias, txtBuscar;
	@FXML Button btnNuevo, btnGuardar, btnEliminar, btnCancelar;
	@FXML ImageView img;
	@FXML TableView <Categorias> tvCategorias;

	private Categorias marcas;
	private ObservableList<Categorias> lista;

	private ControladorVentanas error;
	private FilteredList<Categorias> listaBusqueda;

	public ControladorCategorias() {
		this.marcas=new Categorias();
		this.error = ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		txtCategorias.setDisable(true);
		btnCancelar.setDisable(true);
		img.setVisible(true);

		actualizarTabla();
	}

	private void limpiar() {
		txtCategorias.clear();
	}
	@FXML public void clickCancelar(){
		txtCategorias.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(true);
		limpiar();
	}
	@FXML public void cerrar(){
		error.cerrarAcceso();
	}

	private void actualizarTabla() {
		tvCategorias.getItems().clear();
		lista = marcas.consultar("select * from categorias where estatus = true;");
		tvCategorias.setItems(lista);
		tvCategorias.refresh();
		listaBusqueda=new FilteredList<Categorias>(lista);
	}

	@FXML public void clickNuevo(){
		txtCategorias.setDisable(false);
		btnGuardar.setDisable(false);
		btnNuevo.setDisable(true);
		btnCancelar.setDisable(false);

	}

	@FXML public void clickGuardar(){
		try {
			if(txtCategorias.getText().trim().isEmpty()){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("DATOS FALTANTES");
				alert.setHeaderText(null);
				alert.setContentText("Por favor llena el campo.");
				alert.showAndWait();
			}
			else{
					marcas.setNombre(txtCategorias.getText());
					if(marcas.insertar()){
						Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("CLIENTE REGISTRADO.");
							alert.setHeaderText(null);
							alert.setContentText("Se agregó correctamente un nuevo cliente.");
							alert.showAndWait();
							actualizarTabla();
							limpiar();

						}
						else{
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("ERROR.");
							alert.setHeaderText(null);
							alert.setContentText("Ha ocurrido un error, inténtalo de nuevo.");
							alert.showAndWait();
						}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML public void clickTableView(){
		if(tvCategorias.getSelectionModel().getSelectedItem() != null){
			marcas = tvCategorias.getSelectionModel().getSelectedItem();
			txtCategorias.setText(marcas.getNombre());
			btnCancelar.setDisable(false);
			btnGuardar.setDisable(false);
		    btnEliminar.setDisable(false);
		    txtCategorias.setDisable(false);

			btnNuevo.setDisable(true);
		}
	}

	@FXML public void clickEliminar(){
		if(marcas.getIdCategorias() > 0){
			marcas.eliminar();
			tvCategorias.getItems().clear();
			tvCategorias.setItems(marcas.consultar("select * from categorias where estatus = true"));
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("NO SE SELECCIONÓ NADA");
			alert.setHeaderText(null);
			alert.setContentText("Selecciona un cliente de la tabla.");
			alert.showAndWait();
		}
		limpiar();
		bloquear();
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
	}

	private void bloquear() {
		txtCategorias.setDisable(true);
	}

	@FXML public void textChange_busqueda(){
        if(txtBuscar.getText().trim().isEmpty()){
        	tvCategorias.refresh();
        	tvCategorias.setItems(lista);
        }else{
        	try{
        		listaBusqueda.setPredicate(Categorias->{
        			if(Categorias.getNombre().toLowerCase().contains(txtBuscar.getText().toLowerCase())){
        				return true;
        			}else{
        				return false;
        			}
        		});
        		tvCategorias.refresh();
        		tvCategorias.setItems(listaBusqueda);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	}
}