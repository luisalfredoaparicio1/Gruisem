package com.gruisem.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import com.gruisem.controlador.ControladorVentanas;
import com.sun.prism.impl.Disposer.Record;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Callback;

import com.gruisem.modelo.Proveedores;
//import modelo.DAOReportes;

public class ControladorProveedor implements Initializable {
	@FXML Button btnNuevo, btnGuardar, btnEditar, btnCancelar, btnEliminar, btnTabla;
	@FXML TextField txtNombre, txtTelefono, txtEmail, txtCalle, txtNumero, txtColonia, txtCiudad, txtCP, txtBuscar,txtRazon;
	@FXML TableView<Proveedores> tvProveedores;
	@FXML CheckBox ckbInactivos;
	@FXML ImageView imgBuscar, line;

	private ControladorVentanas error;
	private Proveedores proveedor;
	private ObservableList<Proveedores> lista;
	private FilteredList<Proveedores> listaBusqueda;

	public ControladorProveedor() {
		error = ControladorVentanas.getInstancia();
		this.proveedor = new Proveedores();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnGuardar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		txtNombre.setDisable(true);
		txtColonia.setDisable(true);
		txtCalle.setDisable(true);
		txtNumero.setDisable(true);
		txtTelefono.setDisable(true);
		txtCiudad.setDisable(true);
		txtCP.setDisable(true);
		txtEmail.setDisable(true);
		txtRazon.setDisable(true);
		tvProveedores.setVisible(false);
		txtBuscar.setVisible(false);
		imgBuscar.setVisible(false);
		actualizarTabla();

		txtNombre.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z ]{0,35}") || newValue.length()>25)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtCalle.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9 ]{0,40}") || newValue.length()>25)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtColonia.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9 ]{0,50}") || newValue.length()>25)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtNumero.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,10}") || newValue.length()>7)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtTelefono.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,20}") || newValue.length()>10)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtEmail.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-z0-9-_@.]{0,40}") || newValue.length()>30)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtCiudad.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z ]{0,40}") || newValue.length()>25)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtCP.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,20}") || newValue.length()>5)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtBuscar.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9 @.]{0,99}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior
	        else
	            ((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
    	});
    	txtRazon.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z ]{0,35}") || newValue.length()>25)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
	}
	@FXML public void clickCancelar(){
		txtNombre.setDisable(true);
		txtColonia.setDisable(true);
		txtCalle.setDisable(true);
		txtNumero.setDisable(true);
		txtTelefono.setDisable(true);
		txtCiudad.setDisable(true);
		txtCP.setDisable(true);
		txtEmail.setDisable(true);
		txtRazon.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(true);
		btnTabla.setDisable(false);
		tvProveedores.setVisible(false);
		txtBuscar.setVisible(false);
		imgBuscar.setVisible(false);
		line.setVisible(true);
		limpiar();
	}

	private void limpiar() {
		txtNombre.clear();
		txtCalle.clear();
		txtNumero.clear();
		txtColonia.clear();
		txtTelefono.clear();
		txtEmail.clear();
		txtCiudad.clear();
		txtCP.clear();
		txtRazon.clear();;

	}

	private void actualizarTabla() {
		tvProveedores.getItems().clear();
		lista = proveedor.consultar("select * from proveedores where estatus = true");
		tvProveedores.setItems(lista);
		tvProveedores.refresh();
		listaBusqueda=new FilteredList<Proveedores>(lista);
	}
	@FXML public void clickNuevo(){
		txtNombre.setDisable(false);
		txtColonia.setDisable(false);
		txtCalle.setDisable(false);
		txtNumero.setDisable(false);
		txtTelefono.setDisable(false);
		txtEmail.setDisable(false);
		txtCiudad.setDisable(false);
		txtCP.setDisable(false);
		txtRazon.setDisable(false);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		btnEliminar.setDisable(true);
		btnNuevo.setDisable(true);
		btnTabla.setDisable(false);
		tvProveedores.setVisible(false);
		txtBuscar.setVisible(false);
		imgBuscar.setVisible(false);
		line.setVisible(true);
	}
	@FXML public void clickGuardar(){
		try {
			if(txtNombre.getText().trim().isEmpty() ||txtCalle.getText().trim().isEmpty()
					||txtColonia.getText().trim().isEmpty()||txtNumero.getText().trim().isEmpty()
					||txtTelefono.getText().trim().isEmpty() ||txtEmail.getText().trim().isEmpty()
					||txtCiudad.getText().trim().isEmpty() ||txtCP.getText().trim().isEmpty()
					||txtRazon.getText().trim().isEmpty()
			){
				error.ventanaError("../vista/notificacion.fxml", "Vacio");
			}
			else{
				proveedor.setRazonSocial(txtRazon.getText());
				System.out.println(proveedor.getRazonSocial());
				proveedor.setContacto(txtNombre.getText());
				proveedor.setCalle(txtCalle.getText());
				proveedor.setNumero(txtNumero.getText());
				proveedor.setColonia(txtColonia.getText());
				proveedor.setTelefono(txtTelefono.getText());
				proveedor.setEmail(txtEmail.getText());
				proveedor.setCiudad(txtCiudad.getText());
				proveedor.setCodigoP(txtCP.getText());

				if(proveedor.insertar()){
					error.ventanaError("../vista/success.fxml", "Vacio");
				}else{
					error.ventanaError("../vista/Adver.fxml", "Vacio");
				}
			}
			btnCancelar.setDisable(true);
			btnGuardar.setDisable(true);
			btnNuevo.setDisable(false);
			btnTabla.setDisable(false);
			tvProveedores.setVisible(false);
			txtBuscar.setVisible(false);
			imgBuscar.setVisible(false);
			line.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@FXML public void clickTableView(){
		if(tvProveedores.getSelectionModel().getSelectedItem() != null){
			proveedor = tvProveedores.getSelectionModel().getSelectedItem();
			txtNombre.setText(proveedor.getContacto());
			txtCalle.setText(proveedor.getCalle());
			txtNumero.setText(proveedor.getNumero());
			txtColonia.setText(proveedor.getColonia());
			txtTelefono.setText(proveedor.getTelefono());
			txtEmail.setText(proveedor.getEmail());
			txtCiudad.setText(proveedor.getCiudad());
			txtCP.setText(proveedor.getCodigoP());
			txtRazon.setText(proveedor.getRazonSocial());

		    btnEditar.setDisable(false);
		    btnEliminar.setDisable(false);
		    btnGuardar.setDisable(true);
			btnCancelar.setDisable(false);
			tvProveedores.setVisible(true);
			btnNuevo.setDisable(true);
			btnTabla.setDisable(true);

			txtCalle.setDisable(false);
			txtCiudad.setDisable(false);
			txtColonia.setDisable(false);
			txtCP.setDisable(false);
			txtEmail.setDisable(false);
			txtNombre.setDisable(false);
			txtNumero.setDisable(false);
			txtTelefono.setDisable(false);
			line.setVisible(false);
		}

	}
	@FXML public void clickEliminar(){
		if(proveedor.getId()>0){
			proveedor.eliminar();
			tvProveedores.getItems().clear();
			tvProveedores.setItems(proveedor.consultar("select * from proveedores where estatus = true"));
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
		btnTabla.setDisable(false);
		btnGuardar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(true);
		tvProveedores.setVisible(false);
		txtBuscar.setVisible(false);
		imgBuscar.setVisible(false);
		txtCalle.setVisible(true);
		txtCiudad.setVisible(true);
		txtColonia.setVisible(true);
		txtCP.setVisible(true);
		txtEmail.setVisible(true);
		txtNombre.setVisible(true);
		txtNumero.setVisible(true);
		txtTelefono.setVisible(true);
		txtRazon.setDisable(true);
		line.setVisible(true);
	}

	private void bloquear() {
		txtNombre.setDisable(true);
		txtCalle.setDisable(true);
		txtColonia.setDisable(true);
		txtNumero.setDisable(true);
		txtTelefono.setDisable(true);
		txtEmail.setDisable(true);
		txtCiudad.setDisable(true);
		txtCP.setDisable(true);
		txtRazon.setDisable(true);

	}
	@FXML public void clickEditar(){
		if(txtNombre.getText().trim().isEmpty() || txtCalle.getText().trim().isEmpty()
			||txtColonia.getText().trim().isEmpty()||txtNumero.getText().trim().isEmpty()
			||txtTelefono.getText().trim().isEmpty() ||txtEmail.getText().trim().isEmpty()
			||txtCiudad.getText().trim().isEmpty() ||txtCP.getText().trim().isEmpty()
			||txtRazon.getText().trim().isEmpty()
		){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("DATOS FALTANTES");
			alert.setHeaderText(null);
			alert.setContentText("Por favor llena todos los campos.");
			alert.showAndWait();
		}
		else{
			boolean confirmar2=false;
			 confirmar2 = proveedor.consultarCorreo("select count(*) from proveedores where email='"+txtEmail.getText()+"'");
			 System.out.println("lo que lleva confirmar: "+confirmar2);
			if(confirmar2==false){
					this.proveedor.setContacto(txtNombre.getText());
					this.proveedor.setCalle(txtCalle.getText());
					this.proveedor.setNumero(txtNumero.getText());
					this.proveedor.setColonia(txtColonia.getText());
					this.proveedor.setTelefono(txtTelefono.getText());
					this.proveedor.setEmail(txtEmail.getText());
					this.proveedor.setCiudad(txtCiudad.getText());
					this.proveedor.setCodigoP(txtCP.getText());
					this.proveedor.setRazonSocial(txtRazon.getText());
					if(proveedor.editar()){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("DATOS ACTUALIZADOS");
						alert.setHeaderText(null);
						alert.setContentText("Se actualizaron los datos correctamente.");
						alert.showAndWait();

						limpiar();

						bloquear();

						btnEditar.setDisable(true);
						btnEliminar.setDisable(true);
						btnCancelar.setDisable(true);
						btnNuevo.setDisable(false);
						btnTabla.setDisable(false);
						btnGuardar.setDisable(true);
						tvProveedores.setVisible(false);
						txtBuscar.setVisible(false);
						imgBuscar.setVisible(false);
						line.setVisible(true);
						actualizarTabla();
					}
					else{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR.");
						alert.setHeaderText(null);
						alert.setContentText("Ha ocurrido un error, inténtalo de nuevo.");
						alert.showAndWait();
					}
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("Este correo ya existe, ingresa otro.");
				alert.showAndWait();
			}
		}
	}
	@FXML public void textChange_busqueda(){
        if(txtBuscar.getText().trim().isEmpty()){
        	tvProveedores.refresh();
        	tvProveedores.setItems(lista);
        }else{
        	try{
        		listaBusqueda.setPredicate(DAOProveedores->{
        			if(DAOProveedores.getContacto().toLowerCase().contains(txtBuscar.getText().toLowerCase())){
        				return true;
        			}else{
        				return false;
        			}
        		});
        		tvProveedores.refresh();
        		tvProveedores.setItems(listaBusqueda);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	}

	@FXML public void clickTabla(){
		tvProveedores.setVisible(true);
		btnEditar.setDisable(false);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnGuardar.setDisable(true);
		btnNuevo.setDisable(true);
		btnTabla.setDisable(true);
		txtBuscar.setVisible(true);
		imgBuscar.setVisible(true);
		btnCancelar.setDisable(false);
		line.setVisible(false);
	}
}
