package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class addPartController {
    public TextField addPartMachineIDField;
    public TextField addPartMin;
    public TextField addPartMax;
    public TextField addPartPrice;
    public TextField addPartInv;
    public TextField addPartName;
    public Button addPartCancel;
    public Button addPartSave;
    public Label addPartMachineID;
    public Label errorLabel;
    public RadioButton addPartInHouse;
    public RadioButton addPartOutsourced;

    /**
     * adds part when save button clicked
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void addPartSaveClick(ActionEvent actionEvent) throws IOException {
        String partName = addPartName.getText();

        if (!addPartOutsourced.isSelected() && !addPartInHouse.isSelected()) {

            errorLabel.setText("Please select in house or outsourced");

        }else if (addPartName.getText().isEmpty() || addPartInv.getText().isEmpty() || addPartPrice.getText().isEmpty()
                || addPartMin.getText().isEmpty() || addPartMax.getText().isEmpty() || addPartMachineIDField.getText().isEmpty()) {

            errorLabel.setText("Make sure all fields are filled");

        }else if(!addPartName.getText().matches("[a-zA-Z]+") || !addPartInv.getText().matches("\\d+") || !addPartPrice.getText().matches("\\d+\\.\\d+")
                || !addPartMin.getText().matches("\\d+") || !addPartMax.getText().matches("\\d+")){

            errorLabel.setText("Make sure all fields have valid data types");

        }else if(addPartOutsourced.isSelected()) {

            int partInvLvl = Integer.parseInt(addPartInv.getText());
            double partPrice = Double.parseDouble(addPartPrice.getText());
            int partMin = Integer.parseInt(addPartMin.getText());
            int partMax = Integer.parseInt(addPartMax.getText());

            String companyName = addPartMachineIDField.getText();

            if (partMin >= partMax || partInvLvl > partMax || partInvLvl < partMin){
                errorLabel.setText("Make sure inventory level/inventory max and min are valid");
            }else if (addPartMachineIDField.getText().matches("[a-zA-Z]+")){
                Outsourced obj = new Outsourced(App.getPartId(), partName, partPrice, partInvLvl, partMin, partMax, companyName);
                App.incPartId();
                Inventory.addPart(obj);
                goToMain(actionEvent);
            }else{
                errorLabel.setText("Make sure all fields have valid data types");
            }

        }else if(addPartInHouse.isSelected()){

            int partInvLvl = Integer.parseInt(addPartInv.getText());
            double partPrice = Double.parseDouble(addPartPrice.getText());
            int partMin = Integer.parseInt(addPartMin.getText());
            int partMax = Integer.parseInt(addPartMax.getText());

            if (partMin >= partMax || partInvLvl > partMax || partInvLvl < partMin){
                errorLabel.setText("Make sure inventory level/inventory max and min are valid");
            }else if (addPartMachineIDField.getText().matches("\\d+")){
                int machineID = Integer.parseInt(addPartMachineIDField.getText());
                InHouse obj = new InHouse(App.getPartId(), partName, partPrice, partInvLvl, partMin, partMax, machineID);
                App.incPartId();
                Inventory.addPart(obj);
                goToMain(actionEvent);
            }else{
                errorLabel.setText("Make sure all fields have valid data types");
            }
        }
    }

    /**
     * goes back to main menu
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void addPartCancelClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //addPart 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
        Scene scene = new Scene(root, 1200, 400);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * only allows 1 radio to be clicked at a time
     */
    @FXML
    protected void addPartInHouseClick() {

        addPartMachineID.setText("Machine ID");
        addPartOutsourced.setSelected(false);
        addPartMachineID.setLayoutX(99.0);
    }

    /**
     * only allows 1 radio to be clicked at a time
     */
    @FXML
    protected void addPartOutsourcedClick() {

        addPartMachineID.setText("Company Name");
        addPartInHouse.setSelected(false);
        addPartMachineID.setLayoutX(79.0);
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
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

}