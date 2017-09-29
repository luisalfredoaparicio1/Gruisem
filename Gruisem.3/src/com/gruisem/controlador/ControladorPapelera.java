package com.gruisem.controlador;

import java.awt.Event;
import java.net.URL;
import java.util.ResourceBundle;

import com.gruisem.modelo.Categorias;
import com.gruisem.modelo.Clientes;
import com.gruisem.modelo.Empleado;
import com.gruisem.modelo.Marcas;
import com.gruisem.modelo.Proveedores;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ControladorPapelera implements Initializable{
	@FXML TableView tvProductos;
	@FXML TableView<Proveedores> tvProveedores;
	@FXML TableView<Clientes> tvClientes;
	@FXML TableView<Empleado> tvEmpleados;
	@FXML TableView<Categorias> tvCategorias;
	@FXML TableView<Marcas> tvMarcas;
	@FXML Button btnRes, btnRes1, btnRes2, btnRes3, btnRes4;

	private ObservableList<Proveedores> listaProv;
	private ObservableList<Clientes> listaCli;
	private ObservableList<Empleado> listaEmp;
	private ObservableList<Categorias> listaCat;
	private ObservableList<Marcas> listaMar;

	private Proveedores proveedor;
	private Clientes cliente;
	private Empleado empleado;
	private Categorias categoria;
	private Marcas marca;

	private ControladorVentanas error;

	public ControladorPapelera() {
		this.proveedor=new Proveedores();
		this.cliente = new Clientes();
		this.empleado = new Empleado();
		this.categoria = new Categorias();
		this.marca = new Marcas();
		this.error = ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tvProveedores.getItems().clear();
		listaProv = proveedor.consultar("select * from proveedores where estatus = false");
		tvProveedores.setItems(listaProv);
		tvProveedores.refresh();

		tvClientes.getItems().clear();
		listaCli = cliente.consultar("select * from clientes where estatus = false");
		tvClientes.setItems(listaCli);
		tvClientes.refresh();

		tvEmpleados.getItems().clear();
		listaEmp = empleado.consultar("select * from empleados where estatus = false");
		tvEmpleados.setItems(listaEmp);
		tvEmpleados.refresh();

		tvCategorias.getItems().clear();
		listaCat = categoria.consultar("select * from categorias where estatus = false");
		tvCategorias.setItems(listaCat);
		tvCategorias.refresh();

		tvMarcas.getItems().clear();
		listaMar = marca.consultar("select * from marcas where estatus = false");
		tvMarcas.setItems(listaMar);
		tvMarcas.refresh();
	}

	@FXML public void cerrar(){
		error.cerrarAcceso();
	}

    @FXML public void clickTablas(){
    	try {
			if (tvCategorias.getSelectionModel().getSelectedItem()!=null||tvClientes.getSelectionModel().getSelectedItem()!=null
			||tvEmpleados.getSelectionModel().getSelectedItem()!=null||tvMarcas.getSelectionModel().getSelectedItem()!=null
			||tvProveedores.getSelectionModel().getSelectedItem()!=null) {
				btnRes.setDisable(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @FXML public void clickTablas1(){
    	try {
			if (tvCategorias.getSelectionModel().getSelectedItem()!=null||tvClientes.getSelectionModel().getSelectedItem()!=null
			||tvEmpleados.getSelectionModel().getSelectedItem()!=null||tvMarcas.getSelectionModel().getSelectedItem()!=null
			||tvProveedores.getSelectionModel().getSelectedItem()!=null) {
				btnRes1.setOpacity(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @FXML public void clickTablas2(){
    	try {
			if (tvCategorias.getSelectionModel().getSelectedItem()!=null||tvClientes.getSelectionModel().getSelectedItem()!=null
			||tvEmpleados.getSelectionModel().getSelectedItem()!=null||tvMarcas.getSelectionModel().getSelectedItem()!=null
			||tvProveedores.getSelectionModel().getSelectedItem()!=null) {
				btnRes2.setOpacity(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @FXML public void clickTablas3(){
    	try {
			if (tvCategorias.getSelectionModel().getSelectedItem()!=null||tvClientes.getSelectionModel().getSelectedItem()!=null
			||tvEmpleados.getSelectionModel().getSelectedItem()!=null||tvMarcas.getSelectionModel().getSelectedItem()!=null
			||tvProveedores.getSelectionModel().getSelectedItem()!=null) {
				btnRes3.setOpacity(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @FXML public void clickTablas4(){
    	try {
			if (tvCategorias.getSelectionModel().getSelectedItem()!=null||tvClientes.getSelectionModel().getSelectedItem()!=null
			||tvEmpleados.getSelectionModel().getSelectedItem()!=null||tvMarcas.getSelectionModel().getSelectedItem()!=null
			||tvProveedores.getSelectionModel().getSelectedItem()!=null) {
				btnRes4.setOpacity(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




    @FXML public void recuperarProv(){
    	proveedor= tvProveedores.getSelectionModel().getSelectedItem();
    	System.out.println(""+proveedor);
        proveedor.reactivar();
    		error.ventanaError("../vista/success.fxml", "");
    		tvProveedores.getItems().clear();
    		listaProv = proveedor.consultar("select * from proveedores where estatus = false");
    		tvProveedores.setItems(listaProv);
    		tvProveedores.refresh();





    }
    @FXML public void recuperarCli(){
    	cliente= tvClientes.getSelectionModel().getSelectedItem();

    	cliente.reactivar();
    	tvClientes.refresh();

    }

    @FXML public void recuperarEmp(){
    	empleado.reactivar();
    	tvEmpleados.refresh();

    }
    @FXML public void recuperarMar(){
    	marca.reactivar();
    	tvMarcas.refresh();

    }
    @FXML public void recuperarCa(){
    	categoria.reactivar();
    	tvCategorias.refresh();

    }

}
