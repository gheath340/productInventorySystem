package com.example.project;

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

public class modProductController implements Initializable {
    public TableView<Part> partsTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableView<Part> associatedTable;
    public TableColumn associatedPartIdCol;
    public TableColumn associatedNameCol;
    public TableColumn associatedInvLvlCol;
    public TableColumn associatedPriceCol;

    public Button addAssociated;
    public Button removeAssociated;
    public Button saveProd;
    public Button cancelProd;
    public TextField searchField;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public Label errorLabel;
    private Product handoffProd = null;

    /**
     * initializes table views
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        handoffProd = mainController.handoffProd();

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());
        associatedTable.setItems(handoffProd.getAllAssociatedParts());


        int id = handoffProd.getId();
        idField.setText(String.valueOf(id));
        nameField.setText(handoffProd.getName());

        Double price = handoffProd.getPrice();
        priceField.setText(price.toString());

        int inv = handoffProd.getStock();
        invField.setText(String.valueOf(inv));

        int max = handoffProd.getMax();
        maxField.setText(String.valueOf(max));

        int min = handoffProd.getMin();
        minField.setText(String.valueOf(min));

    }

    /**
     * saves product changes
     * @param actionEvent
     * @throws IOException
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        String prodName = nameField.getText();

        if (nameField.getText().isEmpty() || invField.getText().isEmpty() || priceField.getText().isEmpty()
                || minField.getText().isEmpty() || maxField.getText().isEmpty()) {

            errorLabel.setText("Make sure all fields are filled");

        }else if(!nameField.getText().matches("[a-zA-Z]+") || !invField.getText().matches("\\d+") || !priceField.getText().matches("\\d+\\.\\d+")
                || !minField.getText().matches("\\d+") || !maxField.getText().matches("\\d+")){

            errorLabel.setText("Make sure all fields have valid data types");

        }else {
            int id = Integer.parseInt(idField.getText());
            int prodInv = Integer.parseInt(invField.getText());
            double prodPrice = Double.parseDouble(priceField.getText());
            int prodMin = Integer.parseInt(minField.getText());
            int prodMax = Integer.parseInt(maxField.getText());


            if (prodMin >= prodMax || prodInv > prodMax || prodInv < prodMin){
                errorLabel.setText("Make sure inventory level/inventory max and min are valid");
            }else{
                Product prod = Inventory.lookupProduct(id);
                Inventory.deleteProduct(prod);
                Product p = new Product(id, prodName, prodPrice, prodInv, prodMin, prodMax);
                for(Part part : associatedTable.getItems()){
                    p.addAssociatedPart(part);
                }
                Inventory.addProduct(p);
                goToMain(actionEvent);
            }
        }
    }

    /**
     * goes to main menu
     * @param actionEvent
     * @throws IOException
     */
    public void modProductCancelClick(ActionEvent actionEvent) throws IOException{
       goToMain(actionEvent);
    }

    /**
     * adds part to associated list
     * @param actionEvent
     */
    public void onAddAssociated(ActionEvent actionEvent) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        associatedTable.getItems().add(part);
    }

    /**
     * removes part from associated list
     * @param actionEvent
     */
    public void onRemoveAssociated(ActionEvent actionEvent) {
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
    public void onCancelClick(ActionEvent actionEvent) throws IOException{
        goToMain(actionEvent);
    }

    /**
     * makes part search works
     * @param actionEvent
     */
    public void onSearch(ActionEvent actionEvent) {
        String q = searchField.getText();
        ObservableList<Part> parts = Inventory.lookupPart(q);
        errorLabel.setText("");

        if(parts.size() == 0){
            int id = Integer.parseInt(q);
            Part p = Inventory.lookupPart(id);
            if(p != null){
                parts.add(p);
            }else if(p == null){
                errorLabel.setText("Error: no part matches this search.");
            }
        }
        partsTable.setItems(parts);
        searchField.setText("");
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
}
