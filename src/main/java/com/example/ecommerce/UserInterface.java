package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserInterface {

    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    Button SignInButton;
    Button cartButton;
    Label welcomelabel;
    ObservableList<Product> itemsIncart = FXCollections.observableArrayList();
    ObservableList<Orders> orderitem = FXCollections.observableArrayList();


    VBox body;
    Customer loggedInCustomer;
    Button placeorderbutton = new Button("placed ordered");

    ProductList productList = new ProductList();


    VBox productpage;
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(700,600);
//        root.getChildren().add(loginPage);
//        root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setTop(headerBar);
        productpage = productList.getAllProducts();
        root.setCenter(body);
        body.getChildren().add(productpage);
        root.setBottom(footerBar);
        return root;
    }

    public UserInterface(){
        createloginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void createloginPage(){
        Text usernameText = new Text("UserName");
        Text passwordText = new Text("Password");

        TextField username = new TextField("salmanayaj7@gmail.com");
        username.setPromptText("Type Your UserName here");
        PasswordField password = new PasswordField();
        password.setText("salman12");
        password.setPromptText("Type Your password here");

        Button loginButton = new Button("Login");

        Label messagelabel = new Label("Hi");

        loginPage = new GridPane();
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
//        loginPage.setStyle("-fx-background-color: #f2f2f2;");
        loginPage.add(usernameText,0,0);
        loginPage.add(username,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(loginButton,1,2);
        loginPage.add(messagelabel,0,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = username.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name,pass);
                if(loggedInCustomer != null){
                    messagelabel.setText("welcome :" + loggedInCustomer.getName());
                    welcomelabel.setText("welcome - " + loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomelabel);
                    body.getChildren().clear();
                    body.getChildren().add(productpage);
                } else {
                    messagelabel.setText("Login failed ! please provide the correct username and password");
                }
            }
        });

    }
    private void createHeaderBar(){
        Button homeButton = new Button();
        Image image = new Image("E:\\project file\\IdeaProjects\\ecommerce\\src\\comm.jpeg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(26);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        Button searchButton = new Button("Search");
        SignInButton = new Button("Sign In");
        welcomelabel = new Label();
        cartButton = new Button("Cart");
        Button orderButton = new Button("Orders");

        headerBar = new HBox();
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searchBar, searchButton, SignInButton, cartButton, orderButton);

        SignInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); //remove everything
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(SignInButton);
            }
        });
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getproductInCart(itemsIncart);
                prodPage.getChildren().add(placeorderbutton);
                prodPage.setSpacing(10);
                prodPage.setAlignment(Pos.CENTER);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });
        placeorderbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (itemsIncart == null) {
                    showDailog("Please add a product in the cart to place order!");
                    return;
                }
                if (loggedInCustomer == null) {
                    showDailog("Please log in first to place order!");
                    return;
                }
                int count = Orders.placeMultipleOrders(loggedInCustomer, itemsIncart);
                if (count != 0) {
                    showDailog("Order "+count+" place successfully!!");
                } else {
                    showDailog("Order fail!");
                }
            }
        });
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productpage);
                footerBar.setVisible(true);
                if(loggedInCustomer==null && headerBar.getChildren().indexOf(SignInButton) == -1){
                    headerBar.getChildren().add(SignInButton);
                }
            }
        });
        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getproductInOrder(orderitem);
                prodPage.getChildren().add(orderButton);
                prodPage.setSpacing(10);
                prodPage.setAlignment(Pos.CENTER);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
                headerBar.getChildren().add(orderButton);

//                Orders order = ProductList.getselectedproducts();
//                orderitem.add(order);
//                headerBar.getChildren().add(welcomelabel);

            }
        });

    }
    private void createFooterBar(){
        Button buyNowButton = new Button("Buy Now");
        Button addtocart = new Button("Add to Cart");

        footerBar = new HBox();
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addtocart);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = ProductList.getselectedproduct();
                if (product == null) {
                    showDailog("Please select a product first to place an order!");
                    return;
                }
                if (loggedInCustomer == null) {
                    showDailog("Please log in first to place order!");
                    return;
                }
                boolean status = Orders.placeOrders(loggedInCustomer, product);
                if (status == true) {
                    showDailog("Order place successfully!!");
                } else {
                    showDailog("Order fail!");
                }
            }
        });
        addtocart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = ProductList.getselectedproduct();
                if (product == null) {
                    showDailog("Please select a product first to place in the Cart!");
                    return;
                }
                itemsIncart.add(product);
                showDailog("Items has been added successfully.");
            }
        });


    }
    private void showDailog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle(null);
        alert.showAndWait();
    }
}
