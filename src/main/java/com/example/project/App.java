package com.example.project;

import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JAVADOCS CAN BE FOUND IN com.example.project/com/example/project
 *
 * FUTURE ENHANCEMENT: I would like to go over all the methods again and try to optimize everything,
 * i feel like if it were using more data the app would have a hard time keeping up.
 */
public class App extends Application {
    public static int partID = 5;
    public static int productID = 5;

    /**
     * opens main form
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        //addPart 600, 600 / main 1200, 400 / addProduct 1000, 800 / modifyPart 600, 600 / modifyProduct 1000, 800
        Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * gets parts id
     * @return
     */
    public static int getPartId(){
        return partID;
    }

    /**
     * gets product id
     * @return
     */
    public static int getProductId(){
        return productID;
    }

    /**
     * adds 1 to part id
     */
    public static void incPartId(){
        partID++;
    }

    /**
     * adds 1 to product id
     */
    public static void incProductId(){
        productID++;
    }

    /**
     * launches app
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}