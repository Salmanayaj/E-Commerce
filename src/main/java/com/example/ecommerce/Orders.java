package com.example.ecommerce;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Orders {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty date_time;
    private SimpleStringProperty order_status;

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public String getDate_time() {
        return date_time.get();
    }

    public SimpleStringProperty date_timeProperty() {
        return date_time;
    }

    public String getOrder_status() {
        return order_status.get();
    }

    public SimpleStringProperty order_statusProperty() {
        return order_status;
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public Orders(int id, String name, int quantity, String date_time, String order_status){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date_time = new SimpleStringProperty(date_time);
        this.order_status = new SimpleStringProperty(order_status);

    }
    public static ObservableList<Orders> getAllProducts(){
        String selectAllorders = "select orders.id,product.name,orders.quantity,orders.date_time,orders.order_status from orders join product on orders.product_id = product_id";
        return fetchProductData(selectAllorders);
    }
    public static ObservableList<Orders> fetchProductData(String query){
        ObservableList<Orders> data = FXCollections.observableArrayList();
        dbConnection Connection = new dbConnection();

        try {
            ResultSet rs = Connection.getqueryTable(query);
            while(rs.next()){
                Orders order = new Orders(rs.getInt("id"),rs.getString("name"),rs.getInt("quantity"),rs.getString("date_time"),
                        rs.getString("order_status"));
                data.add(order);
            }
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean placeOrders(Customer customer, Product product) {
        String grouporderId = " select max(group_order_id) +1 id from orders";
        dbConnection connection = new dbConnection();
        ResultSet rs = connection.getqueryTable(grouporderId);
        try {
            if (rs.next()) {
                String placeOrders = "insert into orders(group_order_id,customer_id,product_id) values("+
                        rs.getInt("id") + "," + customer.getId() + "," + product.getId() + ")";
                return connection.updateDatabase(placeOrders) != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int placeMultipleOrders(Customer customer, ObservableList<Product> productList) {
        String grouporderId = " select max(group_order_id) +1 id from orders";
        dbConnection connection = new dbConnection();
        ResultSet rs = connection.getqueryTable(grouporderId);
        try {
            int count = 0;
            if (rs.next()) {
                for (Product product : productList){
                    String placeOrders = "insert into orders(group_order_id,customer_id,product_id) values("+
                            rs.getInt("id") + "," + customer.getId() + "," + product.getId() + ")";
                   count += connection.updateDatabase(placeOrders);
                }

                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
