package com.gruisem.controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import com.gruisem.modelo.Conexion;

public class ControladorProducto implements Initializable{
	@FXML TextField nombre, precio, smax, smin, clave, buscar;
	@FXML ComboBox categoria, marca, proveedor;
	@FXML Button guardar, eliminar, editar, nuevo, cancelar;
	@FXML TableView tvProductos;
	@FXML private TableColumn col;
	@FXML private Label id;
	private Connection conexion;
	private ObservableList<ControladorProducto> productos= FXCollections.observableArrayList();
	ObservableList<ObservableList> producto;
	@FXML TextField filtered;
	@FXML TabPane tab;
	@FXML ImageView imagen;


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tab.setVisible(false);
		imagen.setImage(img);
		guardar.setDisable(true);
		eliminar.setDisable(true);
		editar.setDisable(true);
		cancelar.setDisable(true);

		bloquear();

		try {
			this.cargarDatosTabla();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ObservableList<Object> categoriaID = FXCollections.observableArrayList();
        ObservableList<Object> categoriaNomnre = FXCollections.observableArrayList();
        ObservableList<Object> subCategoria = FXCollections.observableArrayList();
        ObservableList<Object> marcas = FXCollections.observableArrayList();


        try {
        	conexion = Conexion.getInstance();

            // COMBOBOX DE CATEGORIA
            String slqCategoria = "SELECT id, nombre_cat FROM categorias";
            ResultSet resultadoCategoria = conexion.createStatement().executeQuery(slqCategoria);
            while(resultadoCategoria.next()) {
                categoria.getItems().add(resultadoCategoria.getString("nombre_cat"));
            }

            // COMBOBOX DE MARCAS
            String slqMarcas = "SELECT id, nombre_mar FROM marcas";
            ResultSet resultadoMarcas = conexion.createStatement().executeQuery(slqMarcas);
            while(resultadoMarcas.next()) {
                marca.getItems().add(resultadoMarcas.getString("nombre_mar"));
            }

         // COMBOBOX DE PROVEEDORES
            String slqProveedores = "SELECT id, razonsocial, contacto FROM proveedores";
            ResultSet resultadoProveedor = conexion.createStatement().executeQuery(slqProveedores);
            while(resultadoProveedor.next()) {
           proveedor.getItems().add(resultadoProveedor.getString("contacto"));
            }


            resultadoCategoria.close();
            resultadoMarcas.close();
            resultadoProveedor.close();
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	Image img=new Image("com/gruisem/vista/icono/empl.png");

	private ControladorVentanas error;


	public ControladorProducto() {
		error = ControladorVentanas.getInstancia();
	}

	public  void cargarDatosTabla() throws ClassNotFoundException {
        producto = FXCollections.observableArrayList();

        try{
        	conexion = Conexion.getInstance();

           String sql = "SELECT p.id, "
        		   + " p.clave, "
                   + " p.nombre, "
                   + " p.precio, "
                   + " m.nombre_mar AS nom_marca, "
                   + " c.nombre_cat AS nom_categoria, "
                   + " r.contacto AS proveedor "
                   + " FROM productos AS p, "
                   + " categorias AS c, "
                   + " marcas AS m, "
                   + " proveedores AS r "
                   + " WHERE p.idcategoria = c.id AND "
                   + " p.idmarca = m.id AND "
                   + " p.idproveedor = r.id AND"
                   + " p.estatus = true "
                   + " ORDER BY p.id ASC";
           //ResultSet
           ResultSet rs = conexion.createStatement().executeQuery(sql);

           String[] titulos = {
        		   "ID ",
        		   "Clave",
                   "Nombre",
                   "Precio",
                   "Marca",
                   "Categoria",
                   "Proveedor",
           };

           for (int i = 0; i < rs.getMetaData().getColumnCount(); i++ ) {
               final int j = i;
               col = new TableColumn(titulos[i]);
               col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
                   public ObservableValue<String> call(CellDataFeatures<ObservableList, String> parametro) {
                       return new SimpleStringProperty((String)parametro.getValue().get(j));
                   }
               });
               tvProductos.getColumns().addAll(col);
               col.setMinWidth(70);
               //System.out.println("Column ["+i+"] ");
               col.setCellFactory(new Callback<TableColumn<String,String>,TableCell<String,String>>(){
                   @Override
                   public TableCell<String, String> call(TableColumn<String, String> p) {
                       TableCell cell = new TableCell(){
                           @Override
                           protected void updateItem(Object t, boolean bln) {
                               if(t != null){
                                   super.updateItem(t, bln);
                                  // System.out.println(t);
                                   setText(t.toString());
                                   setAlignment(Pos.CENTER); //Setting the Alignment
                               }
                           }
                       };
                       return cell;
                   }
               });
           }

           while(rs.next()){
               ObservableList<String> row = FXCollections.observableArrayList();
               for(int i = 1 ; i <= rs.getMetaData().getColumnCount(); i++){
                   row.add(rs.getString(i));
               }
              // System.out.println("Row [1] added "+row );
               producto.addAll(row);
           }
          tvProductos.setItems(producto);
           rs.close();
         }catch(SQLException e){
             System.out.println("Error "+e);
         }
   }

   public void cargarProductosText(String valor) throws ClassNotFoundException {
	   cancelar.setDisable(false);
	   nuevo.setDisable(true);
	   desbloquear();
       try {

    	   conexion = Conexion.getInstance();
           String sql = "SELECT p.*, c.*, m.*, s.*"
                   + " FROM productos AS p, categorias AS c, marcas AS m, proveedores AS s "
                   + " WHERE p.id = "+valor+" AND "
                   + " p.idcategoria = c.id AND "
                   + " p.idmarca = m.id AND "
                   + " p.idproveedor = s.id ";
           ResultSet rs = conexion.createStatement().executeQuery(sql);

           while (rs.next()) {
        	   id.setText(rs.getString("id"));
               clave.setText(rs.getString("clave"));
               nombre.setText(rs.getString("nombre"));
               precio.setText(rs.getString("precio"));
               smax.setText(rs.getString("stockmax"));
               smin.setText(rs.getString("stockmin"));
               marca.setValue(rs.getString("nombre_mar"));
               categoria.setValue(rs.getString("nombre_cat"));
               proveedor.setValue(rs.getString("contacto"));

           }
           rs.close();
       } catch (SQLException ex) {
           System.out.println("Error "+ex);
       }

   }

   @FXML
   private void getProductoSeleccionado(MouseEvent event) {
	   eliminar.setDisable(false);
	   editar.setDisable(false);
	   cancelar.setDisable(false);
	   nuevo.setDisable(true);
	   desbloquear();
       tvProductos.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent e) {
               if (tvProductos != null) {

                   guardar.setDisable(true);
                   eliminar.setDisable(false);
                   editar.setDisable(false);

                   String valor = tvProductos.getSelectionModel().getSelectedItems().get(0).toString();

                   String cincoDigitos = valor.substring(1, 6);
                   String cuatroDigitos = valor.substring(1, 5);
                   String tresDigitos = valor.substring(1, 4);
                   String dosDigitos = valor.substring(1, 3);
                   String unDigitos = valor.substring(1, 2);

                   Pattern p = Pattern.compile("^[0-9]*$");

                   Matcher m5 = p.matcher(cincoDigitos);
                   Matcher m4 = p.matcher(cuatroDigitos);
                   Matcher m3 = p.matcher(tresDigitos);
                   Matcher m2 = p.matcher(dosDigitos);

                   if (m5.find()) {
                       try {
						cargarProductosText(cincoDigitos);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                   } else {
                       if (m4.find()) {
                           try {
							cargarProductosText(cuatroDigitos);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                       } else {
                           if (m3.find()) {
                               try {
								cargarProductosText(tresDigitos);
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                           } else {
                               if (m2.find()) {
                                   try {
									cargarProductosText(dosDigitos);
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                               } else {
                                   try {
									cargarProductosText(unDigitos);
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                               }
                            }
                       }
                   }
               }
           }
       });
   }

   @FXML
   private void agregarProducto(ActionEvent event) throws ClassNotFoundException {
	   eliminar.setDisable(true);
	   editar.setDisable(true);
	   cancelar.setDisable(true);
	   nuevo.setDisable(false);
       int indiceCategoria = categoria.getSelectionModel().getSelectedIndex() + 1;
       int indiceMarca = categoria.getSelectionModel().getSelectedIndex() + 1;
       int indiceProv = proveedor.getSelectionModel().getSelectedIndex() + 1;

       try {
    	   if (nombre.getText().trim().isEmpty() || precio.getText().trim().isEmpty()
    			   || smin.getText().trim().isEmpty() || smax.getText().trim().isEmpty()
    			   || marca.getSelectionModel().getSelectedItem()==null
    			   || categoria.getSelectionModel().getSelectedItem()==null
    			   || proveedor.getSelectionModel().getSelectedItem()==null){

    		   Alert alert = new Alert(AlertType.ERROR);
	   			alert.setTitle("Mensaje de ERROR");
	   			alert.setHeaderText(null);
	   			alert.setContentText("¡Existen campos vacios!");
	   			alert.showAndWait();
	   			guardar.setDisable(true);

    	   }else{

    	   conexion = Conexion.getInstance();
           String sql = "INSERT INTO productos "
                   + " (clave, nombre, precio,  stockmin, stockmax, idmarca, idcategoria, idproveedor, estatus, existencia) "
                   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, true, 0)";
           PreparedStatement estado = conexion.prepareStatement(sql);
           estado.setString(1, clave.getText());
           estado.setString(2, nombre.getText());
           estado.setInt(3, Integer.parseInt(precio.getText()));
           estado.setInt(4, Integer.parseInt(smin.getText()));
           estado.setInt(5, Integer.parseInt(smax.getText()));
           estado.setInt(6, indiceMarca);
           estado.setInt(7, indiceCategoria);
           estado.setInt(8, indiceProv);

           int n  = estado.executeUpdate();

           if (n > 0) {

               tvProductos.getColumns().clear();
               tvProductos.getItems().clear();
               cargarDatosTabla();
               limpiar();
		            Alert alert = new Alert(AlertType.INFORMATION);
		   			alert.setTitle("Mensaje de información");
		   			alert.setHeaderText(null);
		   			alert.setContentText("¡Producto agregado con éxito!");
		   			alert.showAndWait();
		   			guardar.setDisable(true);
		   			bloquear();
		   			limpiar();
           }

           estado.close();
    	   }
       } catch (SQLException e) {
           System.out.println("Error " + e);
       }

   }

   public void bloquear(){
	   clave.setDisable(true);
	   nombre.setDisable(true);
	   precio.setDisable(true);
	   smin.setDisable(true);
	   smax.setDisable(true);
	   marca.setDisable(true);
	   categoria.setDisable(true);
	   proveedor.setDisable(true);
   }

   public void desbloquear(){
	   clave.setDisable(false);
	   nombre.setDisable(false);
	   precio.setDisable(false);
	   smin.setDisable(false);
	   smax.setDisable(false);
	   marca.setDisable(false);
	   categoria.setDisable(false);
	   proveedor.setDisable(false);
   }

   public void limpiar(){
	   clave.clear();
       nombre.clear();
       precio.clear();
       smin.clear();
       smax.clear();
       marca.getSelectionModel().clearSelection();
       categoria.getSelectionModel().clearSelection();
       proveedor.getSelectionModel().clearSelection();
       id.setText(null);
   }

   @FXML public void cancelar(){
	   bloquear();
	   clave.clear();
       nombre.clear();
       precio.clear();
       smin.clear();
       smax.clear();
       marca.getSelectionModel().clearSelection();
       categoria.getSelectionModel().clearSelection();
       proveedor.getSelectionModel().clearSelection();
       eliminar.setDisable(true);
       editar.setDisable(true);
       guardar.setDisable(true);
       cancelar.setDisable(true);
       nuevo.setDisable(false);
       id.setText(null);
   }

   @FXML
   private void editarProducto(ActionEvent event) throws ClassNotFoundException {
	   eliminar.setDisable(true);
	   editar.setDisable(true);
	   cancelar.setDisable(true);
	   nuevo.setDisable(false);
       int indiceCategoria = categoria.getSelectionModel().getSelectedIndex() + 1;
       int indiceMarca = marca.getSelectionModel().getSelectedIndex() + 1;
       int indiceProv = proveedor.getSelectionModel().getSelectedIndex() + 1;

       try {
    	   conexion = Conexion.getInstance();

           String sql = "UPDATE productos "
        		   + " SET clave = ?, "
                   + " nombre = ?, "
                   + " precio = ?, "
                   + " stockmax = ?, "
                   + " stockmin = ?, "
                   + " idmarca = ?, "
                   + " idcategoria = ?, "
                   + " idproveedor = ?, "
                   + " estatus = true "
                   + " WHERE id = "+id.getText()+"";

           PreparedStatement estado = conexion.prepareStatement(sql);
           estado.setString(1, clave.getText());
           estado.setString(2, nombre.getText());
           estado.setInt(3, Integer.parseInt(precio.getText()));
           estado.setInt(4, Integer.parseInt(smax.getText()));
           estado.setInt(5, Integer.parseInt(smin.getText()));
           estado.setInt(6, indiceMarca);
           estado.setInt(7, indiceCategoria);
           estado.setInt(8, indiceProv);

           int n = estado.executeUpdate();

           if (n > 0) {
               tvProductos.getColumns().clear();
               tvProductos.getItems().clear();
               cargarDatosTabla();
           }
           	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Mensaje de información");
			alert.setHeaderText(null);
			alert.setContentText("¡Modificación exitosa!");
			alert.showAndWait();

			limpiar();
			bloquear();

           estado.close();
       } catch (SQLException e) {
           System.out.println("Error " + e);
       }
   }
	@FXML public void abrir(){
		imagen.setImage(null);
		tab.setVisible(true);
	}
	@FXML public void cerrar(){
		imagen.setImage(img);
		tab.setVisible(false);
	}


	@FXML private void eliminarProducto(ActionEvent event) throws ClassNotFoundException{
		editar.setDisable(true);
		cancelar.setDisable(true);
		nuevo.setDisable(false);
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Mensaje de confirmación");
    	alert.setHeaderText("¿Estás seguro de eliminar a " + nombre.getText() +"?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		   int indiceCategoria = categoria.getSelectionModel().getSelectedIndex() + 1;
		       int indiceMarca = marca.getSelectionModel().getSelectedIndex() + 1;
		       int indiceProv = proveedor.getSelectionModel().getSelectedIndex() + 1;

		       try {
		    	   conexion = Conexion.getInstance();

		           String sql = "UPDATE productos "
		        		   + " SET clave = ?, "
		                   + " nombre = ?, "
		                   + " precio = ?, "
		                   + " stockmax = ?, "
		                   + " stockmin = ?, "
		                   + " idmarca = ?, "
		                   + " idcategoria = ?, "
		                   + " idproveedor = ?, "
		                   + " estatus = false "
		                   + " WHERE id = "+id.getText()+"";

		           PreparedStatement estado = conexion.prepareStatement(sql);
		           estado.setString(1, clave.getText());
		           estado.setString(2, nombre.getText());
		           estado.setInt(3, Integer.parseInt(precio.getText()));
		           estado.setInt(4, Integer.parseInt(smax.getText()));
		           estado.setInt(5, Integer.parseInt(smin.getText()));
		           estado.setInt(6, indiceMarca);
		           estado.setInt(7, indiceCategoria);
		           estado.setInt(8, indiceProv);

		           int n = estado.executeUpdate();

		           if (n > 0) {
		               tvProductos.getColumns().clear();
		               tvProductos.getItems().clear();
		               cargarDatosTabla();
		               limpiar();
		           }

		           estado.close();
		       } catch (SQLException e) {
		           System.out.println("Error " + e);
		       }
		       System.out.println("Eliminaste a: " + nombre.getText());
    	} else {
    		System.out.println("Cancelaste la eliminación ");
    	}
	}

	@FXML private void nuevoProducto(){
		guardar.setDisable(false);
		eliminar.setDisable(true);
		editar.setDisable(true);
		cancelar.setDisable(false);
		nuevo.setDisable(true);
		desbloquear();
		limpiar();
	}

	@SuppressWarnings("unchecked")
	@FXML private void buscarProducto(ActionEvent event) throws ClassNotFoundException {

        tvProductos.getItems().clear();

        try {
        	conexion = Conexion.getInstance();
             String sql = "SELECT p.id, "
                    + " p.nombre, "
                    + " p.precio, "
                    + " p.stockmax, "
                    + " p.stockmin, "
                    + " c.nombre_cat AS nom_categoria, "
                    + " m.nombre_mar AS nom_marca, "
                    + " s.contacto AS nom_prov "
                    + " FROM productos AS p, categorias AS c, marcas AS m, proveedores AS s "
                    + " WHERE CONCAT "
                    + " (p.id, '', "
                    + " p.nombre, '', "
                    + " p.precio, '', "
                    + " c.nombre_cat, '',"
                    + " m.nombre_mar, '', "
                    + " s.contacto ) LIKE '%"+buscar.getText()+"%' AND "
                    + " p.idcategoria = c.id AND "
                    + " p.idmarca = m.id AND "
                    + " p.idproveedor = s.id ORDER BY p.id DESC";

            ResultSet rs = conexion.createStatement().executeQuery(sql);

            while(rs.next()){

                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= rs.getMetaData().getColumnCount(); i++){

                    row.add(rs.getString(i));
                }
                producto.addAll(row);
            }
            tvProductos.setItems(producto);
            rs.close();

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
	}

}




