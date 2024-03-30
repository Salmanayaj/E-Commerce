package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    private static TableView<Product> ProductTable;
    public VBox createTable(ObservableList<Product> data){
//        columns
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


//        data


        ProductTable = new TableView<>();
        ProductTable.setItems(data);
        ProductTable.getColumns().addAll(id,name,price);
        ProductTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(ProductTable);
        return vBox;

    }
//    public VBox DummyTable() {
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.add(new Product(2,"Iphone",45000));
//        data.add(new Product(3,"samsung",50000));
//        return createTable(data);
//    }
    public VBox getAllProducts() {
        ObservableList<Product> data = Product.getAllProducts();
        return createTable(data);
    }
    public static Product getselectedproduct(){
       return ProductTable.getSelectionModel().getSelectedItem();
    }
    public VBox getproductInCart(ObservableList<Product> data){
        return createTable(data);
    }


//    Order Table
    private static TableView<Orders> OrderTable;

    public VBox create(ObservableList<Orders> data){
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn quantity = new TableColumn("QUANTITY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn date_time = new TableColumn("DATE_TIME");
        date_time.setCellValueFactory(new PropertyValueFactory<>("date_time"));

        TableColumn order_status = new TableColumn("ORDER_STATUS");
        order_status.setCellValueFactory(new PropertyValueFactory<>("order_status"));


        OrderTable = new TableView<>();
        OrderTable.getColumns().addAll(id,name,quantity,date_time,order_status);
        OrderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(OrderTable);
        return vBox;
    }

    public VBox getAllProduct() {
        ObservableList<Orders> data = Orders.getAllProducts();
        return create(data);
    }
    public static Orders getselectedproducts(){
        return OrderTable.getSelectionModel().getSelectedItem();
    }
    public VBox getproductInOrder(ObservableList<Orders> data){
        return create(data);
    }
}
