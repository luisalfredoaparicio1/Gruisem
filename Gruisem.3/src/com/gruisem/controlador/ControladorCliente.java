package com.gruisem.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


//import controlador.notificaciones.Notification;
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
import com.gruisem.modelo.Clientes;
import com.sun.prism.impl.Disposer.Record;

public class ControladorCliente implements Initializable {
	@FXML TextField txtNombre, txtAPaterno, txtAMaterno, txtEmail, txtCalle, txtNumero, txtColonia, txtCiudad, txtCP, txtTelefono, txtBuscar;
	@FXML Button btnNuevo, btnGuardar, btnEditar, btnCancelar, btnEliminar, btnClientes;
	@FXML TableView<Clientes> tvClientes;
	@FXML CheckBox ckbInactivos;
	@FXML ImageView buscador,line;
	@FXML Label txtdatosp, txtdatosc;
	private ControladorVentanas instancia;

	private ControladorVentanas error;
	private Clientes cliente;
	private ObservableList<Clientes> lista;

	private FilteredList<Clientes> listaBusqueda;
	//private DAOReportes reporteador;

	public ControladorCliente() {
		error = ControladorVentanas.getInstancia();
		this.cliente=new Clientes();
		//this.reporteador=new Reportes();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnGuardar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(true);
		btnEditar.setDisable(true);
		txtNombre.setDisable(true);
		txtEmail.setDisable(true);
		txtColonia.setDisable(true);
		txtCalle.setDisable(true);
		txtNumero.setDisable(true);
		txtTelefono.setDisable(true);
		txtAPaterno.setDisable(true);
		txtAMaterno.setDisable(true);
		txtCiudad.setDisable(true);
		txtCP.setDisable(true);
		tvClientes.setVisible(false);
		txtBuscar.setVisible(false);
		buscador.setVisible(false);
		txtdatosp.setVisible(true);
		txtdatosc.setVisible(true);
		line.setVisible(true);

		actualizarTabla();

		txtNombre.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z ]{0,35}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtAPaterno.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z]{0,35}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtAMaterno.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z]{0,35}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtCalle.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9 ]{0,40}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtColonia.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9 ]{0,50}") || newValue.length()>50)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtEmail.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-z0-9-_@.]{0,40}") || newValue.length()>40)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtCiudad.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z ]{0,40}") || newValue.length()>50)
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
    	txtCP.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,20}") || newValue.length()>10)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
    	txtBuscar.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9 .]{0,99}") || newValue.length()>90)
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});

	}
	@FXML public void clickCancelar(){
		txtNombre.setDisable(true);
		txtEmail.setDisable(true);
		txtColonia.setDisable(true);
		txtCalle.setDisable(true);
		txtNumero.setDisable(true);
		txtTelefono.setDisable(true);
		txtAPaterno.setDisable(true);
		txtAMaterno.setDisable(true);
		txtCiudad.setDisable(true);
		txtCP.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(true);
		tvClientes.setVisible(false);
		txtBuscar.setVisible(false);
		buscador.setVisible(false);
		txtdatosp.setVisible(true);
		txtdatosc.setVisible(true);
		line.setVisible(true);
		limpiar();
	}

	private void limpiar() {
		txtNombre.clear();
		txtEmail.clear();
		txtCalle.clear();
		txtNumero.clear();
		txtColonia.clear();
		txtTelefono.clear();
		txtAPaterno.clear();
		txtAMaterno.clear();
		txtCiudad.clear();
		txtCP.clear();

	}

	private void actualizarTabla() {
		tvClientes.getItems().clear();
		lista = cliente.consultar("select * from clientes where estatus = true;");
		tvClientes.setItems(lista);
		tvClientes.refresh();
		listaBusqueda=new FilteredList<Clientes>(lista);
	}
	@FXML public void clickNuevo(){
		txtNombre.setDisable(false);
		txtEmail.setDisable(false);
		txtColonia.setDisable(false);
		txtCalle.setDisable(false);
		txtNumero.setDisable(false);
		txtAPaterno.setDisable(false);
		txtAMaterno.setDisable(false);
		txtCiudad.setDisable(false);
		txtCP.setDisable(false);
		txtTelefono.setDisable(false);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		btnNuevo.setDisable(true);
	}
	@FXML public void clickClientes(){
		tvClientes.setVisible(true);
		btnGuardar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(false);
		btnEditar.setDisable(true);
		txtNombre.setDisable(false);
		txtEmail.setDisable(false);
		txtColonia.setDisable(false);
		txtCalle.setDisable(false);
		txtNumero.setDisable(false);
		txtTelefono.setDisable(false);
		txtAPaterno.setDisable(false);
		txtAMaterno.setDisable(false);
		txtCiudad.setDisable(false);
		txtCP.setDisable(false);
		txtBuscar.setVisible(true);
		buscador.setVisible(true);
		txtdatosp.setVisible(true);
		txtdatosc.setVisible(true);
		line.setVisible(false);
		actualizarTabla();
	}
	@FXML public void clickGuardar(){
		try {
			if(txtNombre.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()||txtCalle.getText().trim().isEmpty()
				||txtColonia.getText().trim().isEmpty()||txtNumero.getText().trim().isEmpty()||txtTelefono.getText().trim().isEmpty()
				||txtAPaterno.getText().trim().isEmpty()||txtAMaterno.getText().trim().isEmpty()||txtCiudad.getText().trim().isEmpty()
				||txtCP.getText().trim().isEmpty()
				){
				error.ventanaError("../vista/notificacion.fxml", "Vacio");
			}
			else{
				boolean confirmar=false;
				 confirmar = cliente.consultarCorreo("select count(*) from clientes where email='"+txtEmail.getText()+"'");
				 System.out.println("lo que lleva confirmar: "+confirmar);
				if(confirmar==false){
						cliente.setNombre(txtNombre.getText());
						cliente.setEmail(txtEmail.getText());
						cliente.setCalle(txtCalle.getText());
						cliente.setNumero(txtNumero.getText());
						cliente.setColonia(txtColonia.getText());
						cliente.setTelefono(txtTelefono.getText());
						cliente.setAPaterno(txtAPaterno.getText());
						cliente.setAMaterno(txtAMaterno.getText());
						cliente.setCiudad(txtCiudad.getText());
						cliente.setCodigoP(txtCP.getText());
						if(cliente.insertar()){
							error.ventanaError("../vista/success.fxml", "Vacio");
						}else{
							error.ventanaError("../vista/Adver.fxml", "Vacio");
						}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@FXML public void clickTableView(){
		if(tvClientes.getSelectionModel().getSelectedItem() != null){
			cliente = tvClientes.getSelectionModel().getSelectedItem();
			txtNombre.setText(cliente.getNombre());
			txtEmail.setText(cliente.getEmail());
			txtCalle.setText(cliente.getCalle());
			txtNumero.setText(cliente.getNumero());
			txtColonia.setText(cliente.getColonia());
			txtTelefono.setText(cliente.getTelefono());
			txtAPaterno.setText(cliente.getAPaterno());
			txtAMaterno.setText(cliente.getAMaterno());
			txtCiudad.setText(cliente.getCiudad());
			txtCP.setText(cliente.getCodigoP());

		    btnEditar.setDisable(false);
		    btnGuardar.setDisable(true);
		    btnEliminar.setDisable(false);
			btnCancelar.setDisable(false);
			txtNombre.setDisable(false);
			txtEmail.setDisable(false);
			txtCalle.setDisable(false);
			txtNumero.setDisable(false);
			txtColonia.setDisable(false);
			txtTelefono.setDisable(false);
			txtAPaterno.setDisable(false);
			txtAMaterno.setDisable(false);
			txtCiudad.setDisable(false);
			txtCP.setDisable(false);
			tvClientes.setVisible(false);
			txtBuscar.setVisible(false);
			buscador.setVisible(false);
			btnNuevo.setDisable(true);

			//if(ckbInactivos.isSelected()){
				//btnEliminar.setDisable(true);
			}
		}

	//}
	@FXML public void clickEliminar(){
		if(cliente.getIdCliente() > 0){
			cliente.eliminar();
			tvClientes.getItems().clear();
			tvClientes.setItems(cliente.consultar("select * from clientes where estatus = true"));
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
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancelar.setDisable(true);
	}

	private void bloquear() {
		txtNombre.setDisable(true);
		txtEmail.setDisable(true);
		txtCalle.setDisable(true);
		txtColonia.setDisable(true);
		txtNumero.setDisable(true);
		txtTelefono.setDisable(true);
		txtAPaterno.setDisable(true);
		txtAMaterno.setDisable(true);
		txtCiudad.setDisable(true);
		txtCP.setDisable(true);

	}
	@FXML public void clickEditar(){
		if(txtNombre.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()||txtCalle.getText().trim().isEmpty()
			||txtColonia.getText().trim().isEmpty()||txtNumero.getText().trim().isEmpty()||txtTelefono.getText().trim().isEmpty()
			||txtAPaterno.getText().trim().isEmpty()||txtAMaterno.getText().trim().isEmpty()||txtCiudad.getText().trim().isEmpty()
			||txtCP.getText().trim().isEmpty()
			){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("DATOS FALTANTES");
			alert.setHeaderText(null);
			alert.setContentText("Por favor llena todos los campos.");
			alert.showAndWait();
		}
		else{
			boolean confirmar=false;
			 confirmar = cliente.consultarCorreo("select count(*) from clientes where email='"+txtEmail.getText()+"'");
			 System.out.println("lo que lleva confirmar: "+confirmar);
			if(confirmar==false){
					this.cliente.setNombre(txtNombre.getText());
					this.cliente.setEmail(txtEmail.getText());
					this.cliente.setCalle(txtCalle.getText());
					this.cliente.setNumero(txtNumero.getText());
					this.cliente.setColonia(txtColonia.getText());
					this.cliente.setTelefono(txtTelefono.getText());
					this.cliente.setAPaterno(txtAPaterno.getText());
					this.cliente.setAMaterno(txtAMaterno.getText());
					this.cliente.setCiudad(txtCiudad.getText());
					this.cliente.setCodigoP(txtCP.getText());
					if(cliente.editar()
					){
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
	@SuppressWarnings("unchecked")
	@FXML public void clickInactivos(){
		limpiar();
		try {
			tvClientes.getItems().clear();
			if(ckbInactivos.isSelected()){

				lista = cliente.consultar("select * from clientes where estatus = false");
				listaBusqueda = new FilteredList<Clientes>(lista);


				@SuppressWarnings("rawtypes")
				TableColumn columnaRestaurar = new TableColumn<>();
				tvClientes.getColumns().add(0,columnaRestaurar);
				columnaRestaurar.setCellValueFactory(
						new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {
							public ObservableValue<Boolean> call(CellDataFeatures<Record, Boolean> param) {
								return new SimpleBooleanProperty(param.getValue()!=null);
							}
						});
				columnaRestaurar.setCellFactory(
						new Callback<TableColumn<Record, Boolean>, TableCell<Record,Boolean>>() {

							@Override
							public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> param) {
								return new BotonActivar();
							}
						});
			}
			else{
                btnEliminar.setDisable(true);
				if(tvClientes.getColumns().size()>2){
					tvClientes.getColumns().remove(0);
				}
				lista = cliente.consultar("select * from clientes where estatus = true");
				listaBusqueda = new FilteredList<Clientes>(lista);
			}
			tvClientes.setItems(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private class BotonActivar extends TableCell<Record, Boolean> {
		Button cellButton;
		Image imagen;
		ImageView contenedor;

	    BotonActivar(){
			imagen=new Image("icono/restaurar.png");
			contenedor = new ImageView(imagen);
			contenedor.setFitWidth(15);
			contenedor.setFitHeight(15);
	    	cellButton =new Button("", contenedor);

	        cellButton.setOnAction(new EventHandler<ActionEvent>(){
	            @Override
	            public void handle(ActionEvent t) {
	            	cliente = (Clientes) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
	           		if(cliente.reactivar()){
						lista = cliente.consultar("select * from clientes where estatus = false");
						tvClientes.setItems(lista);
						tvClientes.refresh();
					}
	            }
	        });
	    }
	    @Override
	    protected void updateItem(Boolean t, boolean empty) {
	        super.updateItem(t, empty);
	        if(!empty){
	            setGraphic(cellButton);
	        }
	    }
	}
	@FXML public void textChange_busqueda(){
        if(txtBuscar.getText().trim().isEmpty()){
        	tvClientes.refresh();
        	tvClientes.setItems(lista);
        }else{
        	try{
        		listaBusqueda.setPredicate(DAOClientes->{
        			if(DAOClientes.getNombre().toLowerCase().contains(txtBuscar.getText().toLowerCase())){
        				return true;
        			}else{
        				return false;
        			}
        		});
        		tvClientes.refresh();
        		tvClientes.setItems(listaBusqueda);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	}
}