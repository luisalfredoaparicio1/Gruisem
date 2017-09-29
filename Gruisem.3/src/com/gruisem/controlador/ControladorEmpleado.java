package com.gruisem.controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.gruisem.modelo.Conexion;
import com.gruisem.modelo.Empleado;
import com.gruisem.modelo.Usuario;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.control.Alert.AlertType;

public class ControladorEmpleado implements Initializable{
	@FXML private ComboBox<String> cbGenero;
	@FXML private TextField buscar, txtNombre, txtAp, txtAm, txtTelefono, txtCorreo, txtCalle, txtNumero, txtColonia, txtCiudad, txtCp;
	@FXML private DatePicker dpFecha;
	@FXML private Button btnNuevo, btnGuardar, btnEliminar,  btnEditar, btnAbrir, btnCancelar;
	@FXML private TableView<Empleado> tvEmpleados;
	@FXML private Label lblId, total;
	@FXML private TabPane pane;
	@FXML private Tab tab;
	@FXML private ImageView imagen;
	private FilteredList<Empleado> listaBusqueda;
	private Connection conexion;
	private ControladorVentanas error;
	private Empleado empleado;
	private ObservableList<Empleado> lista;
	private ControladorVentanas instancia;

	Image img=new Image("com/gruisem/vista/icono/empleados.png");

	public ControladorEmpleado() {
		error = ControladorVentanas.getInstancia();
		this.empleado = new Empleado();
		instancia = ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bloquear();

		pane.setVisible(false);
		imagen.setImage(img);

		cbGenero.getItems().addAll("H","M");

		txtNombre.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z]{0,30}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtAp.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z]{0,20}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtTelefono.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,30}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtCorreo.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-z0-9-_@.]{0,40}") || newValue.length()>40)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtCalle.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9 ]{0,50}") || newValue.length()>50)
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
		txtColonia.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z ]{0,50}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtCiudad.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z ]{0,50}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtCp.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,15}") || newValue.length()>10)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});

		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
	}

	@FXML public void clickNuevo(){
		desbloquear();
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		btnNuevo.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
	}

	public void desbloquear(){
		txtNombre.setDisable(false);
		txtAp.setDisable(false);
		txtAm.setDisable(false);
		txtTelefono.setDisable(false);
		txtCorreo.setDisable(false);
		txtCalle.setDisable(false);
		txtNumero.setDisable(false);
		txtColonia.setDisable(false);
		txtCiudad.setDisable(false);
		txtCp.setDisable(false);
		cbGenero.setDisable(false);
		dpFecha.setDisable(false);
	}

	@FXML public void cancel(){
		cancelar();
	}

	public void cancelar(){
		txtNombre.clear();
		txtAp.clear();
		txtAm.clear();
		txtTelefono.clear();
		txtCorreo.clear();
		txtCalle.clear();
		txtNumero.clear();
		txtColonia.clear();
		txtCiudad.clear();
		txtCp.clear();
		dpFecha.setValue(null);
		lblId.setText(null);

	}

	@FXML public void clickGuardar(){
		try {

		if (txtNombre.getText().trim().isEmpty()||txtAp.getText().trim().isEmpty()||txtTelefono.getText().trim().isEmpty()||
				txtCorreo.getText().trim().isEmpty()||txtCalle.getText().trim().isEmpty()||txtNumero.getText().trim().isEmpty()||
				txtColonia.getText().trim().isEmpty()||txtCiudad.getText().trim().isEmpty()||txtCp.getText().trim().isEmpty()||
				dpFecha.getValue()==null||cbGenero.getSelectionModel().getSelectedItem()==null)
		{
			error.ventanaError("../vista/notificacion.fxml", "Vacio");
		}else{
			empleado.setNombre(txtNombre.getText());
			empleado.setAPaterno(txtAp.getText());
			empleado.setAMaterno(txtAm.getText());
			//if(txtAm.getText().isEmpty()){empleado.setAMaterno("");}else{empleado.setAMaterno(txtAm.getText());}
			LocalDate locald = LocalDate.of(dpFecha.getValue().getYear(),dpFecha.getValue().getMonth(),dpFecha.getValue().getDayOfMonth());
			Date date = Date.valueOf(locald);
			empleado.setFechaNaci((date));
			empleado.setSexo(cbGenero.getSelectionModel().getSelectedItem());
			empleado.setTelefono(txtTelefono.getText());
			empleado.setEmail(txtCorreo.getText());
			empleado.setCalle(txtCalle.getText());
			empleado.setNumero(txtNumero.getText());
			empleado.setColonia(txtColonia.getText());
			empleado.setCiudad(txtCiudad.getText());
			empleado.setCp(Integer.parseInt(txtCp.getText()));

			if(empleado.insertar()){
				error.ventanaError("../vista/success.fxml", "Vacio");
				actualizarTabla();
				tvEmpleados.refresh();
				limpiar();
				btnGuardar.setDisable(true);
				btnNuevo.setDisable(false);
			}else{
				error.ventanaError("../vista/Adver.fxml", "Vacio");
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML public void clickAgregarUsu(){

		instancia.ponerVentanaModal("../vista/Usuario.fxml","Usuarios");
	}

	@FXML public void clickEditar(){

		if (txtNombre.getText().trim().isEmpty()||txtTelefono.getText().trim().isEmpty()||
				txtCorreo.getText().trim().isEmpty()||txtCalle.getText().trim().isEmpty()||txtNumero.getText().trim().isEmpty()||
				txtColonia.getText().trim().isEmpty()||txtCiudad.getText().trim().isEmpty()||txtCp.getText().trim().isEmpty()||
				dpFecha.getValue()==null||cbGenero.getSelectionModel().getSelectedItem()==null)
		{
			error.ventanaError("../vista/notificacion.fxml", "Vacio");
		}else{
			this.empleado.setNombre(txtNombre.getText());
			this.empleado.setAPaterno(txtAp.getText());
			if(txtAm.getText().isEmpty()){empleado.setAMaterno("");}else{this.empleado.setAMaterno(txtAm.getText());}
			LocalDate locald = LocalDate.of(dpFecha.getValue().getYear(),dpFecha.getValue().getMonth(),dpFecha.getValue().getDayOfMonth());
			Date date = Date.valueOf(locald);
			this.empleado.setFechaNaci((date));
			this.empleado.setSexo(cbGenero.getSelectionModel().getSelectedItem());
			this.empleado.setTelefono(txtTelefono.getText());
			this.empleado.setEmail(txtCorreo.getText());
			this.empleado.setCalle(txtCalle.getText());
			this.empleado.setNumero(txtNumero.getText());
			this.empleado.setColonia(txtColonia.getText());
			this.empleado.setCiudad(txtCiudad.getText());
			this.empleado.setCp(Integer.parseInt(txtCp.getText()));
			if(empleado.editar()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("DATOS ACTUALIZADOS");
				alert.setHeaderText(null);
				alert.setContentText("Se actualizaron los datos correctamente.");
				alert.showAndWait();
				limpiar();
				actualizarTabla();
			}
			else
			{
				error.ventanaError("../vista/Adver.fxml", "Vacio");
			}
		}

	}
	@FXML public void clickCerrar(){
		limpiar();
		bloquear();
		pane.setVisible(false);
		imagen.setImage(img);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
		btnGuardar.setDisable(true);
		btnNuevo.setDisable(false);
	}

	@FXML public void clickAbrir(){
		imagen.setImage(null);
		pane.setVisible(true);
		actualizarTabla();
	}
	private void actualizarTabla() {
		tvEmpleados.getItems().clear();
		lista = empleado.consultar("select * from empleados where estatus = true");
		tvEmpleados.setItems(lista);
		tvEmpleados.refresh();
	}

	@FXML public void clickTabla(){
		desbloquear();
		btnGuardar.setDisable(true);
		btnEditar.setDisable(false);
		btnCancelar.setDisable(false);
		btnNuevo.setDisable(true);
		btnEliminar.setDisable(false);
		if(tvEmpleados.getSelectionModel().getSelectedItem() != null){
			empleado = tvEmpleados.getSelectionModel().getSelectedItem();
			txtNombre.setText(empleado.getNombre());
			txtAp.setText(empleado.getAPaterno());
			txtAm.setText(empleado.getAMaterno());
			dpFecha.setValue(empleado.getFechaNaci().toLocalDate());
			cbGenero.getSelectionModel().select(empleado.getSexo());
			txtCalle.setText(empleado.getCalle());
			txtNumero.setText(empleado.getNumero());
			txtColonia.setText(empleado.getColonia());
			txtTelefono.setText(empleado.getTelefono());
			txtCorreo.setText(empleado.getEmail());
			txtCiudad.setText(empleado.getCiudad());
			txtCp.setText(String.valueOf(empleado.getCp()));
			lblId.setText(String.valueOf(empleado.getId()));

		}
	}

	@FXML public void clickEliminar(){
		if(empleado.getId() > 0){
			empleado.eliminar();
			tvEmpleados.getItems().clear();
			tvEmpleados.setItems(empleado.consultar("select * from empleados where estatus = true"));
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("NO SE HA SELECCIONADO NADA");
			alert.setHeaderText(null);
			alert.setContentText("Selecciona un empleado de la tabla.");
			alert.showAndWait();
		}
	}

	public void bloquear(){
		txtNombre.setDisable(true);
		txtAp.setDisable(true);
		txtAm.setDisable(true);
		txtTelefono.setDisable(true);
		txtCorreo.setDisable(true);
		txtCalle.setDisable(true);
		txtNumero.setDisable(true);
		txtColonia.setDisable(true);
		txtCiudad.setDisable(true);
		txtCp.setDisable(true);
		cbGenero.setDisable(true);
		dpFecha.setDisable(true);
	}

	public void limpiar(){
		txtNombre.clear();
		txtAp.clear();
		txtAm.clear();
		txtCalle.clear();
		txtNumero.clear();
		txtColonia.clear();
		txtTelefono.clear();
		txtCorreo.clear();
		txtCp.clear();
		txtCiudad.clear();
		cbGenero.getSelectionModel().clearSelection();
		dpFecha.setValue(null);
		lblId.setText(null);
	}
/*
	@FXML public void textChange_busqueda(){
        if(buscar.getText().trim().isEmpty()){
        	tvEmpleados.refresh();
        	tvEmpleados.setItems(lista);
        }else{
        	try{
        		listaBusqueda.setPredicate(Empleado->{
        			if(Empleado.getNombre().toLowerCase().contains(buscar.getText().toLowerCase())){
        				return true;
        			}else{
        				return false;
        			}
        		});
        		tvEmpleados.refresh();
        		tvEmpleados.setItems(listaBusqueda);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	}
*/
}
