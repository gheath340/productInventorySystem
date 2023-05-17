package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainController implements Initializable{

    public TableView<Part> partsTable;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInvLevel;
    public TableColumn partPrice;
    public TableColumn partInvMin;
    public TableColumn partInvMax;
    public TableView<Product> productsTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInvLevel;
    public TableColumn productPrice;
    public TableColumn productInvMin;
    public TableColumn productInvMax;

    public TextField partSearch;
    public Button addPartButton;
    public Button modPartButton;
    public Button addProductButton;
    public Button modProductButton;
    public Button deleteProduct;
    public Button deletePart;
    public Button exitButton;
    public TextField productSearch;
    public Label productDeleteError;
    public Label partErrorLabel;
    public Label productErrorLabel;
    public static boolean firstTime = true;
    public static Part handoff = null;
    public static Product handoffProd = null;

    /**
     *
     * @param location
     * @param resources
     * initializes the parts and products
     */

    @Override
    public void initialize (URL location, ResourceBundle resources){
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        partInvMax.setCellValueFactory(new PropertyValueFactory<>("max"));

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInvMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        productInvMax.setCellValueFactory(new PropertyValueFactory<>("max"));

        addTestData();
        productsTable.setItems(Inventory.getAllProducts());
        partsTable.setItems(Inventory.getAllParts());

    }

    /**
     *adds 4 products and 4 parts to start the app
     */
    private void addTestData(){
        if(!firstTime){
            return;
        }
        firstTime = false;
        InHouse part1 = new InHouse(1, "nut", 1.99, 2, 1, 10, 1);
        Outsourced part2 = new Outsourced(2, "bolt", 1.99, 2, 1, 10, "bolt maker");
        InHouse part3 = new InHouse(3, "washer", 1.99, 2, 1, 10, 1);
        InHouse part4 = new InHouse(4, "bar", 1.99, 2, 1, 10, 1);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);

        Product prod1 = new Product(1, "bike", 1.99, 2, 1, 10);
        Product prod2 = new Product(2, "car", 1.99, 2, 1, 10);
        Product prod3 = new Product(3, "scooter", 1.99, 2, 1, 10);
        Product prod4 = new Product(4, "grill", 1.99, 2, 1, 10);
        Inventory.addProduct(prod1);
        prod1.addAssociatedPart(part1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addProduct(prod4);

        productsTable.setItems(Inventory.getAllProducts());
        partsTable.setItems(Inventory.getAllParts());

    }

    /**
     *
     * @return handoff, used to put info into mod part
     */
    public static Part handoff(){
        return handoff;
    }

    /**
     *
     * @return handoffProduct, used to put info into mod product
     */
    public static Product handoffProd(){
        return handoffProd;
    }

    /**
     * deletes the part you have selected
     */
    public void deletePartClick(){
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Confirm Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Inventory.deletePart(selectedPart);
                partsTable.getItems().remove(selectedPart);
            }
        }
    }

    /**
     * deletes product you selected or raises error if applicable
     */
    public void deleteProductClick(){
        productDeleteError.setText("");
        Product selectedProd = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProd != null && selectedProd.getAllAssociatedParts().size() == 0){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Confirm Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Inventory.deleteProduct(selectedProd);
            }
        }else if(selectedProd.getAllAssociatedParts().size() != 0){
            productDeleteError.setText("Selected product has associated parts.");
        }
    }

    /**
     *
     * @param actionEvent
     * searches for products based on what is in search bar
     */
    public void searchingProduct(ActionEvent actionEvent) {
        String q = productSearch.getText();
        ObservableList<Product> products = Inventory.lookupProduct(q);
        productErrorLabel.setText("");

        if(products.size() == 0){
            int id = Integer.parseInt(q);
            Product p = Inventory.lookupProduct(id);
            if(p != null){
                products.add(p);
            }else if (p == null){
                productErrorLabel.setText("Error: no products match this search.");
            }
        }
        productsTable.setItems(products);
    }

    /**
     * searches for part based off what is in search bar
     * @param actionEvent
     */
    public void searchingPart(ActionEvent actionEvent) {
        String q = partSearch.getText();
        ObservableList<Part> parts = Inventory.lookupPart(q);
        partErrorLabel.setText("");

        if(parts.size() == 0){
            int id = Integer.parseInt(q);
            Part p = Inventory.lookupPart(id);
            if(p != null){
                parts.add(p);
            }else if(p == null){
                partErrorLabel.setText("Error: no parts match this search.");
            }
        }
        partsTable.setItems(parts);
    }


    /**
     * opens add part menu
     * @param actionEvent
     * @throws IOException
     */
    public void openAddPart(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(App.class.getResource("addPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //addPart 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * opens mod part menu
     * @param actionEvent
     * @throws IOException
     */
    public void openModPart(ActionEvent actionEvent) throws IOException{

        if(partsTable.getSelectionModel().getSelectedItem() != null) {

            handoff = partsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(App.class.getResource("modifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //addPart 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
            Scene scene = new Scene(root, 600, 600);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }else{
            partErrorLabel.setText("Please select item to modify.");
        }
    }

    /**
     * opens add product menu
     * @param actionEvent
     * @throws IOException
     */
    public void openAddProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(App.class.getResource("addProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //addPart 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * opens mod product menu
     * @param actionEvent
     * @throws IOException
     */
    public void openModProduct(ActionEvent actionEvent) throws IOException{
        if(productsTable.getSelectionModel().getSelectedItem() != null) {

            handoffProd = productsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(App.class.getResource("modifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //addPart 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
            Scene scene = new Scene(root, 1000, 800);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }else{
            productErrorLabel.setText("Please select item to modify.");
        }
    }

    /**
     * exits app
     * @param actionEvent
     */
    public void exitClick(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
