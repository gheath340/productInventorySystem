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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class addProductController implements Initializable {
    public Button addProductSave;
    public Button addProductCancel;
    public Label errorLabel;
    public Button addPartToAssociated;
    public Button removeAssociated;
    public TextField searchBar;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;

    public TableView<Part> partsTable;
    public TableView<Part> associatedTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn invLevelCol;
    public TableColumn priceCol;
    public TableColumn associatedPartIdCol;
    public TableColumn associatedNameCol;
    public TableColumn associatedInvLvlCol;
    public TableColumn associatedPriceCol;

    /**
     * initializes tables
     * @param location
     * @param resources
     */
    @Override
    public void initialize (URL location, ResourceBundle resources) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());
    }

    /**
     * saves product
     * @param actionEvent
     * @throws IOException
     */
    public void onSaveClick(ActionEvent actionEvent) throws IOException {
        String prodName = nameField.getText();

        if (nameField.getText().isEmpty() || invField.getText().isEmpty() || priceField.getText().isEmpty()
                || minField.getText().isEmpty() || maxField.getText().isEmpty()) {

            errorLabel.setText("Make sure all fields are filled");

        }else if(!nameField.getText().matches("[a-zA-Z]+") || !invField.getText().matches("\\d+") || !priceField.getText().matches("\\d+\\.\\d+")
                || !minField.getText().matches("\\d+") || !maxField.getText().matches("\\d+")){

            errorLabel.setText("Make sure all fields have valid data types");

        }else {

            int prodInv = Integer.parseInt(invField.getText());
            double prodPrice = Double.parseDouble(priceField.getText());
            int prodMin = Integer.parseInt(minField.getText());
            int prodMax = Integer.parseInt(maxField.getText());


            if (prodMin >= prodMax || prodInv > prodMax || prodInv < prodMin){
                errorLabel.setText("Make sure inventory level/inventory max and min are valid");
            }else{
                Product obj = new Product(App.getProductId(), prodName, prodPrice, prodInv, prodMin, prodMax);
                for(Part part : associatedTable.getItems()){
                obj.addAssociatedPart(part);
                }
                App.incProductId();
                Inventory.addProduct(obj);
                goToMain(actionEvent);
            }
        }
    }

    /**
     * goes back to main menu
     * @param actionEvent
     * @throws IOException
     */
    public void addProductCancelClick(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }

    /**
     * goes to main menu
     * @param actionEvent
     * @throws IOException
     */
    protected void goToMain(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(App.class.getResource("main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //addPart 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
        Scene scene = new Scene(root, 1200, 400);
        stage.setTitle("main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * adds part to associated list
     * @param actionEvent
     */
    public void onAddToAssociatedClick(ActionEvent actionEvent) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        associatedTable.getItems().add(part);
    }

    /**
     * removes selected element in associated table
     * @param actionEvent
     */
    public void onRemoveClick(ActionEvent actionEvent) {
        Part selectedPart = associatedTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Confirm Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                associatedTable.getItems().remove(selectedPart);
            }
        }
    }

    /**
     * goes to main menu
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        goToMain(actionEvent);
    }

    /**
     * searches parts
     * @param actionEvent
     */
    public void onSearch(ActionEvent actionEvent) {
        String q = searchBar.getText();
        ObservableList<Part> parts = Inventory.lookupPart(q);
        errorLabel.setText("");

        if(parts.size() == 0){
            int id = Integer.parseInt(q);
            Part p = Inventory.lookupPart(id);
            if(p != null){
                parts.add(p);
            }else if (p == null){
                errorLabel.setText("Error: no parts match this search.");
            }
        }
        partsTable.setItems(parts);
        searchBar.setText("");
    }
}
