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
import java.util.ResourceBundle;

public class modPartController implements Initializable {


    public TextField partMin;
    public Label machineID;
    public TextField machineIdField;
    public TextField partMax;
    public TextField partPrice;
    public TextField partInv;
    public TextField partName;
    public Button partCancel;
    public Button partSave;
    public RadioButton inHouse;
    public RadioButton outsourced;
    public Label errorLabel;
    public TextField idField;
    private Part handoffPart = null;

    /**
     * initializes text fields
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handoffPart = mainController.handoff();

        int id = handoffPart.getId();
        idField.setText(String.valueOf(id));
        partName.setText(handoffPart.getName());

        Double price = handoffPart.getPrice();
        partPrice.setText(price.toString());

        int inv = handoffPart.getStock();
        partInv.setText(String.valueOf(inv));

        int max = handoffPart.getMax();
        partMax.setText(String.valueOf(max));

        int min = handoffPart.getMin();
        partMin.setText(String.valueOf(min));

        if(handoffPart instanceof Outsourced){
            outsourced.setSelected(true);
            outsourcedClick();
            String companyName = ((Outsourced) handoffPart).getCompanyName();
            machineIdField.setText(companyName);
        }else if(handoffPart instanceof InHouse){
            inHouse.setSelected(true);
            inHouseClick();
            int machineId = ((InHouse) handoffPart).getMachineId();
            machineIdField.setText(String.valueOf(machineId));
        }



    }

    /**
     * makes radio buttons work
     */
    @FXML
    protected void inHouseClick() {
        machineID.setText("Machine ID");
        outsourced.setSelected(false);
        machineID.setLayoutX(99.0);
    }
    /**
     * makes radio buttons work
     */
    @FXML
    protected void outsourcedClick() {
        machineID.setText("Company Name");
        inHouse.setSelected(false);
        machineID.setLayoutX(79.0);
    }

    /**
     * goes to main menu
     * @param actionEvent
     * @throws IOException
     */
    public void partCancelClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        //part 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
        Scene scene = new Scene(root, 1200, 400);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * saves changes and goes to main menu
     * RUNTIME ERROR: i was having trouble getting the text fields to become the data type that i wanted but i found the .parseInt function that solved my problem
     * @param actionEvent
     * @throws IOException
     */
    public void partSaveClick(ActionEvent actionEvent) throws IOException {
        String name = partName.getText();

        if (!outsourced.isSelected() && !inHouse.isSelected()) {

            errorLabel.setText("Please select in house or outsourced");

        }else if (partName.getText().isEmpty() || partInv.getText().isEmpty() || partPrice.getText().isEmpty()
                || partMin.getText().isEmpty() || partMax.getText().isEmpty() || machineIdField.getText().isEmpty()) {

            errorLabel.setText("Make sure all fields are filled");

        }else if(!partName.getText().matches("[a-zA-Z]+") || !partInv.getText().matches("\\d+") || !partPrice.getText().matches("\\d+\\.\\d+")
                || !partMin.getText().matches("\\d+") || !partMax.getText().matches("\\d+")){

            errorLabel.setText("Make sure all fields have valid data types");

        }else if(outsourced.isSelected()) {

            int id = Integer.parseInt(idField.getText());
            int partInvLvl = Integer.parseInt(partInv.getText());
            double price = Double.parseDouble(partPrice.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());
            String companyName = machineIdField.getText();

            if (min >= max || partInvLvl > max || partInvLvl < min){
                errorLabel.setText("Make sure inventory level/inventory max and min are valid");
            } else {
                if (machineIdField.getText().matches("[a-zA-Z]+")) {
                    Part part = Inventory.lookupPart(id);
                    Inventory.deletePart(part);
                    Outsourced p = new Outsourced(id, name, price, partInvLvl, min, max, companyName);
                    Inventory.addPart(p);
                    goToMain(actionEvent);
                } else {
                    errorLabel.setText("Make sure all fields have valid data types");
                }
            }

        }else if(inHouse.isSelected()){

            int id = Integer.parseInt(idField.getText());
            int partInvLvl = Integer.parseInt(partInv.getText());
            double price = Double.parseDouble(partPrice.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());
            int machineID = Integer.parseInt(machineIdField.getText());

            if (min >= max || partInvLvl > max || partInvLvl < min){
                errorLabel.setText("Make sure inventory level/inventory max and min are valid");
            }else if (machineIdField.getText().matches("\\d+")){
                Part part = Inventory.lookupPart(id);
                Inventory.deletePart(part);
                InHouse p = new InHouse(id, name, price, partInvLvl, min, max, machineID);
                Inventory.addPart(p);
                goToMain(actionEvent);
            }else{
                errorLabel.setText("Make sure all fields have valid data types");
            }
        }
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
